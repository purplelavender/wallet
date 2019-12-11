package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.UserInfo;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: MnemonicFindPasswordPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/10 15:32
 */
public class MnemonicFindPasswordPresenter extends BasePresenter<MnemonicFindPasswordContract.View> implements MnemonicFindPasswordContract.Presenter {

    private Context mContext;

    public MnemonicFindPasswordPresenter(MnemonicFindPasswordContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void mnemonicFindPassword(String mnemonic, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("zhujici", mnemonic);
        map.put("password", password);
        apiManager.post(TAG, ApiConstant.URL_ZHUJICI_ZHAOHUI_PASSWORD, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    UserInfo userInfo = JSON.parseObject(response, UserInfo.class);
                    getView().onFindSuccess(userInfo);
                } catch (Exception e) {
                    e.printStackTrace();
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
