package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.CoinDetail;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: CoinFragmentPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 11:41
 */
public class CoinFragmentPresenter extends BasePresenter<CoinFragmentContract.View> implements CoinFragmentContract.Presenter {

    private Context mContext;
    private ArrayList<CoinDetail> mCoinDetails = new ArrayList<>();

    public CoinFragmentPresenter(CoinFragmentContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getCoinDetailList(int type, String name, boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("type", type);
        map.put("coinname", name.toLowerCase());
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_COIN_DETAIL_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                if (page == 1) {
                    mCoinDetails.clear();
                }
                try {
                    ArrayList<CoinDetail> tempList = (ArrayList<CoinDetail>) JSON.parseArray(response, CoinDetail.class);
                    if (tempList != null && tempList.size() > 0) {
                        mCoinDetails.addAll(tempList);
                        page ++;
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

    public ArrayList<CoinDetail> getCoinDetails() {
        return mCoinDetails;
    }
}
