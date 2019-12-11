package com.wallet.hz.presenter;

import android.content.Context;

import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ChangePasswordPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 16:39
 */
public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.View> implements ChangePasswordContract.Presenter {

    private Context mContext;

    public ChangePasswordPresenter(ChangePasswordContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("oldpassword", oldPassword);
        map.put("newpassword", newPassword);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_UPLOGIN_PASSWORD, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_change_success));
                getView().onChangeSuccess();
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
    public void changePasswordByMnemonic(String mnemonic, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("zhujici", mnemonic);
        map.put("newpassword", password);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ZHUJICI_ZHAOHUI_PASSWORD, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_change_success));
                getView().onChangeSuccess();
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
}
