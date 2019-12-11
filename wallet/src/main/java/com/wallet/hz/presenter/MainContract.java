package com.wallet.hz.presenter;

import com.wallet.hz.model.UserInfo;
import com.wallet.hz.model.VersionInfo;

import java.io.File;

import share.exchange.framework.base.BaseMVP;

/**
 * @ClassName: MainContract
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 11:36
 */
public interface MainContract {

    interface View extends BaseMVP.MvpView<MainPresenter> {

        void onMineSuccess(UserInfo userInfo);

        void onVersionSuccess(VersionInfo versionInfo);

        void installNewApk(File file);
    }

    interface Presenter {

        void getCoinBalance();

        void getUserInfo();

        void checkVersion();

        void downApk(String url, String path);
    }
}
