package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.BindGoogleOneContract;
import com.wallet.hz.presenter.BindGoogleOnePresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: BindGoogleOneActivity
 * @Description: 绑定谷歌第一步页面
 * @Author: ZL
 * @CreateDate: 2019/10/22 10:18
 */
public class BindGoogleOneActivity extends BaseAppMVPActivity<BindGoogleOnePresenter> implements BindGoogleOneContract.View {

    @BindView(R.id.iv_key_code)
    ImageView ivCode;
    @BindView(R.id.tv_key)
    TextView tvKey;

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, BindGoogleOneActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bind_google_one;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.bind_google), null, null, null);
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getGoogleSecret();
    }

    @Override
    public BindGoogleOnePresenter injectDependencies() {
        return new BindGoogleOnePresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_key_copy, R.id.tv_next_step})
    public void onViewClicked(View view) {
        String key = tvKey.getText().toString();
        int id = view.getId();
        switch (id) {
            case R.id.tv_key_copy:
                EnvironmentUtil.copyTextContent(mAppContext, key);
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_copy_success));
                break;
            case R.id.tv_next_step:
                BindGoogleTwoActivity.startIntentResult(BindGoogleOneActivity.this, key, 105);
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 105) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(BindGoogleOneActivity.this);
    }

    @Override
    public void onSecretSuccess(String secret) {
        tvKey.setText(secret);
    }

    @Override
    public void onSecretImage(Bitmap bitmap) {
        ivCode.setImageBitmap(bitmap);
    }
}
