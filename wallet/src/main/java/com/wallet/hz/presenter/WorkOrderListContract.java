package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: WorkOrderListContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:15
 */
public interface WorkOrderListContract {

    interface View extends BaseMVP.MvpView<WorkOrderListPresenter> {

        void updateAdapter();

        void onOrderListFailed();
    }

    interface Presenter {

        void getOrderList(int userId);
    }
}
