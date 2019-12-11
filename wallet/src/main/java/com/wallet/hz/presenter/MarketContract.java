package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: MarketContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:11
 */
public interface MarketContract {

    interface View extends BaseMVP.MvpView<MarketPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getMarketList();

        void startTimer();

        void stopTimer();

        void getMarketSocket();

        void closeSocket();
    }
}
