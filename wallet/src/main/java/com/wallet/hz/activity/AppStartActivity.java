package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wallet.hz.R;
import com.wallet.hz.utils.AppSpUtil;

import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.SpUtil;

/**
 * @ClassName: AppStartActivity
 * @Description: 应用开始页面
 * @Author: ZL
 * @CreateDate: 2019/11/06 11:36
 */
public class AppStartActivity extends BaseAppActivity {

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, AppStartActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        // 解决重启问题
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return 0;
        }
        // 设置程序语言
        int language = SpUtil.getLanguage(AppStartActivity.this);
        EnvironmentUtil.changeAppLanguage(AppStartActivity.this, LanguageEnums.getLanguageByKye(language));
        return R.layout.activity_start;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showTitleBar(false);
        showSuccessStateLayout();
        if (AppSpUtil.isLogin(mAppContext)) {
            MainActivity.startIntent(AppStartActivity.this, 0);
        } else {
            LoginActivity.startIntent(AppStartActivity.this);
        }
        finish();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initCurrentData() {

    }
}
