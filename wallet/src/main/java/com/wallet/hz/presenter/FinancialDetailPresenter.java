package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.FinancialDetailInfo;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: FinancialDetailPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 09:35
 */
public class FinancialDetailPresenter extends BasePresenter<FinancialDetailContract.View> implements FinancialDetailContract.Presenter {

    private Context mContext;
    private ArrayList<FinancialDetailInfo> mFinancialDetailInfos = new ArrayList<>();
    private int lastType = 0;

    public FinancialDetailPresenter(FinancialDetailContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getFinancialList(final int type, boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_FINANCIAL_DETAIL_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (page == 1 || lastType != type) {
                    mFinancialDetailInfos.clear();
                }
                try {
                    ArrayList<FinancialDetailInfo> tempList = (ArrayList<FinancialDetailInfo>) JSON.parseArray(response, FinancialDetailInfo.class);
                    if (tempList != null && tempList.size() > 0) {
                        mFinancialDetailInfos.addAll(tempList);
                        page ++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lastType = type;
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

    public ArrayList<FinancialDetailInfo> getFinancialDetailInfos() {
        return mFinancialDetailInfos;
    }
}
