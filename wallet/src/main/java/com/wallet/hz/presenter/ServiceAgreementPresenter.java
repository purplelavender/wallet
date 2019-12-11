package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.ServiceAgreement;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ServiceAgreementPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/11 16:28
 */
public class ServiceAgreementPresenter extends BasePresenter<ServiceAgreementContract.View> implements ServiceAgreementContract.Presenter {

    private Context mContext;

    public ServiceAgreementPresenter(ServiceAgreementContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getServiceAgreement() {
        HashMap<String, Object> map = new HashMap<>();
        apiManager.post(TAG, ApiConstant.URL_SERVICE_AGREEMENT, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    ArrayList<ServiceAgreement> serviceAgreement = (ArrayList<ServiceAgreement>) JSON.parseArray(response, ServiceAgreement.class);
                    AppSpUtil.saveServiceInfo(mContext, serviceAgreement);
                    getView().onAgreeSuccess(serviceAgreement);
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
