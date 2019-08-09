package share.exchange.framework.http.response;


import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.utils.StringUtil;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public abstract class RawResHandler implements IResponseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String resBodyStr = "";
        try {
            resBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(response.code(), "failed read response body");
                }
            });
        } finally {
            responseBody.close();
        }
        final String finalResBodyStr = resBodyStr;
        if (!StringUtil.isContain(finalResBodyStr, "status")) {
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(404, "expected begin_object but was string");
                }
            });
        } else {
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(response.code(), finalResBodyStr);
                }
            });
        }
    }

    public abstract void onSuccess(int statusCode, String response);

    @Override
    public void onProgress(long progress, long total) {

    }
}
