package com.wallet.hz.presenter;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: AddressBenContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 16:08
 */
public interface AddressBenContract {

    interface View extends BaseMVP.MvpView<AddressBenPresenter> {

        void updateAdapter();

        void onDeleteSuccess();
    }

    interface Presenter {

        void getAddressList(boolean isFirst);

        void deleteAddress(int id);
    }
}
