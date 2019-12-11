package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: ExtractContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 14:46
 */
public interface ExtractContract {

    interface View extends BaseMVP.MvpView<ExtractPresenter> {

        void onExtractSuccess();

    }

    interface Presenter {

        void extract(String count, String name, String password);
    }
}
