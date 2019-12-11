package share.exchange.framework.http.response;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import share.exchange.framework.http.OkDroid;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public abstract class JsonResHandler implements IResponseHandler {

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";
        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(response.code(), "read response body failed");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalBodyStr = responseBodyStr;
        try {
            final JSONObject jsonObject = JSONObject.parseObject(finalBodyStr);
            OkDroid.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String msg = jsonObject.getString("msg");
                        int code = jsonObject.getInteger("code");
                        if (code == 888) {
                            onNoLogin();
                        } else if (code == 0){
                            if (jsonObject.containsKey("data")) {
                                String data = jsonObject.getString("data");
                                onSuccess(response.code(), data);
                            } else {
                                onSuccess(response.code(), finalBodyStr);
                            }
                        } else {
                            onFailed(code, msg);
                        }
                    }
                });

        } catch (Exception e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(response.code(), "failed parse jsonobject,body=" + finalBodyStr);
                }
            });
        }
    }

    public void onSuccess(int statusCode, String response) {

    }

    @Override
    public void onProgress(long progress, long total) {

    }

    public abstract void onNoLogin();
}
