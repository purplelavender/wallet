package com.wallet.hz.utils;

import android.app.Activity;

import com.wallet.hz.activity.LoginActivity;
import com.wallet.hz.dialog.LoginDialog;

import share.exchange.framework.manager.AppManager;

/**
 * @ClassName: LoginDialogUtil
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/08 17:21
 */
public class LoginDialogUtil {

    private static LoginDialogUtil instance;

    private LoginDialogUtil() {
    }

    public static LoginDialogUtil getInstance() {
        if (instance == null ) {
            instance = new LoginDialogUtil();
        }
        return instance;
    }

    private LoginDialog mLoginDialog;

    public void showLoginDialog(final Activity activity) {
        try {
            if (activity.isFinishing()) {
                LoginActivity.startIntent(activity);
                return;
            }
            if (mLoginDialog == null) {
                mLoginDialog = new LoginDialog(activity);
            }
            if (mLoginDialog.isShowing()) {
                return;
            } else {
                mLoginDialog.build().setOnViewClicked(new LoginDialog.OnViewClicked() {
                    @Override
                    public void onSure() {
                        AppSpUtil.logout(activity);
                        AppManager.getAppManager().appExit();
                        LoginActivity.startIntent(activity);
                    }
                });
                mLoginDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
