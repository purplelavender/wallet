package com.wallet.hz.presenter;

import com.wallet.hz.model.WalletInfo;

import java.util.ArrayList;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: WalletRecordContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 14:05
 */
public interface WalletRecordContract {

    interface View extends BaseMVP.MvpView<WalletRecordPresenter> {

        void updateAdapter();

        void onTypeListSuccess(ArrayList<WalletInfo> walletInfos);
    }

    interface Presenter {

        void getRecordList(int type, String name, boolean isFirst);

        void getWalletCoinList();
    }
}
