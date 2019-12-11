package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.MarketInfo;
import com.wallet.hz.model.MarketList;
import com.wallet.hz.utils.MarketJsonResHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.executor.AsyncExecutor;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CustomCountDownTimer;

/**
 * @ClassName: MarketPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:11
 */
public class MarketPresenter extends BasePresenter<MarketContract.View> implements MarketContract.Presenter {

    private Context mContext;
    private WebSocket mSocketClient;
    private ArrayList<MarketList> mMarketLists = new ArrayList<>();
    private MarketTimer mMarketTimer;

    public MarketPresenter(MarketContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void destroyView() {
        super.destroyView();
        stopTimer();
    }

    @Override
    public void getMarketList() {
        startTimer();
        final HashMap<String, Object> map = new HashMap<>();
        apiManager.get(TAG, ApiConstant.URL_REQUEST_MARKET_LIST, map, new MarketJsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    MarketInfo marketInfo = JSON.parseObject(response, MarketInfo.class);
                    if (marketInfo != null) {
                        ArrayList<MarketList> tempList = marketInfo.getData();
                        if (tempList != null && tempList.size() > 0) {
                            mMarketLists.clear();
                            mMarketLists.addAll(tempList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().updateAdapter();
            }
        });
    }

    @Override
    public void startTimer() {
        if (mMarketTimer == null) {
            mMarketTimer = new MarketTimer(15000, 1000);
        }
        mMarketTimer.start();
    }

    @Override
    public void stopTimer() {
        if (mMarketTimer != null) {
            mMarketTimer.cancel();
            mMarketTimer = null;
        }
    }

    public class MarketTimer extends CustomCountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MarketTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            getMarketList();
        }
    }

    @Override
    public void getMarketSocket() {
        // 网络请求：获取市场深度
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//设置允许失败重连
                .readTimeout(15, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(15, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(20, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        Request request = new Request.Builder().url(ApiConstant.URL_MARKET_LIST).build();
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }

    @Override
    public void closeSocket() {
        if (mSocketClient != null) {
            mSocketClient.close(1000, "");
            mSocketClient = null;
        }
    }

    public ArrayList<MarketList> getMarketLists() {
        return mMarketLists;
    }

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocketClient = webSocket;
            try {
//                mSocketClient.send("{\"msg\":\"nihao\"}");
//                mSocketClient.send("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, final String text) {
            super.onMessage(webSocket, text);
            parseMarketListData(text);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
        }
    }

    private void parseMarketListData(final String response) {
        executor.execute(new AsyncExecutor.Worker<ArrayList<MarketList>>() {
            @Override
            protected ArrayList<MarketList> doInBackground() {
                try {
                    if (!StringUtil.isEmpty(response)) {
                        ArrayList<MarketList> tempList = (ArrayList<MarketList>) JSON.parseArray(response, MarketList.class);
                        return tempList;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<MarketList> data) {
                if (data != null && data.size() > 0) {
                    mMarketLists.clear();
                    mMarketLists.addAll(data);
                }
                getView().updateAdapter();
            }
        });
    }
}
