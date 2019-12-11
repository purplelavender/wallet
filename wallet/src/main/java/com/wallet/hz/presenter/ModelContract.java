package com.wallet.hz.presenter;

import com.wallet.hz.model.ModelInfo;

import java.util.ArrayList;

import share.exchange.framework.base.BaseMVP;
import share.exchange.framework.bean.NoticeBean;

/**
 * @ClassName: ModelContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 14:30
 */
public interface ModelContract {

    interface View extends BaseMVP.MvpView<ModelPresenter> {

        void onNoticeSuccess(ArrayList<NoticeBean> noticeList);

        void onNoticeFailed();

        void onModelInfoSuccess(ModelInfo modelInfo);
    }

    interface Presenter {

        void getNoticeList();

        void getModelInfo();

        void getHsRate();
    }
}
