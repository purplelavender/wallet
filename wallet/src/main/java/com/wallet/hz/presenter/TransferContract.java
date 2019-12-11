package com.wallet.hz.presenter;

import com.wallet.hz.model.CoinBalance;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: TransferContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 17:09
 */
public interface TransferContract {

    interface View extends BaseMVP.MvpView<TransferPresenter> {

        void onFeeSuccess(CoinBalance coinBalance);

        void onTransferSuccess();
    }

    interface Presenter {

        void getTransferFee(String name);

        void transfer(String name, String count, String address, String mark, String fee, String password);
    }
}
