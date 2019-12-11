package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ProjectApplyListContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 17:27
 */
public interface ProjectApplyListContract {

    interface View extends BaseMVP.MvpView<ProjectApplyListPresenter> {

        void updateAdapter();
    }

    interface Presenter {

        void getProjectList(boolean isFirst);
    }
}
