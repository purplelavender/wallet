package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.UserInfo;
import com.wallet.hz.presenter.MnemonicFindPasswordContract;
import com.wallet.hz.presenter.MnemonicFindPasswordPresenter;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: MnemonicFindPasswordActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/10 15:32
 */
public class MnemonicFindPasswordActivity extends BaseAppMVPActivity<MnemonicFindPasswordPresenter> implements MnemonicFindPasswordContract.View {

    @BindView(R.id.et_mnemonic)
    EditText etMnemonic;
    @BindView(R.id.et_new_password)
    EditText etPassword;
    @BindView(R.id.et_sure_password)
    EditText etSurePassword;
    @BindView(R.id.tv_password_sure)
    TextView tvSure;

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, MnemonicFindPasswordActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mnemonic_find_login_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.password_zhujici_find), null, null, null);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCheck();
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public MnemonicFindPasswordPresenter injectDependencies() {
        return new MnemonicFindPasswordPresenter(this, mAppContext);
    }

    @Override
    public void onFindSuccess(UserInfo userInfo) {
        if (userInfo != null) {
            Intent intent = new Intent();
            intent.putExtra("name", userInfo.getUsername());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 页面输入修改
     */
    private void changeCheck() {
        String mnemonic = etMnemonic.getText().toString();
        String newPassWord = etPassword.getText().toString();
        String surePassword = etSurePassword.getText().toString();
        if (StringUtil.isEmpty(newPassWord)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_empty));
            return;
        }
        if (newPassWord.length() < 6 || newPassWord.length() > 16) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_length));
            return;
        }
        if (!StringUtil.isEqual(newPassWord, surePassword)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_password_sure));
            return;
        }
        if (StringUtil.isEmpty(mnemonic)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.forgot_mnemonic_password));
            return;
        }
        String word = mnemonic.trim().replaceAll("，", ",").replaceAll(" ", "");
        getPresenter().mnemonicFindPassword(word, newPassWord);
    }
}
