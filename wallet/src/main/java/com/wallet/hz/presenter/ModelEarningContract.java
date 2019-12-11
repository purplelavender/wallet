package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ModelEarningContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/04 11:10
 */
public interface ModelEarningContract {

    interface View extends BaseMVP.MvpView<ModelEarningPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getModelEarningList(boolean isFirst);
    }
}
