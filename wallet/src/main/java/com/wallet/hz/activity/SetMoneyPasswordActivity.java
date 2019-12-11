package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.SetMoneyPasswordContract;
import com.wallet.hz.presenter.SetMoneyPasswordPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: SetMoneyPasswordActivity
 * @Description: 设置资金密码
 * @Author: ZL
 * @CreateDate: 2019/10/22 16:53
 */
public class SetMoneyPasswordActivity extends BaseAppMVPActivity<SetMoneyPasswordPresenter> implements SetMoneyPasswordContract.View {

    @BindView(R.id.ll_money_else)
    LinearLayout llMoneyElse;
    @BindView(R.id.tv_money_else)
    TextView tvMoneyElse;
    @BindView(R.id.et_money_else)
    EditText etMoneyElse;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_money_password)
    EditText etMoneyPassword;
    @BindView(R.id.et_sure_password)
    EditText etSurePassword;

    private boolean isNormal = true, isForgot = false;
    private String phone, mail, mnemonic;
    private boolean isBindPhone = false, isBindMail = false, isBindGoogle = false;

    public static void startIntentResult(Activity activity, boolean isNormal, boolean isForgot, String mnemonic, int requestCode) {
        Intent intent = new Intent(activity, SetMoneyPasswordActivity.class);
        intent.putExtra("isNormal", isNormal);
        intent.putExtra("isForgot", isForgot);
        intent.putExtra("mnemonic", mnemonic);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_set_money_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        phone = AppSpUtil.getUserMobile(mAppContext);
        mail = AppSpUtil.getUserMail(mAppContext);
        isBindPhone = !StringUtil.isEmpty(phone);
        isBindMail = !StringUtil.isEmpty(mail);
        isBindGoogle = AppSpUtil.getUserGoogle(mAppContext);
        if (isNormal || isForgot) {
            llMoneyElse.setVisibility(View.VISIBLE);
            if (isBindGoogle) {
                tvMoneyElse.setText(getString(R.string.bind_google_code));
                etMoneyElse.setHint(getString(R.string.bind_google_code_tag));
                etMoneyElse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                tvGetCode.setVisibility(View.GONE);
            } else {
                if (isBindPhone) {
                    tvMoneyElse.setText(getString(R.string.bind_sms_code));
                    etMoneyElse.setHint(getString(R.string.bind_sms_code));
                    etMoneyElse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    tvGetCode.setVisibility(View.VISIBLE);
                } else {
                    if (isBindMail) {
                        tvMoneyElse.setText(getString(R.string.bind_mail_code));
                        etMoneyElse.setHint(getString(R.string.bind_mail_code));
                        etMoneyElse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                        tvGetCode.setVisibility(View.VISIBLE);
                    } else {
                        tvMoneyElse.setText(getString(R.string.bind_login_password));
                        etMoneyElse.setHint(getString(R.string.bind_login_password_tag));
                        etMoneyElse.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        tvGetCode.setVisibility(View.GONE);
                    }
                }
            }
        } else {
            llMoneyElse.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(isForgot ? getString(R.string.login_forgot_password) : getString(R.string.password_money_password), null, null, null);
        if (isForgot) {
            setToolRightClick(getString(R.string.password_zhujici_find), null, null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mnemonic = AppSpUtil.getUserMnemonic(mAppContext);
                    MnemonicCheckActivity.startIntentResult(SetMoneyPasswordActivity.this, true, false, mnemonic, "", "", "", 118);
                }
            });
        }
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public SetMoneyPasswordPresenter injectDependencies() {
        isNormal = getIntent().getBooleanExtra("isNormal", true);
        isForgot = getIntent().getBooleanExtra("isForgot", false);
        mnemonic = getIntent().getStringExtra("mnemonic");
        return new SetMoneyPasswordPresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_get_code, R.id.tv_password_sure})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_get_code:
                if (!tvGetCode.getText().toString().contains("S")) {
                    if (isBindPhone) {
                        getPresenter().getPhoneCode(phone, isForgot);
                    } else {
                        if (isBindMail) {
                            getPresenter().getMailCode(mail, isForgot);
                        }
                    }
                }
                break;
            case R.id.tv_password_sure:
                checkEdit();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(SetMoneyPasswordActivity.this);
    }

    @Override
    public void setMoneySuccess() {
        AppSpUtil.setUserAvatar(mAppContext, true);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setGetCodeText(String time) {
        if (tvGetCode != null) {
            tvGetCode.setText(time);
        }
    }

    /**
     * 验证输入的信息
     */
    private void checkEdit() {
        String code = etMoneyElse.getText().toString();
        String password = etMoneyPassword.getText().toString();
        String surePassword = etSurePassword.getText().toString();
        if (password.length() < 6 || password.length() > 16) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.password_money_password_tag));
            return;
        }
        if (!StringUtil.isEqual(password, surePassword)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_sure));
            return;
        }
        if (isNormal || isForgot) {
            if (StringUtil.isEmpty(code)) {
                if (isBindGoogle) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_google_empty));
                    return;
                } else {
                    if (isBindPhone) {
                        showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_code_empty));
                        return;
                    } else {
                        if (isBindMail) {
                            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_code_empty));
                            return;
                        } else {
                            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
                            return;
                        }
                    }
                }
            }
        }
        if (isNormal) {
            if (isBindGoogle) {
                getPresenter().setMoneyPassword(1, code, password);
            } else {
                if (isBindPhone) {
                    getPresenter().setMoneyPassword(2, code, password);
                } else {
                    if (isBindMail) {
                        getPresenter().setMoneyPassword(3, code, password);
                    } else {
                        if (StringUtil.isEmpty(password)) {
                            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
                            return;
                        }
                        getPresenter().setMoneyPassword(4, code, password);
                    }
                }
            }
        } else if (isForgot) {
            if (isBindGoogle) {
                getPresenter().forgotMoneyPassword(1, code, password);
            } else {
                if (isBindPhone) {
                    getPresenter().forgotMoneyPassword(2, code, password);
                } else {
                    if (isBindMail) {
                        getPresenter().forgotMoneyPassword(3, code, password);
                    } else {
                        getPresenter().forgotMoneyPassword(4, code, password);
                    }
                }
            }
        } else {
            getPresenter().mnemonicMoneyPassword(password);
        }
    }
}
