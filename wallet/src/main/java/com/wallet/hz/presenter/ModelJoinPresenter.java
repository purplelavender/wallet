package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.ExchangeInfo;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ModelJoinPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/04 10:24
 */
public class ModelJoinPresenter extends BasePresenter<ModelJoinContract.View> implements ModelJoinContract.Presenter {

    private Context mContext;
    private ArrayList<ExchangeInfo> mModelJoinInfos = new ArrayList<>();

    public ModelJoinPresenter(ModelJoinContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getModelJoinList(boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_JOIN_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                if (page == 1) {
                    mModelJoinInfos.clear();
                }
                try {
                    ArrayList<ExchangeInfo> tempList = (ArrayList<ExchangeInfo>) JSON.parseArray(response, ExchangeInfo.class);
                    if (tempList != null && tempList.size() > 0) {
                        mModelJoinInfos.addAll(tempList);
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

    public ArrayList<ExchangeInfo> getModelJoinInfos() {
        return mModelJoinInfos;
    }
}
