package com.wallet.hz.presenter;

import com.wallet.hz.model.GroupInfo;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: MyGroupContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/29 15:11
 */
public interface MyGroupContract {

    interface View extends BaseMVP.MvpView<MyGroupPresenter> {

        void onGroupSuccess(GroupInfo groupInfo);

        void updateAdapter();
    }

    interface Presenter {

        void getGroupInfo();
    }
}
