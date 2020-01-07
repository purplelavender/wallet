package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.CoinDetail;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.widget.CircleImageView;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: CoinRecordDetailActivity
 * @Description: 货币交易详情页面
 * @Author: ZL
 * @CreateDate: 2019/10/24 14:49
 */
public class CoinRecordDetailActivity extends BaseAppActivity {

    @BindView(R.id.iv_success)
    CircleImageView ivSuccess;
    @BindView(R.id.tv_success)
    TextView tvSuccess;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_gathering)
    TextView tvGathering;
    @BindView(R.id.tv_transfer)
    TextView tvTransfer;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_hash_value)
    TextView tvHashValue;
    @BindView(R.id.tv_hash_height)
    TextView tvHashHeight;

    @BindView(R.id.ll_fee)
    LinearLayout llFee;
    @BindView(R.id.line_fee)
    View lineFee;
    @BindView(R.id.ll_hash_value)
    LinearLayout llHashValue;
    @BindView(R.id.line_hash_height)
    View lineHashHeight;
    @BindView(R.id.ll_hash_height)
    LinearLayout llHashHeight;
    @BindView(R.id.line_hash_value)
    View lineHashNalue;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.line_remark)
    View lineRemark;

    private CoinDetail coinDetail;

    public static void startIntent(Activity activity, CoinDetail coinDetail) {
        Intent intent = new Intent(activity, CoinRecordDetailActivity.class);
        intent.putExtra("coinDetail", coinDetail);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_history_record_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
        coinDetail = (CoinDetail) getIntent().getSerializableExtra("coinDetail");
        changeUI();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(coinDetail.getCoinname(), null, null, null);
        tvGathering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnvironmentUtil.copyTextContent(mAppContext, tvGathering.getText().toString());
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_copy_success));
            }
        });
        tvTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnvironmentUtil.copyTextContent(mAppContext, tvTransfer.getText().toString());
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_copy_success));
            }
        });
        tvHashValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnvironmentUtil.copyTextContent(mAppContext, tvHashValue.getText().toString());
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_copy_success));
            }
        });
    }

    @Override
    protected void initCurrentData() {}

    private void changeUI() {
        ivSuccess.setImageDrawable(coinDetail.getStatusDrawable(mAppContext));
        tvSuccess.setText(coinDetail.getTypeSuccessText(mAppContext));
        tvType.setText(coinDetail.getTypeText(mAppContext));
        tvAmount.setText(BigDecimalUtils.formatServiceNumber(coinDetail.getAmount()) + " " + coinDetail.getCoinname());
        tvTime.setText(coinDetail.getCreateTime());
        tvGathering.setText(coinDetail.getAddress());
        tvTransfer.setText(coinDetail.getFromaddress());
        tvFee.setText(BigDecimalUtils.formatServiceNumber(coinDetail.getKgfamount()) + " " + coinDetail.getCoinname());
        tvHashValue.setText(coinDetail.getTxash());
        tvHashHeight.setText(coinDetail.getTxheight());
        tvRemark.setText(coinDetail.getRemarks());

        llFee.setVisibility(coinDetail.isIn() ? View.GONE : View.VISIBLE);
        llHashValue.setVisibility(coinDetail.isIn() && !coinDetail.isSpecialCoin() ? View.VISIBLE : View.GONE);
        llHashHeight.setVisibility(coinDetail.isIn() && !coinDetail.isSpecialCoin() ? View.VISIBLE : View.GONE);
        llRemark.setVisibility(coinDetail.isIn() || !coinDetail.isSpecialCoin() ? View.GONE : View.VISIBLE);

        lineFee.setVisibility(coinDetail.isIn() ? View.GONE : View.VISIBLE);
        lineHashHeight.setVisibility(coinDetail.isIn() && !coinDetail.isSpecialCoin() ? View.VISIBLE : View.GONE);
        lineHashNalue.setVisibility(coinDetail.isIn() && !coinDetail.isSpecialCoin() ? View.VISIBLE : View.GONE);
        lineRemark.setVisibility(coinDetail.isIn() || !coinDetail.isSpecialCoin() ? View.GONE : View.VISIBLE);
    }
}
