package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.utils.AppSpUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppActivity;

/**
 * @ClassName: PasswordManagerActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 16:26
 */
public class PasswordManagerActivity extends BaseAppActivity {

    @BindView(R.id.tv_money_state)
    TextView tvMoneyState;

    private boolean isSetMoney = false;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, PasswordManagerActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_password_manager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_password_manager), null, null, null);
    }

    @Override
    protected void initCurrentData() {
        isSetMoney = AppSpUtil.getUserAvatar(mAppContext);
        tvMoneyState.setText(isSetMoney ? getString(R.string.bind_go_change) : getString(R.string.password_go_setting));
    }

    @OnClick({R.id.ll_login_password, R.id.ll_money_password})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_login_password:
                ChangePasswordActivity.startIntentResult(PasswordManagerActivity.this, false, 124);
                break;
            case R.id.ll_money_password:
                if (isSetMoney) {
                    ChangeMoneyPasswordActivity.startIntentResult(PasswordManagerActivity.this, 120);
                } else {
                    SetMoneyPasswordActivity.startIntentResult(PasswordManagerActivity.this, true, false, "", 115);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initCurrentData();
        }
    }
}
