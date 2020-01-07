package share.exchange.framework.http.callback;

import android.app.Activity;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import share.exchange.framework.R;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.response.IResponseHandler;
import share.exchange.framework.manager.AppManager;

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
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            //判断超时异常
            //判断连接异常
            Activity activity = AppManager.getAppManager().currentActivity();
            String errStr = "";
            if (activity != null && !activity.isFinishing()) {
                errStr = activity.getString(R.string.no_network);
            }
            final String finalErrStr = errStr;
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseHandler.onFailed(-1, finalErrStr);
                }
            });
        } else {
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseHandler.onFailed(500, e.toString());
                }
            });
        }
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
