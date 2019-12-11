package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
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
 * @ClassName: NoticeListPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:54
 */
public class NoticeListPresenter extends BasePresenter<NoticeListContract.View> implements NoticeListContract.Presenter {

    private Context mContext;
    private ArrayList<NoticeBean> mNoticeBeans = new ArrayList<>();

    public NoticeListPresenter(NoticeListContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    public ArrayList<NoticeBean> getNoticeBeans() {
        return mNoticeBeans;
    }

    @Override
    public void getNoticeList(boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        int type = 1;
        if (SpUtil.getLanguage(mContext) == LanguageEnums.ENGLISH.getKey()) {
            type = 1;
        } else if (SpUtil.getLanguage(mContext) == LanguageEnums.TRADITIONAL_CHINESE.getKey()) {
            type = 2;
        } else if (SpUtil.getLanguage(mContext) == LanguageEnums.SIMPLIFIED_CHINESE.getKey()) {
            type = 3;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("type", type);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ANNOUNCEMENT_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (page == 1) {
                    mNoticeBeans.clear();
                }
                try {
                    ArrayList<NoticeBean> tempList = (ArrayList<NoticeBean>) JSON.parseArray(response, NoticeBean.class);
                    if (tempList != null && tempList.size() > 0) {
                        mNoticeBeans.addAll(tempList);
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
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().onNoticeListFailed();
            }
        });
    }
}
