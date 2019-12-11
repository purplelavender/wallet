package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.model.WalletToatl;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: WalletPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 14:31
 */
public class WalletPresenter extends BasePresenter<WalletContract.View> implements WalletContract.Presenter {

    private Context mContext;
    private ArrayList<WalletInfo> mWalletInfos = new ArrayList<>();

    public WalletPresenter(WalletContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getWalletInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_TOTAL, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                try {
                    WalletToatl walletInfo = JSON.parseObject(response, WalletToatl.class);
                    getView().onWalletSuccess(walletInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().onWalletSuccess(null);
                }
            }

            @Override
            public void onNoLogin() {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().onWalletSuccess(null);
            }
        });
    }

    @Override
    public void getWalletCoinList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_INFO, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                try {
                    ArrayList<WalletInfo> tempList = (ArrayList<WalletInfo>) JSON.parseArray(response, WalletInfo.class);
                    if (tempList != null && tempList.size() > 0) {
                        mWalletInfos.clear();
                        mWalletInfos.addAll(tempList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onNoLogin() {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                getView().showLoginDialog();
                getView().updateAdapter();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().updateAdapter();
            }
        });
    }

    public ArrayList<WalletInfo> getWalletInfos() {
        return mWalletInfos;
    }
}
