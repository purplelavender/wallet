package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ForgotPasswordContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:36
 */
public interface ForgotPasswordContract {

    interface View extends BaseMVP.MvpView<ForgotPasswordPresenter> {

        void changeCodeText(String time);

        void onFindSuccess(String name);
    }

    interface Presenter {

        void getPhoneCode(String phone);

        void getMailCode(String mail);

        void findPasswordByPhoneMail(String name, String phone, String mail, String code, String password);

        void mnemonicFindPassword(String mnemonic, String password);
    }
}
