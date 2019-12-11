package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: AddressAddContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 16:08
 */
public interface AddressAddContract {

    interface View extends BaseMVP.MvpView<AddressAddPresenter> {

        void onAddSuccess();
    }

    interface Presenter {

        void addCoinAddress(String address, String remark);
    }
}
