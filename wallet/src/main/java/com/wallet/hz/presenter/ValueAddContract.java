package com.wallet.hz.presenter;

import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.model.WalletInfo;

import java.util.ArrayList;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ValueAddContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:44
 */
public interface ValueAddContract {

    interface View extends BaseMVP.MvpView<ValueAddPresenter> {

        void onTypeListSuccess(ArrayList<WalletInfo> walletInfos);

        void onValueSuccess();

        void getBalanceSuccess(CoinBalance balance);
    }

    interface Presenter {

        void getCoinList();

        void valueAdd(String name, String count, String targetCount, String password);

        void getCoinBalance(String name);
    }
}
