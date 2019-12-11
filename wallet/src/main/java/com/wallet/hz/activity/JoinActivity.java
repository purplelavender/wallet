package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.adapter.MySpinnerAdapter;
import com.wallet.hz.dialog.AppointmentDialog;
import com.wallet.hz.dialog.JoinTypeRuleDialog;
import com.wallet.hz.dialog.NoFundPasswordDialog;
import com.wallet.hz.dialog.SafeCheckDialog;
import com.wallet.hz.dialog.YuYueDialog;
import com.wallet.hz.model.JoinInfo;
import com.wallet.hz.model.ModelInfo;
import com.wallet.hz.model.YuyueQueue;
import com.wallet.hz.presenter.JoinContract;
import com.wallet.hz.presenter.JoinPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: JoinActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 16:44
 */
public class JoinActivity extends BaseAppMVPActivity<JoinPresenter> implements JoinContract.View {

    @BindView(R.id.sp_join_type)
    AppCompatSpinner spJoinType;
    @BindView(R.id.tv_join_dig_earning)
    TextView tvDigEarning;
    @BindView(R.id.et_join_dig_count)
    EditText etDigCount;
    @BindView(R.id.tv_join_need_dig_usdt)
    TextView tvNeedDigUsdt;
    @BindView(R.id.tv_join_release_dig_usdt)
    TextView tvReleaseDigUsdt;
    @BindView(R.id.tv_join_benjin_dig_usdt)
    TextView tvBenJinDigUsdt;

    private int joinType = 0;
    private boolean isFirst = true, isPaiDui = false;
    private ModelInfo mModelInfo;
    private String moneyPassword = "";

    public static void startIntent(Activity activity, ModelInfo modelInfo) {
        Intent intent = new Intent(activity, JoinActivity.class);
        intent.putExtra("modelInfo", modelInfo);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_join;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        onChangeUI(mModelInfo);

        String[] languages = getResources().getStringArray(R.array.join_type);
        ArrayList<String> languageList = new ArrayList<>();
        languageList.add(0, getString(R.string.modle_list_join_type));
        for (int i = 0; i < languages.length; i++) {
            languageList.add(languages[i]);
        }
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(languageList, mAppContext);
        spJoinType.setAdapter(spinnerAdapter);

    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        setToolRightClick(getString(R.string.wallet_history_record), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelRecordListActivity.startIntent(JoinActivity.this, 2);
            }
        });
        spJoinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 第一次加载（即初始化的默认加载）
                if (isFirst) {
                    //不做任何处理
                    joinType = 0;
                } else {
                    joinType = position;
                }
                isFirst = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etDigCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (BigDecimalUtils.isEightPoint(s.toString())){
                    etDigCount.setText(BigDecimalUtils.decimalPoint(s.toString(), 8));
                    etDigCount.setSelection(etDigCount.getText().length());
                } else {
                    String total = tvDigEarning.getText().toString();
                    if (BigDecimalUtils.compare(s.toString(), total) || BigDecimalUtils.isEightPoint(s.toString())) {
                        etDigCount.setText(total);
                        etDigCount.setSelection(etDigCount.getText().length());
                    } else {
                        tvNeedDigUsdt.setText(BigDecimalUtils.mul(s.toString(), "10", 8));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initCurrentData() {
        getPresenter().lookDuiLie(false);
    }

    @Override
    public JoinPresenter injectDependencies() {
        mModelInfo = (ModelInfo) getIntent().getSerializableExtra("modelInfo");
        return new JoinPresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_join_dig_all, R.id.tv_join_sure, R.id.iv_type, R.id.tv_join_appointment})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_join_dig_all:
                etDigCount.setText(tvDigEarning.getText().toString());
                break;
            case R.id.tv_join_sure:
                joinCheck();
                break;
            case R.id.iv_type:
                JoinTypeRuleDialog joinTypeRuleDialog = new JoinTypeRuleDialog(JoinActivity.this);
                joinTypeRuleDialog.build();
                joinTypeRuleDialog.show();
                break;
            case R.id.tv_join_appointment:
                getPresenter().lookDuiLie(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(JoinActivity.this);
    }

    @Override
    public void onJoinSuccess(final JoinInfo joinInfo) {
        getPresenter().getModelInfo();
        if (joinInfo.isNeedYuYue()) {
            YuYueDialog yuYueDialog = new YuYueDialog(JoinActivity.this);
            yuYueDialog.build(joinInfo.getSuccessCount() + "", joinInfo.getFailCount() + "").setOnViewClicked(new YuYueDialog.OnViewClicked() {
                @Override
                public void onSure() {
                    getPresenter().yuyue(joinType, joinInfo.getFailCount() + "", moneyPassword);
                }
            });
            yuYueDialog.show();
        }
        etDigCount.setText("");
    }

    @Override
    public void onLookSuccess(YuyueQueue queue, boolean isClick) {
        if (queue != null) {
            if (queue.getRank() == 0) {
                if (isClick) {
                    showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_no_yuyue));
                }
            } else {
                isPaiDui = true;
                if (isClick) {
                    AppointmentDialog appointmentDialog = new AppointmentDialog(JoinActivity.this);
                    appointmentDialog.build(queue.getAmount() + "", queue.getRank() + "").show();
                }
            }
        } else {
            if (isClick) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_no_yuyue));
            }
        }
    }

    @Override
    public void onYuyueSuccess() {
        getPresenter().getModelInfo();
        isPaiDui = true;
    }

    @Override
    public void onChangeUI(ModelInfo modelInfo) {
        if (modelInfo != null) {
            mModelInfo = modelInfo;
            tvDigEarning.setText(BigDecimalUtils.formatServiceNumber(mModelInfo.getMinables()));
            tvReleaseDigUsdt.setText(BigDecimalUtils.formatServiceNumber(mModelInfo.getAvailabs()));
            tvBenJinDigUsdt.setText(BigDecimalUtils.formatServiceNumber(mModelInfo.getCapital()));
        }
    }

    /**
     * 增值前验证
     */
    private void joinCheck() {
        if (isPaiDui) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_in_yuyue));
            return;
        }
        String count = etDigCount.getText().toString();
        if (StringUtil.isEmpty(count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_exchange_count_empty));
            return;
        }
        if (BigDecimalUtils.compare("1", count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_error_join_one));
            return;
        }
        if (joinType <= 0) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_join_type_error));
            return;
        }
        boolean isBindFund = AppSpUtil.getUserAvatar(mAppContext);
        if (isBindFund) {
            SafeCheckDialog safeCheckDialog = new SafeCheckDialog(JoinActivity.this);
            safeCheckDialog.build().setOnViewClicked(new SafeCheckDialog.OnViewClicked() {
                @Override
                public void onSure(String password) {
                    moneyPassword = password;
                    getPresenter().join(joinType, etDigCount.getText().toString(), password);
                }

                @Override
                public void onClose() {

                }
            });
            safeCheckDialog.show();
        } else {
            NoFundPasswordDialog noFundPasswordDialog = new NoFundPasswordDialog(JoinActivity.this);
            noFundPasswordDialog.build().setOnViewClicked(new NoFundPasswordDialog.OnViewClicked() {
                @Override
                public void onSure() {
                    SetMoneyPasswordActivity.startIntentResult(JoinActivity.this, true, false, "", 137);
                }
            });
            noFundPasswordDialog.show();
        }
    }
}
