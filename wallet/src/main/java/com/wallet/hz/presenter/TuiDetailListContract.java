package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: TuiDetailListContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/12/16 16:10
 */
public interface TuiDetailListContract {

    interface View extends BaseMVP.MvpView<TuiDetailListPresenter> {

        void updateAdapter();

        void changeTotalAmount(double total);
    }

    interface Presenter {

        void getTuiHsList(String data);

        void getTuiUsdtList(String data);

        void getNodeHsList(String data);
    }
}
