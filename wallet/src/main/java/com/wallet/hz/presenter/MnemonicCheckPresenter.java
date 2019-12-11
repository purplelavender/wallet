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
 * @ClassName: MnemonicCheckPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/31 17:08
 */
public class MnemonicCheckPresenter extends BasePresenter<MnemonicCheckContract.View> implements MnemonicCheckContract.Presenter {

    private Context mContext;

    public MnemonicCheckPresenter(MnemonicCheckContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void regist(String name, String password, String inviteCode, String mnemonicWord) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", name);
        map.put("password", password);
        map.put("parent_code", inviteCode);
        map.put("zhujici", mnemonicWord);
        apiManager.post(TAG, ApiConstant.URL_REGISTER, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_regist_success));
                getView().onRegistSuccess();
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
    public void checkZhujici(String mnemonic) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("zhujici", mnemonic);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ZHUJICI_CHECK, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().oncheckSuccess();
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
