package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.adapter.MySpinnerAdapter;
import com.wallet.hz.dialog.RegistRiskWarningDialog;
import com.wallet.hz.presenter.RegistContract;
import com.wallet.hz.presenter.RegistPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.manager.AppManager;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: RegistActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:54
 */
public class RegistActivity extends BaseAppMVPActivity<RegistPresenter> implements RegistContract.View {

    @BindView(R.id.et_regist_name)
    EditText etRegistName;
    @BindView(R.id.et_regist_password)
    EditText etRegistPassword;
    @BindView(R.id.et_regist_sure_password)
    EditText etSurePassword;
    @BindView(R.id.et_regist_code)
    EditText etRegistCode;
    @BindView(R.id.cb_password)
    CheckBox cbPassword;
    @BindView(R.id.cb_sure_password)
    CheckBox cbSurePassword;
    @BindView(R.id.tv_regist_error)
    TextView tvError;
    @BindView(R.id.sp_regist_language)
    AppCompatSpinner spLanguage;
    @BindView(R.id.tv_regist_service)
    TextView tvService;

    private String mnemonic;
    private boolean isFirst = true;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, RegistActivity.class);
        activity.startActivity(intent);
    }

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, RegistActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        String[] languages = getResources().getStringArray(R.array.language);
        ArrayList<String> languageList = new ArrayList<>();
        for (int i = 0; i < languages.length; i++) {
            languageList.add(languages[i]);
        }
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(languageList, mAppContext);
        spLanguage.setAdapter(spinnerAdapter);

        spLanguage.setSelection(SpUtil.getLanguage(mAppContext));
        changeServiceTextColor();
//        showWarningDialog(getString(R.string.dialog_warning_content));
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String password = etRegistPassword.getText().toString();
                if (isChecked) {
                    etRegistPassword.setText(password);
                    etRegistPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    etRegistPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        cbSurePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String password = etSurePassword.getText().toString();
                if (isChecked) {
                    etSurePassword.setText(password);
                    etSurePassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    etSurePassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        etRegistPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String name = etRegistName.getText().toString();
                    if (StringUtil.isEmpty(name)) {
                        return;
                    }
                    if (name.length() < 6 || name.length() > 16) {
                        showToast(CommonToast.ToastType.TEXT, getString(R.string.check_name_length));
                        return;
                    }
                    getPresenter().checkName(name);
                }
            }
        });
        spLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 第一次加载（即初始化的默认加载）
                if (isFirst) {
                    //不做任何处理
                } else {
                    SpUtil.setLanguage(mAppContext, position);
                    EnvironmentUtil.changeAppLanguage(mAppContext, LanguageEnums.getLanguageByKye(position));
                    AppManager.getAppManager().appExit();
                    AppStartActivity.startIntent(RegistActivity.this);
                }
                isFirst = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initCurrentData() {
//        getPresenter().getWarningString();
    }

    @Override
    public RegistPresenter injectDependencies() {
        return new RegistPresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_rigist, R.id.tv_regist_service})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_rigist:
                checkPassword();
                break;
            case R.id.tv_regist_service:
                ServiceAgreementActivity.startIntent(RegistActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = etRegistName.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("name", name);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onGetZhujiciSuccess(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public void onCheckSuccess(boolean isRepeat) {
        if (isRepeat) {
            tvError.setVisibility(View.GONE);
            getPresenter().getZhujici(etRegistName.getText().toString());
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCheckCodeSuccess() {
        String name = etRegistName.getText().toString();
        String password = etRegistPassword.getText().toString();
        String code = etRegistCode.getText().toString();
        MnemonicWordActivity.startIntentResult(RegistActivity.this, mnemonic, name, password, code, 112);
    }

    /**
     * 显示风险提示弹层
     */
    @Override
    public void showWarningDialog(String warningString) {
        RegistRiskWarningDialog warningDialog = new RegistRiskWarningDialog(RegistActivity.this);
        warningDialog.build(warningString).setOnViewClicked(new RegistRiskWarningDialog.OnViewClicked() {
            @Override
            public void onSure() {

            }

            @Override
            public void onClose() {
                finish();
            }
        });
        warningDialog.show();
    }

    /**
     * 验证输入的信息
     */
    private void checkPassword() {
        String name = etRegistName.getText().toString();
        String password = etRegistPassword.getText().toString();
        String surePassword = etSurePassword.getText().toString();
        String code = etRegistCode.getText().toString();
        if (StringUtil.isEmpty(name)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_name_empty));
            return;
        }
        if (name.length() < 6 || name.length() > 16) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_name_length));
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
        if (StringUtil.isEmpty(code)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_invite_empty));
            return;
        }
        if (tvError.getVisibility() == View.GONE) {
            getPresenter().checkInviteCode(code);
        }
    }

    private void changeServiceTextColor() {
        int language = SpUtil.getLanguage(mAppContext);
        String text = getString(R.string.regist_agree_service);
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_black));
        if (language > 0) {
            spannableString.setSpan(foregroundColorSpan, 10, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        } else {
            spannableString.setSpan(foregroundColorSpan, text.length() > 43 ? 43 : 10, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        tvService.setText(spannableString);
    }
}
