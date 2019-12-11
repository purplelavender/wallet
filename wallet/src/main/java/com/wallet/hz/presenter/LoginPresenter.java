package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.LoginInfo;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: LoginPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:32
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private Context mContext;

    public LoginPresenter(LoginContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void login(String userName, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", userName);
        map.put("password", password);
        apiManager.post(TAG, ApiConstant.URL_LOGIN, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                LoginInfo loginInfo = JSON.parseObject(response, LoginInfo.class);
                if (loginInfo != null) {
                    getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_login_success));
                    AppSpUtil.setUserToken(mContext, loginInfo.getToken());
                    getView().onLoginSuccess();
                }
            }

            @Override
            public void onNoLogin() {
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }
}
