package com.wallet.hz.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.response.IResponseHandler;

/**
 * @ClassName: MarketJsonResHandler
 * @Description: 行情列表专用的解析器
 * @Author: ZL
 * @CreateDate: 2019/11/07 19:18
 */
public abstract class MarketJsonResHandler implements IResponseHandler {

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
                    if (jsonObject.containsKey("data")) {
                        String data = jsonObject.getString("data");
                        onSuccess(response.code(), data);
                    } else {
                        onSuccess(response.code(), finalBodyStr);
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

}
