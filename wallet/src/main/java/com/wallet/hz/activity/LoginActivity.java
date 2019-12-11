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
import com.wallet.hz.presenter.LoginContract;
import com.wallet.hz.presenter.LoginPresenter;
import com.wallet.hz.utils.AppSpUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.manager.AppManager;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.SpUtil;

/**
 * @ClassName: LoginActivity
 * @Description: 登录页面
 * @Author: ZL
 * @CreateDate: 2019/10/23 16:37
 */
public class LoginActivity extends BaseAppMVPActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.cb_password)
    CheckBox cbPassword;
    @BindView(R.id.sp_login_language)
    AppCompatSpinner spLanguage;
    @BindView(R.id.tv_login_service)
    TextView tvService;

    private boolean isFirst = true;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);
        etLoginName.setText(AppSpUtil.getUserName(mAppContext));

        String[] languages = getResources().getStringArray(R.array.language);
        ArrayList<String> languageList = new ArrayList<>();
        for (int i = 0; i < languages.length; i++) {
            languageList.add(languages[i]);
        }
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(languageList, mAppContext);
        spLanguage.setAdapter(spinnerAdapter);

        spLanguage.setSelection(SpUtil.getLanguage(mAppContext));
        changeServiceTextColor();
    }

    @Override
    protected void initEvent() {
        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String password = etLoginPassword.getText().toString();
                if (isChecked) {
                    etLoginPassword.setText(password);
                    etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                    AppStartActivity.startIntent(LoginActivity.this);
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

    }

    @Override
    public LoginPresenter injectDependencies() {
        return new LoginPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            etLoginName.setText(name);
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_login_forget, R.id.tv_login_regist, R.id.tv_login_service, R.id.tv_name_clear})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_login:
                String name = etLoginName.getText().toString();
                String password = etLoginPassword.getText().toString();
                getPresenter().login(name, password);
                break;
            case R.id.tv_login_forget:
                ForgotPasswordActivity.startIntent(LoginActivity.this, 140);
                break;
            case R.id.tv_login_regist:
                RegistActivity.startIntentResult(LoginActivity.this, 108);
                break;
            case R.id.tv_login_service:
                ServiceAgreementActivity.startIntent(LoginActivity.this);
                break;
            case R.id.tv_name_clear:
                etLoginName.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        String name = etLoginName.getText().toString();
        String password = etLoginPassword.getText().toString();
        AppSpUtil.setUserName(mAppContext, name);
        AppSpUtil.setUserPassword(mAppContext, password);
        MainActivity.startIntent(LoginActivity.this, 0);
        finish();
    }

    private void changeServiceTextColor() {
        int language = SpUtil.getLanguage(mAppContext);
        String text = getString(R.string.login_agree_service);
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_black));
        if (language > 0) {
            spannableString.setSpan(foregroundColorSpan, 10, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        } else {
            spannableString.setSpan(foregroundColorSpan, text.length() > 40 ? 40 : 10, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        tvService.setText(spannableString);
    }
}
