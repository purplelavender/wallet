package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ChangeMoneyPasswordContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/02 15:35
 */
public interface ChangeMoneyPasswordContract {

    interface View extends BaseMVP.MvpView<ChangeMoneyPasswordPresenter> {

        void onChangeSuccess();
    }

    interface Presenter {

        void changeMoneyPassword(String password, String newPassword);
    }
}
