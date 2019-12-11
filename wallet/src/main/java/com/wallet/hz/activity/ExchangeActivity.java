package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.dialog.NoFundPasswordDialog;
import com.wallet.hz.dialog.SafeCheckDialog;
import com.wallet.hz.presenter.ExchangeContract;
import com.wallet.hz.presenter.ExchangePresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ExchangeActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:55
 */
public class ExchangeActivity extends BaseAppMVPActivity<ExchangePresenter> implements ExchangeContract.View {

    @BindView(R.id.sp_exchange_coin)
    AppCompatSpinner spCoin;
    @BindView(R.id.tv_exchange_start_coin)
    TextView tvStartCoin;
    @BindView(R.id.et_exchange_start_count)
    EditText etStartCount;
    @BindView(R.id.tv_exchange_target_count)
    TextView tvTargetCount;
    @BindView(R.id.tv_exchange_rate)
    TextView tvRate;
    @BindView(R.id.tv_exchange_available)
    TextView tvAvailable;
    @BindView(R.id.tv_exchange_rule)
    TextView tvRule;
    @BindView(R.id.tv_available_tag)
    TextView tvAvailableTag;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private String rate, amount;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, ExchangeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_exchange;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        tvTitle.setText(getString(R.string.modle_exchange));
        tvRule.setVisibility(View.GONE);
        spCoin.setVisibility(View.GONE);

        onchangeUI();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        setToolRightClick(getString(R.string.wallet_history_record), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelRecordListActivity.startIntent(ExchangeActivity.this, 0);
            }
        });
        etStartCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (BigDecimalUtils.isEightPoint(s.toString())){
                    etStartCount.setText(BigDecimalUtils.decimalPoint(s.toString(), 8));
                    etStartCount.setSelection(etStartCount.getText().length());
                } else {
                    String pcs = tvAvailable.getText().toString();
                    if (BigDecimalUtils.compare(s.toString(), pcs)) {
                        etStartCount.setText(pcs);
                        etStartCount.setSelection(etStartCount.getText().length());
                    } else {
                        tvTargetCount.setText(BigDecimalUtils.mul(s.toString(), rate + "", 8));
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
        getPresenter().getCoinBalance();
    }

    @Override
    public ExchangePresenter injectDependencies() {
        return new ExchangePresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_exchange})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_exchange:
                exchangeCheck();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ExchangeActivity.this);
    }

    @Override
    public void onExchangeSuccess() {
        getPresenter().getCoinBalance();
        etStartCount.setText("");
    }

    @Override
    public void onchangeUI() {
        rate = AppSpUtil.getUserRate(mAppContext);
        amount = AppSpUtil.getUserAmount(mAppContext);
        tvAvailable.setText(BigDecimalUtils.formatServiceNumber(amount));
        tvRate.setText("1 HS ≈ " + rate + " USDT");
        tvAvailableTag.setText(getString(R.string.modle_available_pcs, "HS"));
    }

    /**
     * 兑换前验证
     */
    private void exchangeCheck() {
        final String count = etStartCount.getText().toString();
        if (StringUtil.isEmpty(count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_exchange_count_empty));
            return;
        }
        if (BigDecimalUtils.compare("1", count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_error_exchange_one));
            return;
        }
        boolean isBindFund = AppSpUtil.getUserAvatar(mAppContext);
        if (isBindFund) {
            SafeCheckDialog safeCheckDialog = new SafeCheckDialog(ExchangeActivity.this);
            safeCheckDialog.build().setOnViewClicked(new SafeCheckDialog.OnViewClicked() {
                @Override
                public void onSure(String password) {
                    getPresenter().exchange("hs", count, password);
                }

                @Override
                public void onClose() {

                }
            });
            safeCheckDialog.show();
        } else {
            NoFundPasswordDialog noFundPasswordDialog = new NoFundPasswordDialog(ExchangeActivity.this);
            noFundPasswordDialog.build().setOnViewClicked(new NoFundPasswordDialog.OnViewClicked() {
                @Override
                public void onSure() {
                    SetMoneyPasswordActivity.startIntentResult(ExchangeActivity.this, true, false, "", 135);
                }
            });
            noFundPasswordDialog.show();
        }
    }
}
