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
 * @ClassName: ModelRecordListPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:02
 */
public class ModelRecordListPresenter extends BasePresenter<ModelRecordListContract.View> implements ModelRecordListContract.Presenter {

    private Context mContext;
    private ArrayList<ExchangeInfo> dataList = new ArrayList<>();

    public ModelRecordListPresenter(ModelRecordListContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getExchangeList(boolean isFirst) {
        getList(ApiConstant.URL_EXCHANGE_LIST, isFirst, 1);
    }

    @Override
    public void getValueList(boolean isFirst) {
        getList(ApiConstant.URL_EXCHANGE_LIST, isFirst, 2);
    }

    @Override
    public void getJoinList(boolean isFirst) {
        getList(ApiConstant.URL_JOIN_LIST, isFirst, 0);
    }

    @Override
    public void getExtractList(boolean isFirst) {
        getList(ApiConstant.URL_MODEL_EXTRACT_LIST, isFirst, 0);
    }

    private void getList(String api, boolean isFirst, int type) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        if (type != 0) {
            map.put("type", type);
        }
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, api, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (page == 1) {
                    dataList.clear();
                }
                try {
                    ArrayList<ExchangeInfo> tempList = (ArrayList<ExchangeInfo>) JSON.parseArray(response, ExchangeInfo.class);
                    if (tempList != null && tempList.size() > 0) {
                        dataList.addAll(tempList);
                        page ++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().updateAdapter();
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    public ArrayList<ExchangeInfo> getDataList() {
        return dataList;
    }
}
