package com.wallet.hz.presenter;

import android.graphics.Bitmap;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: GatheringContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 15:11
 */
public interface GatheringContract {

    interface View extends BaseMVP.MvpView<GatheringPresenter> {

        void onAddressSuccess(String code);

        void onCreateSuccess(Bitmap bitmap);
    }

    interface Presenter {

        void createImage(String secret);
    }
}
