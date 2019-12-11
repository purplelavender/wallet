package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.ChangePhoneOneContract;
import com.wallet.hz.presenter.ChangePhoneOnePresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ChangePhoneOneActivity
 * @Description: 修改手机号码第一步
 * @Author: ZL
 * @CreateDate: 2019/10/22 15:40
 */
public class ChangePhoneOneActivity extends BaseAppMVPActivity<ChangePhoneOnePresenter> implements ChangePhoneOneContract.View {

    @BindView(R.id.et_old_phone)
    EditText etPhone;
    @BindView(R.id.et_old_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.tv_phone_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_phone_country)
    TextView tvCountry;

    private String countryCode = "";

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ChangePhoneOneActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_phone_one;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.bind_change_phone), null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public ChangePhoneOnePresenter injectDependencies() {
        return new ChangePhoneOnePresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_phone_get_code, R.id.tv_phone_next_step, R.id.ll_phone_country})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_phone_get_code:
                getCodeCheck();
                break;
            case R.id.tv_phone_next_step:
                updateCheck();
                break;
            case R.id.ll_phone_country:
                CountryActivity.startIntentResult(ChangePhoneOneActivity.this, 131);
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 107 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
        if (requestCode == 131 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            countryCode = data.getStringExtra("code");
            tvCountry.setText(name + "（" + countryCode + "）");
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ChangePhoneOneActivity.this);
    }

    @Override
    public void setGetCodeText(String time) {
        if (tvGetCode != null) {
            tvGetCode.setText(time);
        }
    }

    @Override
    public void onCheckPhoneSuccess() {
        ChangePhoneTwoActivity.startIntentResult(ChangePhoneOneActivity.this, 107);
    }

    /**
     * 获取验证码验证
     */
    private void getCodeCheck() {
        if (!tvGetCode.getText().toString().contains("S")) {
            String phone = etPhone.getText().toString();
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
    }

    /**
     * 验证旧手机号码
     */
    private void updateCheck() {
        String phone = etPhone.getText().toString();
        String code = etPhoneCode.getText().toString();
        if (StringUtil.isEmpty(phone)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_empty));
            return;
        }
        if (StringUtil.isEmpty(code)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_phone_code_empty));
            return;
        }
        getPresenter().updatePhone(phone, code);
    }
}
