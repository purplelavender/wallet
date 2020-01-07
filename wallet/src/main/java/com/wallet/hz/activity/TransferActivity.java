package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.dialog.NoFundPasswordDialog;
import com.wallet.hz.dialog.PayDetailDialog;
import com.wallet.hz.dialog.SafeCheckDialog;
import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.presenter.TransferContract;
import com.wallet.hz.presenter.TransferPresenter;
import com.wallet.hz.utils.AppSpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.CommonConst;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: TransferActivity
 * @Description: 货币转账页面
 * @Author: ZL
 * @CreateDate: 2019/10/24 17:11
 */
public class TransferActivity extends BaseAppMVPActivity<TransferPresenter> implements TransferContract.View {

    @BindView(R.id.tv_transfer_balance)
    TextView tvBalance;
    @BindView(R.id.et_transfer_amount)
    EditText etAmount;
    @BindView(R.id.et_transfer_address)
    EditText etAddress;
    @BindView(R.id.et_transfer_mark)
    EditText etMark;
    @BindView(R.id.tv_transfer_fee)
    TextView tvFee;
    @BindView(R.id.tv_transfer_type)
    TextView tvCoinName;
    @BindView(R.id.tv_transfer_rule)
    TextView tvRule;
    @BindView(R.id.tv_transfer_mark_tag)
    TextView tvMarkTag;

    private WalletInfo mWalletInfo;
    private boolean isSpecial = false;
    private String minimum = "0";
    private double feePrice = 0d;

    public static void startIntent(Activity activity, WalletInfo walletInfo) {
        Intent intent = new Intent(activity, TransferActivity.class);
        intent.putExtra("wallet", walletInfo);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        tvBalance.setText(getString(R.string.wallet_balance) + mWalletInfo.getBalance() + " " + mWalletInfo.getCoinName());
        tvMarkTag.setVisibility(isSpecial ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (BigDecimalUtils.isEightPoint(s.toString())) {
                    etAmount.setText(BigDecimalUtils.decimalPoint(s.toString(), 8));
                    etAmount.setSelection(etAmount.getText().length());
                } else {
                    String total = mWalletInfo.getBalance() + "";
                    if (BigDecimalUtils.compare(s.toString(), total) || BigDecimalUtils.isEightPoint(s.toString())) {
                        etAmount.setText(total);
                        etAmount.setSelection(etAmount.getText().length());
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
        getPresenter().getTransferFee(mWalletInfo.getCoinName());
    }

    @Override
    public TransferPresenter injectDependencies() {
        mWalletInfo = (WalletInfo) getIntent().getSerializableExtra("wallet");
        isSpecial = mWalletInfo.isSpecialCoin();
        return new TransferPresenter(this, mAppContext);
    }

    @OnClick({R.id.iv_transfer_sao, R.id.iv_transfer_ben, R.id.tv_transfer_next, R.id.tv_transfer_type})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_transfer_sao:
                requestCamera();
                break;
            case R.id.iv_transfer_ben:
                AddressBenActivity.startIntentResult(TransferActivity.this, 127);
                break;
            case R.id.tv_transfer_next:
                transferEditCheck();
                break;
            case R.id.tv_transfer_type:
                etAmount.setText(mWalletInfo.getBalance() + "");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String code = data.getStringExtra("code");
            etAddress.setText(code);
        }
    }

    @Override
    public void onFeeSuccess(CoinBalance coinBalance) {
        this.feePrice = coinBalance.getSxfamount();
        this.minimum = coinBalance.getTbamount() + "";
        tvFee.setText(feePrice * 100 + "%");
        tvRule.setText(getString(R.string.wallet_transfer_rule, minimum, mWalletInfo.getCoinName()));
        tvBalance.setText(getString(R.string.wallet_balance) + coinBalance.getBalance() + " " + mWalletInfo.getCoinName());
    }

    @Override
    public void onTransferSuccess() {
        getPresenter().getTransferFee(mWalletInfo.getCoinName());
        etAmount.setText("");
        etAddress.setText("");
        etMark.setText("");
    }

    private void transferEditCheck() {
        String count = etAmount.getText().toString();
        String address = etAddress.getText().toString();
        String mark = etMark.getText().toString();
        if (StringUtil.isEmpty(count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_exchange_count_empty));
            return;
        }
        if (BigDecimalUtils.compare(minimum, count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_small_transfer, minimum));
            return;
        }
        if (StringUtil.isEmpty(address)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_address_empty));
            return;
        }
        if (isSpecial && StringUtil.isEqual(address, mWalletInfo.getAddress())) {
            if (StringUtil.isEmpty(mark)) {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.wallet_new_transfer_notice));
                return;
            }
        }
        PayDetailDialog payDetailDialog = new PayDetailDialog(TransferActivity.this);
        payDetailDialog.build(1, etAddress.getText().toString(), mWalletInfo.getAddress(), BigDecimalUtils.sub(etAmount.getText().toString(),
                BigDecimalUtils.mul(etAmount.getText().toString(), feePrice + "")) + mWalletInfo.getCoinName())
                .setOnViewClicked(new PayDetailDialog.OnViewClicked() {
                    @Override
                    public void onSure() {
                        showPasswordDialog();
                    }

                    @Override
                    public void onClose() {

                    }
                });
        payDetailDialog.show();
    }

    private void showPasswordDialog() {
        boolean isBindFund = AppSpUtil.getUserAvatar(mAppContext);
        if (isBindFund) {
            SafeCheckDialog safeCheckDialog = new SafeCheckDialog(TransferActivity.this);
            safeCheckDialog.build().setOnViewClicked(new SafeCheckDialog.OnViewClicked() {
                @Override
                public void onSure(String password) {
                    if (isSpecial) {
                        getPresenter().specialTransfer(mWalletInfo.getCoinName(), etAmount.getText().toString(), etAddress.getText().toString(),
                                etMark.getText().toString(), BigDecimalUtils.mul(feePrice + "", etAmount.getText().toString()), password);
                    } else {
                        getPresenter().transfer(mWalletInfo.getCoinName(), etAmount.getText().toString(), etAddress.getText().toString(), etMark
                                .getText().toString(), BigDecimalUtils.mul(feePrice + "", etAmount.getText().toString()), password);
                    }
                }

                @Override
                public void onClose() {

                }
            });
            safeCheckDialog.show();
        } else {
            NoFundPasswordDialog noFundPasswordDialog = new NoFundPasswordDialog(TransferActivity.this);
            noFundPasswordDialog.build().setOnViewClicked(new NoFundPasswordDialog.OnViewClicked() {
                @Override
                public void onSure() {
                    SetMoneyPasswordActivity.startIntentResult(TransferActivity.this, true, false, "", 135);
                }
            });
            noFundPasswordDialog.show();
        }
    }

    private void requestCamera() {
        performCodeWithPermission(getString(R.string.permission_app_camera), CommonConst.REQUEST_PERMISSION_CAMERA, CommonConst
                .PERMISSION_CAMERA_STORAGE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        requestCamera();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        QRCodeActivity.startIntentResult(TransferActivity.this, 126);
    }
}
