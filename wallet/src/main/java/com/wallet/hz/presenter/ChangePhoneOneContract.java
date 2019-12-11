package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ChangePhoneOneContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 15:35
 */
public interface ChangePhoneOneContract {

    interface View extends BaseMVP.MvpView<ChangePhoneOnePresenter> {

        void setGetCodeText(String time);

        void onCheckPhoneSuccess();
    }

    interface Presenter {

        void getPhoneCode(String phone);

        void updatePhone(String phone, String code);

        void updatePhoneSure(String phone, String code);
    }
}
