package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: LoginContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:32
 */
public interface LoginContract {

    interface View extends BaseMVP.MvpView<LoginPresenter> {

        void onLoginSuccess();

    }

    interface Presenter {

        void login(String userName, String password);

    }
}
