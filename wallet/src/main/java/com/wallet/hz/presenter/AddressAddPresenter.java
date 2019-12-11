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
 * @ClassName: AddressAddPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 16:08
 */
public class AddressAddPresenter extends BasePresenter<AddressAddContract.View> implements AddressAddContract.Presenter {

    private Context mContext;

    public AddressAddPresenter(AddressAddContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void addCoinAddress(String address, String remark) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("address", address);
        map.put("remarks", remark);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ADDRESS_SAVE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_add_success));
                getView().onAddSuccess();
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
