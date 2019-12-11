package com.wallet.hz.presenter;

import com.wallet.hz.model.ServiceAgreement;

import java.util.ArrayList;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ServiceAgreementContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/11 16:27
 */
public interface ServiceAgreementContract {

    interface View extends BaseMVP.MvpView<ServiceAgreementPresenter> {

        void onAgreeSuccess(ArrayList<ServiceAgreement> serviceAgreementList);
    }

    interface Presenter {

        void getServiceAgreement();
    }
}
