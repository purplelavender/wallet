package com.wallet.hz.presenter;

import com.wallet.hz.model.WalletToatl;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: WalletContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 14:30
 */
public interface WalletContract {

    interface View extends BaseMVP.MvpView<WalletPresenter> {

        void onWalletSuccess(WalletToatl walletInfo);

        void updateAdapter();
    }

    interface Presenter {

        void getWalletInfo();

        void getWalletCoinList();
    }
}
