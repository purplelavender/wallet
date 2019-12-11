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
 * @ClassName: TransferPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 17:09
 */
public class TransferPresenter extends BasePresenter<TransferContract.View> implements TransferContract.Presenter {

    private Context mContext;

    public TransferPresenter(TransferContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getTransferFee(String name) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.toLowerCase());
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_COIN_BALANCE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    CoinBalance coinBalance = JSON.parseObject(response, CoinBalance.class);
                    getView().onFeeSuccess(coinBalance);
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
                getView().onFeeSuccess(null);
            }
        });
    }

    @Override
    public void transfer(String name, String count, String address, String mark, String fee, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("amount", count);
        map.put("address", address);
        map.put("coinname", name.toLowerCase());
        map.put("remarks", mark);
        map.put("fee", fee);
        map.put("jypassword", password);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_TRANSFER, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_transfer_success));
                getView().onTransferSuccess();
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
}
