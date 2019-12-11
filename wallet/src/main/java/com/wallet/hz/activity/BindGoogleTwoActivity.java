package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.BindGoogleTwoContract;
import com.wallet.hz.presenter.BindGoogleTwoPrsenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: BindGoogleTwoActivity
 * @Description: 绑定谷歌第二步页面
 * @Author: ZL
 * @CreateDate: 2019/10/22 11:06
 */
public class BindGoogleTwoActivity extends BaseAppMVPActivity<BindGoogleTwoPrsenter> implements BindGoogleTwoContract.View {

    @BindView(R.id.et_google_code)
    EditText etGoogleCode;
    @BindView(R.id.tv_google_else)
    TextView tvGoogleElse;
    @BindView(R.id.et_google_else)
    EditText etGoogleElse;
    @BindView(R.id.tv_google_get_code)
    TextView tvGetCode;

    private String secret, phone, mail;
    private boolean isBindPhone = false, isBindMail = false;

    public static void startIntentResult(Activity activity, String secret, int requestCode) {
        Intent intent = new Intent(activity, BindGoogleTwoActivity.class);
        intent.putExtra("secret", secret);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bind_google_two;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        if (isBindPhone) {
            tvGoogleElse.setText(getString(R.string.bind_sms_code));
            etGoogleElse.setHint(getString(R.string.bind_sms_code));
            etGoogleElse.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
            tvGetCode.setVisibility(View.VISIBLE);
        } else {
            if (isBindMail) {
                tvGoogleElse.setText(getString(R.string.bind_mail_code));
                etGoogleElse.setHint(getString(R.string.bind_mail_code));
                etGoogleElse.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                tvGetCode.setVisibility(View.VISIBLE);
            } else {
                tvGoogleElse.setText(getString(R.string.bind_login_password));
                etGoogleElse.setHint(getString(R.string.bind_login_password_tag));
                etGoogleElse.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                tvGetCode.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.bind_google), null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public BindGoogleTwoPrsenter injectDependencies() {
        secret = getIntent().getStringExtra("secret");
        phone = AppSpUtil.getUserMobile(mAppContext);
        mail = AppSpUtil.getUserMail(mAppContext);
        isBindPhone = !StringUtil.isEmpty(phone);
        isBindMail = !StringUtil.isEmpty(mail);
        return new BindGoogleTwoPrsenter(this, mAppContext);
    }

    @OnClick({R.id.tv_google_get_code, R.id.tv_google_sure, R.id.tv_google_paste})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_google_get_code:
                if (!tvGetCode.getText().toString().contains("S")) {
                    if (isBindPhone) {
                        getPresenter().getPhoneCode(phone);
                    } else {
                        if (isBindMail) {
                            getPresenter().getMailCode(mail);
                        }
                    }
                }
                break;
            case R.id.tv_google_sure:
                checkEdit();
                break;
            case R.id.tv_google_paste:
                String paste = EnvironmentUtil.pasteContent(mAppContext);
                etGoogleCode.setText(paste);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(BindGoogleTwoActivity.this);
    }

    @Override
    public void changeCodeText(String time) {
        if (tvGetCode != null) {
            tvGetCode.setText(time);
        }
    }

    @Override
    public void onBindGoogleSuccess() {
        AppSpUtil.setUserGoogle(mAppContext, true);
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 验证输入的信息
     */
    private void checkEdit() {
        String googleCode = etGoogleCode.getText().toString();
        String otherCode = etGoogleElse.getText().toString();
        if (StringUtil.isEmpty(googleCode)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_google_empty));
            return;
        }
        if (isBindPhone) {
            if (StringUtil.isEmpty(otherCode)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_code_empty));
                return;
            }
        } else {
            if (isBindMail) {
                if (StringUtil.isEmpty(otherCode)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_code_empty));
                    return;
                }
            } else {
                if (StringUtil.isEmpty(otherCode)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
                    return;
                }
            }
        }
        if (isBindPhone) {
            getPresenter().bindGoogle(secret, 2, otherCode, googleCode);
        } else {
            if (isBindMail) {
                getPresenter().bindGoogle(secret, 3, otherCode, googleCode);
            } else {
                getPresenter().bindGoogle(secret, 4, otherCode, googleCode);
            }
        }
    }
}
