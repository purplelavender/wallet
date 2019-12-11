package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: MnemonicCheckContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/31 17:08
 */
public interface MnemonicCheckContract {

    interface View extends BaseMVP.MvpView<MnemonicCheckPresenter> {

        void onRegistSuccess();

        void oncheckSuccess();
    }

    interface Presenter {

        void regist(String name, String password, String inviteCode, String mnemonicWord);

        void checkZhujici(String mnemonic);
    }
}
