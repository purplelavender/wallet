package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.TuiDetail;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: TuiDetailListPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/12/16 16:11
 */
public class TuiDetailListPresenter extends BasePresenter<TuiDetailListContract.View> implements TuiDetailListContract.Presenter {

    private Context mContext;
    private ArrayList<TuiDetail> mTuiDetails = new ArrayList<>();

    public TuiDetailListPresenter(TuiDetailListContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getTuiHsList(String data) {
        getDataList(ApiConstant.URL_TUI_HS_LIST, data);
    }

    @Override
    public void getTuiUsdtList(String data) {
        getDataList(ApiConstant.URL_TUI_USDT_LIST, data);
    }

    @Override
    public void getNodeHsList(String data) {
        getDataList(ApiConstant.URL_NODE_HS_LIST, data);
    }

    private void getDataList(String api, String data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("datas", data);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, api, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (page == 1) {
                    mTuiDetails.clear();
                }
                try {
                    ArrayList<TuiDetail> tempList = (ArrayList<TuiDetail>) JSON.parseArray(response, TuiDetail.class);
                    if (tempList != null && tempList.size() > 0) {
                        mTuiDetails.addAll(tempList);
                        page ++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onTotalUsdt(double usdt) {
                getView().changeTotalAmount(usdt);
            }

            @Override
            public void onNoLogin() {
                getView().updateAdapter();
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().updateAdapter();
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    public ArrayList<TuiDetail> getTuiDetails() {
        return mTuiDetails;
    }
}
