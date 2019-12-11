package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.utils.AppSpUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;

/**
 * @ClassName: CoinDetailPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 11:41
 */
public class CoinDetailPresenter extends BasePresenter<CoinDetailContract.View> implements CoinDetailContract.Presenter {

    private Context mContext;

    public CoinDetailPresenter(CoinDetailContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getCoinBalance(String name) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.toLowerCase());
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_COIN_BALANCE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    CoinBalance coinBalance = JSON.parseObject(response, CoinBalance.class);
                    getView().onchangeUI(coinBalance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().onchangeUI(null);
            }
        });
    }
}
