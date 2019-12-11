package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.CoinDetail;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: WalletRecordPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 14:06
 */
public class WalletRecordPresenter extends BasePresenter<WalletRecordContract.View> implements WalletRecordContract.Presenter {

    private Context mContext;
    private ArrayList<CoinDetail> mWalletRecords = new ArrayList<>();

    public WalletRecordPresenter(WalletRecordContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getWalletCoinList() {
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
    public void getRecordList(int type, String name, boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("type", type);
        map.put("coinname", name.toLowerCase());
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_HISTORY_RECORD, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (page == 1) {
                    mWalletRecords.clear();
                }
                try {
                    ArrayList<CoinDetail> tempList = (ArrayList<CoinDetail>) JSON.parseArray(response, CoinDetail.class);
                    if (tempList != null && tempList.size() > 0) {
                        mWalletRecords.addAll(tempList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
                getView().updateAdapter();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().updateAdapter();
            }
        });
    }

    public ArrayList<CoinDetail> getWalletRecords() {
        return mWalletRecords;
    }
}
