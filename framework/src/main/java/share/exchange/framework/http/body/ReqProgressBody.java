package share.exchange.framework.http.body;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.response.IResponseHandler;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public class ReqProgressBody extends RequestBody {

    private IResponseHandler mResponseHandler;
    private RequestBody mRequestBody;
    private BufferedSink mBufferedSink;

    public ReqProgressBody(RequestBody mRequestBody, IResponseHandler mResponseHandler) {
        this.mRequestBody = mRequestBody;
        this.mResponseHandler = mResponseHandler;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(mBufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        mBufferedSink.flush();
    }

    private Sink sink(BufferedSink sink) {

        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度
            long contentLength = 0l;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }
                bytesWritten += byteCount;
                OkDroid.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mResponseHandler.onProgress(bytesWritten, contentLength);
                    }
                });
            }
        };
    }
}
