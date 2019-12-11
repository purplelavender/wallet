package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ProjectSubmitContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 19:20
 */
public interface ProjectSubmitContract {

    interface View extends BaseMVP.MvpView<ProjectSubmitPresenter> {

        void onSubmitSuccess();
    }

    interface Presenter {

        void submitProject(int type, String name, String web, String paper, String mail, String exchange, String detail);
    }
}
