package com.wallet.hz.presenter;

import java.io.File;
import java.util.ArrayList;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: WorkOrderAddContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:18
 */
public interface WorkOrderAddContract {

    interface View extends BaseMVP.MvpView<WorkOrderAddPresenter> {

        void onAddOrEeplySuccess();

        void onImgUploadSuccess(String img);
    }

    interface Presenter {

        void uploadImg(ArrayList<File> files);

        void addWorkOrder(String title, String content, String mail, String imgs);

        void replyWorkOrder(String id, String content, String mail, String imgs);
    }
}
