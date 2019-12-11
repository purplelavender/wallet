package com.wallet.hz.presenter;

import com.wallet.hz.model.JoinInfo;
import com.wallet.hz.model.ModelInfo;
import com.wallet.hz.model.YuyueQueue;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: JoinContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:45
 */
public interface JoinContract {

    interface View extends BaseMVP.MvpView<JoinPresenter> {

        void onJoinSuccess(JoinInfo joinInfo);

        void onLookSuccess(YuyueQueue queue, boolean isClick);

        void onYuyueSuccess();

        void onChangeUI(ModelInfo modelInfo);
    }

    interface Presenter {

        void join(int type, String count, String password);

        void yuyue(int type, String count, String password);

        void lookDuiLie(boolean isClick);

        void getModelInfo();
    }
}
