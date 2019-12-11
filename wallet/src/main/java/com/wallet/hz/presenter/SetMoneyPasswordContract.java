package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: SetMoneyPasswordContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 16:39
 */
public interface SetMoneyPasswordContract {

    interface View extends BaseMVP.MvpView<SetMoneyPasswordPresenter> {

        void setMoneySuccess();

        void setGetCodeText(String time);
    }

    interface Presenter {

        void getPhoneCode(String phone, boolean isForgot);

        void getMailCode(String mail, boolean isForgot);

        void setMoneyPassword(int type, String code, String moneyPassword);

        void forgotMoneyPassword(int type, String code, String moneyPassword);

        void mnemonicMoneyPassword(String moneyPassword);
    }
}
