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
 * @ClassName: ChangeMoneyPasswordPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/02 15:35
 */
public class ChangeMoneyPasswordPresenter extends BasePresenter<ChangeMoneyPasswordContract.View> implements ChangeMoneyPasswordContract.Presenter {

    private Context mContext;

    public ChangeMoneyPasswordPresenter(ChangeMoneyPasswordContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void changeMoneyPassword(String password, String newPassword) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("oldpassword", password);
        map.put("newpassword", newPassword);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_UPJY_PASSWORD, map, new JsonResHandler() {

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
