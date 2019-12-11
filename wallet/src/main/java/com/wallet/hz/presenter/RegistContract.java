package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: RegistContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:34
 */
public interface RegistContract {

    interface View extends BaseMVP.MvpView<RegistPresenter> {

        void onGetZhujiciSuccess(String mnemonic);

        void onCheckSuccess(boolean isRepeat);

        void onCheckCodeSuccess();

        void showWarningDialog(String warningString);
    }

    interface Presenter {

        void checkName(String name);

        void getZhujici(String name);

        void checkInviteCode(String code);

        void getWarningString();
    }
}
