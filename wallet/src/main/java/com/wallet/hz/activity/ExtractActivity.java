package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.dialog.NoFundPasswordDialog;
import com.wallet.hz.dialog.SafeCheckDialog;
import com.wallet.hz.presenter.ExtractContract;
import com.wallet.hz.presenter.ExtractPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ExtractActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 16:52
 */
public class ExtractActivity extends BaseAppMVPActivity<ExtractPresenter> implements ExtractContract.View {

    @BindView(R.id.tv_extract_drawable_principal)
    TextView tvExtractPrincipal;
    @BindView(R.id.et_extract_count)
    EditText etExtractCount;
    @BindView(R.id.tv_extract_total_amount)
    TextView tvExtractAmount;
    @BindView(R.id.tv_extract_now_rate)
    TextView tvExtractRate;

    private String extractJin;
    private String rate;

    public static void startIntent(Activity activity, String extractJin) {
        Intent intent = new Intent(activity, ExtractActivity.class);
        intent.putExtra("extractJin", extractJin);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_extract;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        tvExtractPrincipal.setText(BigDecimalUtils.formatServiceNumber(extractJin));
        tvExtractRate.setText("1 HS ≈ " + rate + " USDT");
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        setToolRightClick(getString(R.string.wallet_history_record), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelRecordListActivity.startIntent(ExtractActivity.this, 3);
            }
        });
        etExtractCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (BigDecimalUtils.isEightPoint(s.toString())){
                    etExtractCount.setText(BigDecimalUtils.decimalPoint(s.toString(), 8));
                    etExtractCount.setSelection(etExtractCount.getText().length());
                } else {
                    String total = tvExtractPrincipal.getText().toString();
                    if (BigDecimalUtils.compare(s.toString(), total) || BigDecimalUtils.isEightPoint(s.toString())) {
                        etExtractCount.setText(total);
                        etExtractCount.setSelection(etExtractCount.getText().length());
                    } else {
                        tvExtractAmount.setText(BigDecimalUtils.div(s.toString(), rate + "", 8));
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

    }

    @Override
    public ExtractPresenter injectDependencies() {
        extractJin = getIntent().getStringExtra("extractJin");
        rate = AppSpUtil.getUserRate(mAppContext);
        return new ExtractPresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_extract_count_all, R.id.tv_extract_sure})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_extract_count_all:
                etExtractCount.setText(extractJin);
                break;
            case R.id.tv_extract_sure:
                extractCheck();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ExtractActivity.this);
    }

    /**
     * 增值前验证
     */
    private void extractCheck() {
        String count = etExtractCount.getText().toString();
        if (StringUtil.isEmpty(count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_exchange_count_empty));
            return;
        }
        if (BigDecimalUtils.compare("1", count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_error_extract_one));
            return;
        }
        boolean isBindFund = AppSpUtil.getUserAvatar(mAppContext);
        if (isBindFund) {
            SafeCheckDialog safeCheckDialog = new SafeCheckDialog(ExtractActivity.this);
            safeCheckDialog.build().setOnViewClicked(new SafeCheckDialog.OnViewClicked() {
                @Override
                public void onSure(String password) {
                    getPresenter().extract(etExtractCount.getText().toString(), "hs", password);
                }

                @Override
                public void onClose() {

                }
            });
            safeCheckDialog.show();
        } else {
            NoFundPasswordDialog noFundPasswordDialog = new NoFundPasswordDialog(ExtractActivity.this);
            noFundPasswordDialog.build().setOnViewClicked(new NoFundPasswordDialog.OnViewClicked() {
                @Override
                public void onSure() {
                    SetMoneyPasswordActivity.startIntentResult(ExtractActivity.this, true, false, "", 136);
                }
            });
            noFundPasswordDialog.show();
        }
    }

    @Override
    public void onExtractSuccess() {
        extractJin = BigDecimalUtils.sub(tvExtractPrincipal.getText().toString(), etExtractCount.getText().toString());
        tvExtractPrincipal.setText(extractJin);
        etExtractCount.setText("");
    }
}
