package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: NoticeListContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:54
 */
public interface NoticeListContract {

    interface View extends BaseMVP.MvpView<NoticeListPresenter> {

        void updateAdapter();

        void onNoticeListFailed();
    }

    interface Presenter {

        void getNoticeList(boolean isFirst);
    }
}
