package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: CoinFragmentContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 11:41
 */
public interface CoinFragmentContract {

    interface View extends BaseMVP.MvpView<CoinFragmentPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getCoinDetailList(int type, String name, boolean isFirst);
    }
}
