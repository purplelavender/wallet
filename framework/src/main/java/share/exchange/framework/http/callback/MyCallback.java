package share.exchange.framework.http.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.response.IResponseHandler;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public class MyCallback implements Callback {

    private IResponseHandler mResponseHandler;

    public MyCallback(IResponseHandler mResponseHandler) {
        this.mResponseHandler = mResponseHandler;
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        OkDroid.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mResponseHandler.onFailed(500, e.toString());
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {
            mResponseHandler.onSuccess(response);
        } else {
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseHandler.onFailed(response.code(), response.message());
                }
            });
        }
    }
}
