package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wallet.hz.R;
import com.wallet.hz.presenter.ChangePasswordContract;
import com.wallet.hz.presenter.ChangePasswordPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.manager.AppManager;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ChangePasswordActivity
 * @Description: 修改登录密码
 * @Author: ZL
 * @CreateDate: 2019/10/22 16:43
 */
public class ChangePasswordActivity extends BaseAppMVPActivity<ChangePasswordPresenter> implements ChangePasswordContract.View {

    @BindView(R.id.ll_password_old)
    LinearLayout llOldPassword;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_sure_password)
    EditText etSurePassword;

    private boolean isFind = false;

    public static void startIntentResult(Activity activity, boolean isFind, int requestCode) {
        Intent intent = new Intent(activity, ChangePasswordActivity.class);
        intent.putExtra("isFind", isFind);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_login_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        llOldPassword.setVisibility(isFind ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.password_change_password_title), null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public ChangePasswordPresenter injectDependencies() {
        isFind = getIntent().getBooleanExtra("isFind", false);
        return new ChangePasswordPresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_password_sure})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_password_sure:
                changeCheck();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ChangePasswordActivity.this);
    }

    @Override
    public void onChangeSuccess() {
        if (isFind) {
            setResult(RESULT_OK);
            finish();
        } else {
            AppManager.getAppManager().appExit();
            LoginActivity.startIntent(ChangePasswordActivity.this);
        }
    }

    /**
     * 页面输入修改
     */
    private void changeCheck() {
        String oldPassword = etOldPassword.getText().toString();
        String newPassWord = etNewPassword.getText().toString();
        String surePassword = etSurePassword.getText().toString();
        if (!isFind) {
            if (StringUtil.isEmpty(oldPassword)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
                return;
            }
            if (oldPassword.length() < 6 || oldPassword.length() > 16) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_length));
                return;
            }
            if (!StringUtil.isEqual(newPassWord, surePassword)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_sure));
                return;
            }
        }
        if (StringUtil.isEmpty(newPassWord)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
            return;
        }
        if (newPassWord.length() < 6 || newPassWord.length() > 16) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_length));
            return;
        }
        if (isFind) {
            String mnemonic = AppSpUtil.getUserMnemonic(mAppContext);
            getPresenter().changePasswordByMnemonic(mnemonic, newPassWord);
        } else {
            getPresenter().changePassword(oldPassword, newPassWord);
        }
    }
}
