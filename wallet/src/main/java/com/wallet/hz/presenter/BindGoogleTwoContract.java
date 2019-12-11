package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: BindGoogleTwoContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 11:04
 */
public interface BindGoogleTwoContract {

    interface View extends BaseMVP.MvpView<BindGoogleTwoPrsenter> {

        void changeCodeText(String time);

        void onBindGoogleSuccess();
    }

    interface Presenter {

        void getPhoneCode(String phone);

        void getMailCode(String mail);

        void bindGoogle(String secret, int type, String typeCode, String googleCode);
    }
}
