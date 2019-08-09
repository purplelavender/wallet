package share.exchange.framework.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import share.exchange.framework.http.builder.DownloadBuilder;
import share.exchange.framework.http.builder.GetBuilder;
import share.exchange.framework.http.builder.PostBuilder;
import share.exchange.framework.http.builder.UploadBuilder;
import share.exchange.framework.utils.StringUtil;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public class OkDroid {

    public static Handler mHandler = new Handler(Looper.getMainLooper());
    public static OkDroid mInstance;
    private OkHttpClient httpClient;

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("LogUtils", message);
        }
    });

    private OkDroid() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(15, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(15, TimeUnit.SECONDS)//设置连接超时时间
                .build();
//                .addInterceptor(logging).build();
    }

    public static OkDroid getInstance() {
        if (mInstance == null) {
            synchronized (OkDroid.class) {
                if (mInstance == null) {
                    mInstance = new OkDroid();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return httpClient;
    }

    /**
     * get请求
     * @return
     */
    public GetBuilder get() {
        return new GetBuilder(this);
    }

    /**
     * post请求
     * @return
     */
    public PostBuilder post() {
        return new PostBuilder(this);
    }

    /**
     * 上传
     * @return
     */
    public UploadBuilder upload() {
        return new UploadBuilder(this);
    }

    /**
     * 下载
     * @return
     */
    public DownloadBuilder download() {
        return new DownloadBuilder(this);
    }

    /**
     * 根据tag取消请求
     *
     * @param tag
     */
    public void cancel(String tag) {
        Dispatcher dispatcher = httpClient.dispatcher();
        if (StringUtil.isEmpty(tag)) {
            dispatcher.cancelAll();
        } else {
            // 结束等待队列中的请求
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            // 结束正在运行中的请求
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }
}
