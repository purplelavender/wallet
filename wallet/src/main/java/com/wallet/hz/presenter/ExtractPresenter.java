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
 * @ClassName: ExtractPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:46
 */
public class ExtractPresenter extends BasePresenter<ExtractContract.View> implements ExtractContract.Presenter {

    private Context mContext;

    public ExtractPresenter(ExtractContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void extract(String count, String name, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("amount", count);
        map.put("name", name);
        map.put("jypassword", password);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_EXTRACT, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_extract_success));
                getView().onExtractSuccess();
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
