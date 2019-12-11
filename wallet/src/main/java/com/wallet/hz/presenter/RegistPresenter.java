package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.MnemonicWord;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: RegistPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:34
 */
public class RegistPresenter extends BasePresenter<RegistContract.View> implements RegistContract.Presenter {

    private Context mContext;

    public RegistPresenter(RegistContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void checkName(String name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", name);
        apiManager.post(TAG, ApiConstant.URL_YANZHENG, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().onCheckSuccess(true);
            }

            @Override
            public void onNoLogin() {
                getView().onCheckSuccess(false);
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                if (statusCode == 300) {
                    getView().onCheckSuccess(false);
                } else {
                    getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                    getView().onCheckSuccess(true);
                }
            }
        });
    }

    @Override
    public void getZhujici(String name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", name);
        apiManager.get(TAG, ApiConstant.URL_ZHUJICI, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                MnemonicWord mnemonic = JSON.parseObject(response, MnemonicWord.class);
                getView().onGetZhujiciSuccess(mnemonic.getZhujici());
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
    public void checkInviteCode(String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        apiManager.post(TAG, ApiConstant.URL_YANZHENG_CODE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().onCheckCodeSuccess();
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
    public void getWarningString() {
        HashMap<String, Object> map = new HashMap<>();
        apiManager.post(TAG, ApiConstant.URL_POINTS, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
//                WarningString warningString = JSON.parseObject(response, WarningString.class);
//                getView().showWarningDialog(warningString.getWarningString());
                getView().showWarningDialog("");
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showWarningDialog(mContext.getString(R.string.dialog_warning_content));
            }
        });
    }
}
