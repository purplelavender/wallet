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
import com.wallet.hz.dialog.NoFundPasswordDialog;
import com.wallet.hz.dialog.SafeCheckDialog;
import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.presenter.ValueAddContract;
import com.wallet.hz.presenter.ValueAddPresenter;
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
 * @ClassName: ExchangeActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:55
 */
public class ValueAddActivity extends BaseAppMVPActivity<ValueAddPresenter> implements ValueAddContract.View {

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
    @BindView(R.id.tv_exchange)
    TextView tvExchange;

    private String rate, amount;
    private boolean isFirst = true;
    private ArrayList<String> entries = new ArrayList<>();
    private String coinName = "HS";

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, ValueAddActivity.class);
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
        tvRule.setVisibility(View.VISIBLE);
        spCoin.setVisibility(View.VISIBLE);
        tvTitle.setText(getString(R.string.modle_value_add));
        tvExchange.setText(getString(R.string.bind_sure));

        tvAvailable.setText(BigDecimalUtils.formatServiceNumber(amount));
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        setToolRightClick(getString(R.string.wallet_history_record), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelRecordListActivity.startIntent(ValueAddActivity.this, 1);
            }
        });
        spCoin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 第一次加载（即初始化的默认加载）
                if (isFirst) {
                    //不做任何处理
                } else {
                    coinName = entries.get(position);
                    tvStartCoin.setText(coinName);
                    getPresenter().getCoinBalance(coinName);
                }
                isFirst = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    String total = tvAvailable.getText().toString();
                    if (BigDecimalUtils.compare(s.toString(), total) || BigDecimalUtils.isEightPoint(s.toString())) {
                        etStartCount.setText(total);
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
        getPresenter().getCoinList();
    }

    @Override
    public ValueAddPresenter injectDependencies() {
        rate = AppSpUtil.getUserRate(mAppContext);
        amount = AppSpUtil.getUserAmount(mAppContext);
        return new ValueAddPresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_exchange})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_exchange:
                addValueCheck();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ValueAddActivity.this);
    }

    @Override
    public void onTypeListSuccess(ArrayList<WalletInfo> walletInfos) {
        if (walletInfos != null && walletInfos.size() > 1) {
            for (int i = 0; i < walletInfos.size(); i++) {
                WalletInfo info = walletInfos.get(i);
                if (StringUtil.isContain("HS", info.getCoinName()) || StringUtil.isContain("USDT", info.getCoinName())) {

                } else {
                    entries.add(info.getCoinName());
                }
            }
        } else {
            entries.add("TOA");
        }
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(entries, mAppContext);
        spCoin.setAdapter(spinnerAdapter);
        coinName = entries.get(0);
        tvStartCoin.setText(coinName);
        tvRate.setText("1 " + coinName + " ≈ " + rate + " USDT");
        tvAvailableTag.setText(getString(R.string.modle_available_pcs, coinName));
        getPresenter().getCoinBalance(coinName);
    }

    @Override
    public void onValueSuccess() {
        getPresenter().getCoinBalance(coinName);
        etStartCount.setText("");
    }

    @Override
    public void getBalanceSuccess(CoinBalance balance) {
        rate = balance.getHuilv() + "";
        tvAvailable.setText(BigDecimalUtils.formatServiceNumber(balance.getBalance()));
        tvRate.setText("1 " + coinName + " ≈ " + rate + " USDT");
        tvAvailableTag.setText(getString(R.string.modle_available_pcs, coinName));
        if (!StringUtil.isEmpty(etStartCount.getText().toString())) {
            tvTargetCount.setText(BigDecimalUtils.mul(etStartCount.getText().toString(), rate + "", 8));
        }
    }

    /**
     * 增值前验证
     */
    private void addValueCheck() {
        String count = etStartCount.getText().toString();
        if (StringUtil.isEmpty(count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_exchange_count_empty));
            return;
        }
        if (BigDecimalUtils.compare("1", count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_error_value_one));
            return;
        }
        boolean isBindFund = AppSpUtil.getUserAvatar(mAppContext);
        if (isBindFund) {
            SafeCheckDialog safeCheckDialog = new SafeCheckDialog(ValueAddActivity.this);
            safeCheckDialog.build().setOnViewClicked(new SafeCheckDialog.OnViewClicked() {
                @Override
                public void onSure(String password) {
                    getPresenter().valueAdd(tvStartCoin.getText().toString(), etStartCount.getText().toString(), tvTargetCount.getText().toString()
                            , password);
                }

                @Override
                public void onClose() {

                }
            });
            safeCheckDialog.show();
        } else {
            NoFundPasswordDialog noFundPasswordDialog = new NoFundPasswordDialog(ValueAddActivity.this);
            noFundPasswordDialog.build().setOnViewClicked(new NoFundPasswordDialog.OnViewClicked() {
                @Override
                public void onSure() {
                    SetMoneyPasswordActivity.startIntentResult(ValueAddActivity.this, true, false, "", 135);
                }
            });
            noFundPasswordDialog.show();
        }
    }
}
