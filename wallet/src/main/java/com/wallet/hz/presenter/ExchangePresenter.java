package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ExchangePresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:44
 */
public class ExchangePresenter extends BasePresenter<ExchangeContract.View> implements ExchangeContract.Presenter {

    private Context mContext;

    public ExchangePresenter(ExchangeContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void exchange(String name, String startCount, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("exchange_count", startCount);
        map.put("jypassword", password);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_EXCHANGE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_exchange_success));
                getView().onExchangeSuccess();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    @Override
    public void getCoinBalance() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name", "hs");
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_COIN_BALANCE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    CoinBalance coinBalance = JSON.parseObject(response, CoinBalance.class);
                    if (coinBalance != null) {
                        AppSpUtil.setUserRate(mContext, coinBalance.getHuilv() + "");
                        AppSpUtil.setUserAmount(mContext, coinBalance.getBalance() + "");
                    }
                    getView().onchangeUI();
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
            }
        });
    }
}
