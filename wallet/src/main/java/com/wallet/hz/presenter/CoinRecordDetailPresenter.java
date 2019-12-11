package com.wallet.hz.presenter;

import android.content.Context;

import share.exchange.framework.base.BasePresenter;

/**
 * @ClassName: CoinRecordDetailPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 14:47
 */
public class CoinRecordDetailPresenter extends BasePresenter<CoinRecordDetailContract.View> implements CoinRecordDetailContract.Presenter {

    private Context mContext;

    public CoinRecordDetailPresenter(CoinRecordDetailContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }
}
