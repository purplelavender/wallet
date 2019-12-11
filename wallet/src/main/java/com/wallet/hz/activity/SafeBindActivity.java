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
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: SafeBindActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 20:30
 */
public class SafeBindActivity extends BaseAppActivity {

    @BindView(R.id.tv_google_state)
    AutoSizeTextView tvGoogleState;
    @BindView(R.id.tv_phone_state)
    AutoSizeTextView tvPhoneState;
    @BindView(R.id.tv_mail_state)
    AutoSizeTextView tvMailState;
    @BindView(R.id.tv_bind_phone)
    TextView tvBindPhone;

    private String phone, mail;
    private boolean isBindGoogle = false, isBindPhone = false, isBindMail = false;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, SafeBindActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_safe_bind;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_safe_bind), null, null, null);
    }

    @Override
    protected void initCurrentData() {
        phone = AppSpUtil.getUserMobile(mAppContext);
        mail = AppSpUtil.getUserMail(mAppContext);
        isBindGoogle = AppSpUtil.getUserGoogle(mAppContext);
        isBindMail = !StringUtil.isEmpty(mail);
        isBindPhone = !StringUtil.isEmpty(phone);
        updateBindState();
    }

    @OnClick({R.id.ll_bind_google, R.id.ll_bind_phone, R.id.ll_bind_mail})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_bind_google:
                if (!isBindGoogle) {
                    BindGoogleOneActivity.startIntentResult(SafeBindActivity.this, 102);
                }
                break;
            case R.id.ll_bind_phone:
                if (isBindPhone) {
                    ChangePhoneOneActivity.startIntentResult(SafeBindActivity.this, 106);
                } else {
                    BindPhoneMailActivity.startIntentResult(SafeBindActivity.this, true, 103);
                }
                break;
            case R.id.ll_bind_mail:
                if (!isBindMail) {
                    BindPhoneMailActivity.startIntentResult(SafeBindActivity.this, false, 104);
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

    /**
     * 根据绑定的情况修改页面显示
     */
    private void updateBindState() {
        if (isBindGoogle) {
            tvGoogleState.setText(R.string.bind_already_bind);
            tvGoogleState.setTextColor(ResourcesUtil.getColor(mAppContext, R.color.color_696969));
        } else {
            tvGoogleState.setText(R.string.bind_go_bind);
            tvGoogleState.setTextColor(ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color));
        }
        if (isBindMail) {
            tvMailState.setText(R.string.bind_already_bind);
            tvMailState.setTextColor(ResourcesUtil.getColor(mAppContext, R.color.color_696969));
        } else {
            tvMailState.setText(R.string.bind_go_bind);
            tvMailState.setTextColor(ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color));
        }
        if (isBindPhone) {
            tvPhoneState.setText(R.string.bind_go_change);
            tvPhoneState.setTextColor(ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color));
            tvBindPhone.setVisibility(View.VISIBLE);
            tvBindPhone.setText(StringUtil.formatMobile(phone));
        } else {
            tvPhoneState.setText(R.string.bind_go_bind);
            tvPhoneState.setTextColor(ResourcesUtil.getColor(mAppContext, R.color.tab_selected_color));
            tvBindPhone.setVisibility(View.INVISIBLE);
        }
    }
}
