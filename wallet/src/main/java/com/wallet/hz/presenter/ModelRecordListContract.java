package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ModelRecordListContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:02
 */
public interface ModelRecordListContract {

    interface View extends BaseMVP.MvpView<ModelRecordListPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getExchangeList(boolean isFirst);

        void getValueList(boolean isFirst);

        void getJoinList(boolean isFirst);

        void getExtractList(boolean isFirst);
    }
}
