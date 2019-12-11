package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ExchangeContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:44
 */
public interface ExchangeContract {

    interface View extends BaseMVP.MvpView<ExchangePresenter> {

        void onExchangeSuccess();

        void onchangeUI();
    }

    interface Presenter {
        void getCoinBalance();

        void exchange(String name, String startCount, String password);
    }
}
