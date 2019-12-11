package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: GroupListContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/29 15:12
 */
public interface GroupListContract {

    interface View extends BaseMVP.MvpView<GroupListPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getGroupFriendList(String name);
    }
}
