package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ChangePasswordContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 16:39
 */
public interface ChangePasswordContract {

    interface View extends BaseMVP.MvpView<ChangePasswordPresenter> {

        void onChangeSuccess();
    }

    interface Presenter {

        void changePassword(String oldPassword, String newPassword);

        void changePasswordByMnemonic(String mnemonic, String password);
    }
}
