package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.JoinInfo;
import com.wallet.hz.model.ModelInfo;
import com.wallet.hz.model.YuyueQueue;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: JoinPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:46
 */
public class JoinPresenter extends BasePresenter<JoinContract.View> implements JoinContract.Presenter {

    private Context mContext;

    public JoinPresenter(JoinContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void join(int type, String count, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("amount", count);
        map.put("jypassword", password);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_JOIN, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    JoinInfo joinInfo = JSON.parseObject(response, JoinInfo.class);
                    if (!joinInfo.isNeedYuYue()) {
                        getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_join_success));
                    }
                    getView().onJoinSuccess(joinInfo);
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

    @Override
    public void yuyue(int type, String count, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("amount", count);
        map.put("jypassword", password);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_PAIDUI, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_yuyue_success));
                getView().onYuyueSuccess();
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

    @Override
    public void lookDuiLie(final boolean isClick) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_LOOK_PAIDUI, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    YuyueQueue queue = JSON.parseObject(response, YuyueQueue.class);
                    getView().onLookSuccess(queue, isClick);
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

    @Override
    public void getModelInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_INFO, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    ModelInfo modelInfo = JSON.parseObject(response, ModelInfo.class);
                    getView().onChangeUI(modelInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().onChangeUI(null);
                }
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().onChangeUI(null);
            }
        });
    }
}
