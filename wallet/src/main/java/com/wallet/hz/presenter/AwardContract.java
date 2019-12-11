package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: AwardContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 18:30
 */
public interface AwardContract {

    interface View extends BaseMVP.MvpView<AwardPresenter> {

        void onAwardSuccess(double award);

        void onDestruction(double destruction);
    }

    interface Presenter {

        void getPoolInfo();
    }
}
