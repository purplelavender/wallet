package com.wallet.hz;

import android.app.Application;
import android.content.Context;

import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.SpUtil;

/**
 * @ClassName: com.wallet.hz.WalletApplication
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 11:44
 */
public class WalletApplication extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();

        initConfig();
    }

    private void initConfig() {
        // 设置程序语言
        int language = SpUtil.getLanguage(appContext);
        EnvironmentUtil.changeAppLanguage(appContext, LanguageEnums.getLanguageByKye(language));
    }
}
