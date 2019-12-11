package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.wallet.hz.R;
import com.wallet.hz.presenter.ChangeMoneyPasswordContract;
import com.wallet.hz.presenter.ChangeMoneyPasswordPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ChangeMoneyPasswordActivity
 * @Description: 修改资金密码页面
 * @Author: ZL
 * @CreateDate: 2019/11/02 15:46
 */
public class ChangeMoneyPasswordActivity extends BaseAppMVPActivity<ChangeMoneyPasswordPresenter> implements ChangeMoneyPasswordContract.View {

    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_sure_password)
    EditText etSurePassword;

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ChangeMoneyPasswordActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_money_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.password_money_password), null, null, null);
        setToolRightClick(getString(R.string.login_forgot_password), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetMoneyPasswordActivity.startIntentResult(ChangeMoneyPasswordActivity.this, false, true, "", 117);
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public ChangeMoneyPasswordPresenter injectDependencies() {
        return new ChangeMoneyPasswordPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ChangeMoneyPasswordActivity.this);
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
    public void onChangeSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 页面输入修改
     */
    private void changeCheck() {
        String oldPassword = etOldPassword.getText().toString();
        String newPassWord = etNewPassword.getText().toString();
        String surePassword = etSurePassword.getText().toString();
        if (StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newPassWord)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
            return;
        }
        if (oldPassword.length() < 6 || oldPassword.length() > 16 || newPassWord.length() < 6 || newPassWord.length() > 16) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_length));
            return;
        }
        if (!StringUtil.isEqual(newPassWord, surePassword)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_sure));
            return;
        }
        getPresenter().changeMoneyPassword(oldPassword, newPassWord);
    }
}
