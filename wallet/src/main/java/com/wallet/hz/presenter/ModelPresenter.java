package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.ModelInfo;
import com.wallet.hz.model.PriceRate;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.bean.NoticeBean;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ModelPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 14:31
 */
public class ModelPresenter extends BasePresenter<ModelContract.View> implements ModelContract.Presenter {

    private Context mContext;
    private ArrayList<NoticeBean> mNoticeBeans = new ArrayList<>();

    public ModelPresenter(ModelContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getNoticeList() {
        int type = 1;
        if (SpUtil.getLanguage(mContext) == LanguageEnums.ENGLISH.getKey()) {
            type = 1;
        } else if (SpUtil.getLanguage(mContext) == LanguageEnums.TRADITIONAL_CHINESE.getKey()) {
            type = 2;
        } else if (SpUtil.getLanguage(mContext) == LanguageEnums.SIMPLIFIED_CHINESE.getKey()) {
            type = 3;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("limit", 10);
        map.put("page", 1);
        map.put("type", type);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ANNOUNCEMENT_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    ArrayList<NoticeBean> tempList = (ArrayList<NoticeBean>) JSON.parseArray(response, NoticeBean.class);
                    if (tempList != null && tempList.size() > 0) {
                        mNoticeBeans.clear();
                        mNoticeBeans.addAll(tempList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().onNoticeSuccess(mNoticeBeans);
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().onNoticeFailed();
            }
        });
    }

    @Override
    public void getModelInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_INFO, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                ModelInfo modelInfo = JSON.parseObject(response, ModelInfo.class);
                getView().onModelInfoSuccess(modelInfo);
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().onModelInfoSuccess(null);
            }
        });
    }

    @Override
    public void getHsRate() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.get(TAG, ApiConstant.URL_MODEL_RATE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    PriceRate priceRates = JSON.parseObject(response, PriceRate.class);
                    AppSpUtil.setUserRate(mContext, priceRates.getPrice() + "");
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
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));

            }
        });
    }

    public ArrayList<NoticeBean> getNoticeBeans() {
        return mNoticeBeans;
    }
}
