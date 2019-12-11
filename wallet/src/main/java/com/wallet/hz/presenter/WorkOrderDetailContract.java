package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: WorkOrderDetailContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:15
 */
public interface WorkOrderDetailContract {

    interface View extends BaseMVP.MvpView<WorkOrderDetailPresenter> {

        void updateAdapter();

        void onEndOrderSuccess();
    }

    interface Presenter {

        void endOrder(String id);
    }
}
