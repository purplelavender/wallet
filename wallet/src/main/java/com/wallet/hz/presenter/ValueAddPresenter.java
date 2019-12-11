package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ValueAddPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:45
 */
public class ValueAddPresenter extends BasePresenter<ValueAddContract.View> implements ValueAddContract.Presenter {

   private Context mContext;

    public ValueAddPresenter(ValueAddContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getCoinList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_INFO, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    ArrayList<WalletInfo> tempList = (ArrayList<WalletInfo>) JSON.parseArray(response, WalletInfo.class);
                    getView().onTypeListSuccess(tempList);
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().onTypeListSuccess(null);
                }
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().onTypeListSuccess(null);
            }
        });
    }

    @Override
    public void valueAdd(String name, String count, String targetCount, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.toLowerCase());
        map.put("exchange_count", count);
        map.put("income_count", targetCount);
        map.put("jypassword", password);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_ADD_VALUE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_value_success));
                getView().onValueSuccess();
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
    public void getCoinBalance(String name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.toLowerCase());
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_COIN_BALANCE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    CoinBalance coinBalance = JSON.parseObject(response, CoinBalance.class);
                    getView().getBalanceSuccess(coinBalance);
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
