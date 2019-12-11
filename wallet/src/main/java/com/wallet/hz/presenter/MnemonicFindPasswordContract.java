package com.wallet.hz.presenter;

import com.wallet.hz.model.UserInfo;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: MnemonicFindPasswordContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/10 15:32
 */
public interface MnemonicFindPasswordContract {

    interface View extends BaseMVP.MvpView<MnemonicFindPasswordPresenter> {

        void onFindSuccess(UserInfo userInfo);
    }

    interface Presenter {

        void mnemonicFindPassword(String mnemonic, String password);
    }
}
