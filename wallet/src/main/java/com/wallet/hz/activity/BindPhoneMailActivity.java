package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.BindPhoneMailContract;
import com.wallet.hz.presenter.BindPhoneMailPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: BindPhoneMailActivity
 * @Description: 绑定手机或者邮箱
 * @Author: ZL
 * @CreateDate: 2019/10/22 14:48
 */
public class BindPhoneMailActivity extends BaseAppMVPActivity<BindPhoneMailPresenter> implements BindPhoneMailContract.View {

    @BindView(R.id.et_phone_else)
    EditText etPhoneElse;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_phone_code)
    TextView tvPhoneCode;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.tv_phone_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_phone_sure)
    TextView tvSure;
    @BindView(R.id.ll_phone_country)
    LinearLayout llCountry;
    @BindView(R.id.tv_phone_country)
    TextView tvCountry;

    private boolean isPhone = true;
    private String countryCode = "";

    public static void startIntentResult(Activity activity, boolean isPhone, int requestCode) {
        Intent intent = new Intent(activity, BindPhoneMailActivity.class);
        intent.putExtra("isPhone", isPhone);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bind_phone_mail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        if (isPhone) {
            tvPhone.setText(getString(R.string.bind_phone_num));
            etPhone.setHint(getString(R.string.bind_phone_num_tag));
            tvPhoneCode.setText(getString(R.string.bind_sms_code));
            etPhoneCode.setHint(getString(R.string.bind_sms_code));
            llCountry.setVisibility(View.VISIBLE);
        } else {
            tvPhone.setText(getString(R.string.bind_mail_num));
            etPhone.setHint(getString(R.string.bind_mail_num_tag));
            tvPhoneCode.setText(getString(R.string.bind_mail_code));
            etPhoneCode.setHint(getString(R.string.bind_mail_code));
            llCountry.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initEvent() {
        setToolTitleClick(isPhone ? getString(R.string.bind_phone) : getString(R.string.bind_mail), null, null, null);
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeCheck();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindCheck();
            }
        });
        llCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryActivity.startIntentResult(BindPhoneMailActivity.this, 130);
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public BindPhoneMailPresenter injectDependencies() {
        isPhone = getIntent().getBooleanExtra("isPhone", true);
        return new BindPhoneMailPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 130) {
            String name = data.getStringExtra("name");
            countryCode = data.getStringExtra("code");
            tvCountry.setText(name + "（" + countryCode + "）");
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(BindPhoneMailActivity.this);
    }

    @Override
    public void setGetCodeText(String time) {
        if (tvGetCode != null) {
            tvGetCode.setText(time);
        }
    }

    @Override
    public void onBindSuccess() {
        String phone = etPhone.getText().toString();
        if (isPhone) {
            AppSpUtil.setUserMobile(mAppContext, phone);
        } else {
            AppSpUtil.setUserMail(mAppContext, phone);
        }
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 获取验证码验证
     */
    private void getCodeCheck() {
        if (!tvGetCode.getText().toString().contains("S")) {
            String phone = etPhone.getText().toString();
            if (isPhone) {
                if (StringUtil.isEmpty(countryCode)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_country_empty));
                    return;
                }
                if (!StringUtil.isContain("+86", countryCode)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_country_error));
                    return;
                }
            }
            if (StringUtil.isEmpty(phone)) {
                if (isPhone) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_empty));
                } else {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_empty));
                }
                return;
            } else {
                if (isPhone) {
                    getPresenter().getPhoneCode(phone);
                } else {
                    getPresenter().getMailCode(phone);
                }
            }
        }
    }

    /**
     * 绑定前验证
     */
    private void bindCheck() {
        String password = etPhoneElse.getText().toString();
        String phone = etPhone.getText().toString();
        String code = etPhoneCode.getText().toString();
        if (StringUtil.isEmpty(password)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
            return;
        }
        if (StringUtil.isEmpty(phone)) {
            if (isPhone) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_empty));
            } else {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_empty));
            }
            return;
        }
        if (StringUtil.isEmpty(code)) {
            if (isPhone) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_code_empty));
            } else {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_code_empty));
            }
            return;
        }
        if (isPhone) {
            getPresenter().bindPhone(password, phone, code);
        } else {
            getPresenter().bindMail(password, phone, code);
        }
    }
}
