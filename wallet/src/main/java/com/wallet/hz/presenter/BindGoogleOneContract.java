package com.wallet.hz.presenter;

import android.graphics.Bitmap;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: BindGoogleOneContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 10:19
 */
public interface BindGoogleOneContract {

    interface View extends BaseMVP.MvpView<BindGoogleOnePresenter> {

        void onSecretSuccess(String secret);

        void onSecretImage(Bitmap bitmap);
    }

    interface Presenter {

        void getGoogleSecret();
    }
}
