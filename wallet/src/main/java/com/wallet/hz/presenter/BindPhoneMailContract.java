package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: BindPhoneMailContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 14:46
 */
public interface BindPhoneMailContract {

    interface View extends BaseMVP.MvpView<BindPhoneMailPresenter> {

        void setGetCodeText(String time);

        void onBindSuccess();
    }

    interface Presenter {

        void getPhoneCode(String phone);

        void getMailCode(String mail);

        void bindPhone(String password, String phone, String code);

        void bindMail(String password, String email, String code);
    }
}
