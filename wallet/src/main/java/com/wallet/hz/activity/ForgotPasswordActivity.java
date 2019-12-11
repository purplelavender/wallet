package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.ForgotPasswordContract;
import com.wallet.hz.presenter.ForgotPasswordPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ForgotPasswordActivity
 * @Description: 忘记密码页面
 * @Author: ZL
 * @CreateDate: 2019/10/23 17:06
 */
public class ForgotPasswordActivity extends BaseAppMVPActivity<ForgotPasswordPresenter> implements ForgotPasswordContract.View {

    @BindView(R.id.tv_mobile)
    TextView tvPhone;
    @BindView(R.id.view_mobile)
    View viewPhone;
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.view_mail)
    View viewMail;
    @BindView(R.id.tv_mnemonic)
    TextView tvMnemonic;
    @BindView(R.id.view_mnemonic)
    View viewMnemonic;

    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.et_forgot_name)
    EditText etName;

    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.et_forgot_phone)
    EditText etPhone;
    @BindView(R.id.tv_phone_country)
    TextView tvCountry;

    @BindView(R.id.ll_mail)
    LinearLayout llMail;
    @BindView(R.id.et_forgot_mail)
    EditText etMail;

    @BindView(R.id.ll_mnemonic)
    LinearLayout llMnemonic;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.et_forgot_mnemonic)
    EditText etMnemonic;

    @BindView(R.id.tv_forgot_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_forgot_code)
    EditText etCode;
    @BindView(R.id.et_forgot_password)
    EditText etPassword;
    @BindView(R.id.et_forgot_sure_password)
    EditText etSurePassword;
    @BindView(R.id.cb_password)
    CheckBox cbPassword;
    @BindView(R.id.cb_sure_password)
    CheckBox cbSurePassword;

    private String countryCode = "";
    private boolean isPhone = true;
    private boolean isMail = false;
    private boolean isMnemonic = false;

    public static void startIntent(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ForgotPasswordActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        changeTabUI(true, false, false);
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String password = etPassword.getText().toString();
                if (isChecked) {
                    etPassword.setText(password);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        cbSurePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String password = etSurePassword.getText().toString();
                if (isChecked) {
                    etSurePassword.setText(password);
                    etSurePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    etSurePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public ForgotPasswordPresenter injectDependencies() {
        return new ForgotPasswordPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 133 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            countryCode = data.getStringExtra("code");
            tvCountry.setText(countryCode);
        }
    }

    @OnClick({R.id.ll_tab_phone, R.id.ll_tab_mail, R.id.ll_tab_mnemonic, R.id.tv_forgot_get_code, R.id.tv_change, R.id.tv_phone_country})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_tab_mnemonic:
                changeTabUI(false, false, true);
                break;
            case R.id.ll_tab_phone:
                changeTabUI(true, false, false);
                break;
            case R.id.ll_tab_mail:
                changeTabUI(false, true, false);
                break;
            case R.id.tv_forgot_get_code:
                getCodeCheck();
                break;
            case R.id.tv_change:
                changeCheck();
                break;
            case R.id.tv_phone_country:
                CountryActivity.startIntentResult(ForgotPasswordActivity.this, 133);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ForgotPasswordActivity.this);
    }


    @Override
    public void changeCodeText(String time) {
        if (tvGetCode != null) {
            tvGetCode.setText(time);
        }
    }

    @Override
    public void onFindSuccess(String name) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * tab选择切换
     *
     * @param isPhone
     */
    private void changeTabUI(boolean isPhone, boolean isMail, boolean isMnemonic) {
        this.isPhone = isPhone;
        this.isMail = isMail;
        this.isMnemonic = isMnemonic;
        tvPhone.setTextColor(isPhone ? ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color) : ResourcesUtil.getColor(mAppContext, R
                .color.tab_normal_color));
        viewPhone.setBackgroundColor(isPhone ? ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color) : ResourcesUtil.getColor
                (mAppContext, R.color.tab_normal_color));
        tvMail.setTextColor(isMail ? ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color) : ResourcesUtil.getColor(mAppContext, R.color
                .tab_normal_color));
        viewMail.setBackgroundColor(isMail ? ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color) : ResourcesUtil.getColor(mAppContext,
                R.color.tab_normal_color));
        tvMnemonic.setTextColor(isMnemonic ? ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color) : ResourcesUtil.getColor(mAppContext,
                R.color.tab_normal_color));
        viewMnemonic.setBackgroundColor(isMnemonic ? ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color) : ResourcesUtil.getColor
                (mAppContext, R.color.tab_normal_color));

        viewPhone.setVisibility(isPhone ? View.VISIBLE : View.GONE);
        viewMail.setVisibility(isMail ? View.VISIBLE : View.GONE);
        viewMnemonic.setVisibility(isMnemonic ? View.VISIBLE : View.GONE);
        llPhone.setVisibility(isPhone ? View.VISIBLE : View.GONE);
        llMail.setVisibility(isMail ? View.VISIBLE : View.GONE);
        llMnemonic.setVisibility(isMnemonic ? View.VISIBLE : View.GONE);
        llCode.setVisibility(!isMnemonic ? View.VISIBLE : View.GONE);
    }

    /**
     * 获取绑定的邮箱或手机的验证码
     */
    private void getCodeCheck() {
        if (!tvGetCode.getText().toString().contains("S")) {
            String phone = etPhone.getText().toString();
            String mail = etMail.getText().toString();
            if (isPhone) {
                if (StringUtil.isEmpty(phone)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_empty));
                    return;
                }
                if (StringUtil.isEmpty(countryCode)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_country_empty));
                    return;
                }
                if (!StringUtil.isContain("+86", countryCode)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_country_error));
                    return;
                }
                getPresenter().getPhoneCode(phone);
            }
            if (isMail) {
                if (StringUtil.isEmpty(mail)) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_empty));
                    return;
                }
                getPresenter().getMailCode(mail);
            }
        }
    }

    /**
     * 找回密码验证
     */
    private void changeCheck() {
        String phone = etPhone.getText().toString();
        String mail = etMail.getText().toString();
        String name = etName.getText().toString();
        String code = etCode.getText().toString();
        String mnemonic = etMnemonic.getText().toString();
        String password = etPassword.getText().toString();
        String surePassword = etSurePassword.getText().toString();
        if (isMnemonic) {
            if (StringUtil.isEmpty(mnemonic)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.forgot_mnemonic_password));
                return;
            }
        } else {
            if (StringUtil.isEmpty(name)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_name_empty));
                return;
            }
        }
        if (isPhone) {
            if (StringUtil.isEmpty(code)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_code_empty));
                return;
            }
        }
        if (isMail) {
            if (StringUtil.isEmpty(code)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_code_empty));
                return;
            }
        }
        if (StringUtil.isEmpty(code)) {
            if (StringUtil.isMobile(phone)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_code_empty));
            } else {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mail_code_empty));
            }
            return;
        }
        if (StringUtil.isEmpty(password)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
            return;
        }
        if (password.length() < 6 || password.length() > 16) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_length));
            return;
        }
        if (!StringUtil.isEqual(password, surePassword)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_sure));
            return;
        }
        if (isPhone) {
            getPresenter().findPasswordByPhoneMail(name, phone, "", code, password);
        }
        if (isMail) {
            getPresenter().findPasswordByPhoneMail(name, "", mail, code, password);
        }
        if (isMnemonic) {
            getPresenter().mnemonicFindPassword(mnemonic, password);
        }
    }
}
