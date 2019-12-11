package com.wallet.hz.presenter;

import com.wallet.hz.model.CoinBalance;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: CoinDetailContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 11:40
 */
public interface CoinDetailContract {

    interface View extends BaseMVP.MvpView<CoinDetailPresenter> {

        void onchangeUI(CoinBalance coinBalance);
    }

    interface Presenter {

        void getCoinBalance(String name);
    }
}
