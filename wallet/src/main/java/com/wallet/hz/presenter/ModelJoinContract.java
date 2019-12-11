package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ModelJoinContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/04 10:24
 */
public interface ModelJoinContract {

    interface View extends BaseMVP.MvpView<ModelJoinPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getModelJoinList(boolean isFirst);
    }
}
