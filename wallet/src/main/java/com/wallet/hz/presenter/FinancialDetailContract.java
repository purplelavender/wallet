package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: FinancialDetailContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 09:35
 */
public interface FinancialDetailContract {

    interface View extends BaseMVP.MvpView<FinancialDetailPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getFinancialList(int type, boolean isFirst);
    }
}
