package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.ModelReleaseInfo;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: EarningDetailActivity
 * @Description: 收益详情页面
 * @Author: ZL
 * @CreateDate: 2019/11/13 15:04
 */
public class EarningDetailActivity extends BaseAppActivity {

    @BindView(R.id.tv_earning_amount)
    TextView tvAmount;
    @BindView(R.id.tv_earning_time)
    AutoSizeTextView tvTime;
    @BindView(R.id.tv_earning_type)
    AutoSizeTextView tvType;
    @BindView(R.id.tv_earning_join)
    AutoSizeTextView tvJoin;
    @BindView(R.id.tv_earning_exchange)
    AutoSizeTextView tvExchange;
    @BindView(R.id.tv_earning_award)
    AutoSizeTextView tvAward;
    @BindView(R.id.tv_earning_destruction)
    AutoSizeTextView tvDestruction;
    @BindView(R.id.tv_earning_rate)
    AutoSizeTextView tvRate;
    @BindView(R.id.tv_earning_wallet)
    AutoSizeTextView tvWallet;
    @BindView(R.id.tv_earning_benjin)
    AutoSizeTextView tvBenjin;

    public static void startIntent(Activity activity, ModelReleaseInfo modelReleaseInfo) {
        Intent intent = new Intent(activity, EarningDetailActivity.class);
        intent.putExtra("modelReleaseInfo", modelReleaseInfo);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_earning_record_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();

        ModelReleaseInfo modelReleaseInfo = (ModelReleaseInfo) getIntent().getSerializableExtra("modelReleaseInfo");
        tvAmount.setText(BigDecimalUtils.formatServiceNumber(modelReleaseInfo.getWaamount()) + " USDT");
        tvTime.setText(modelReleaseInfo.getCreateTime());
        tvType.setText(modelReleaseInfo.getJoinTypeText(mAppContext));
        tvJoin.setText(BigDecimalUtils.formatServiceNumber(modelReleaseInfo.getAmount()) + " USDT");
        tvExchange.setText(BigDecimalUtils.formatServiceNumber(modelReleaseInfo.getWaamount()) + " USDT");
        tvAward.setText(BigDecimalUtils.formatServiceNumber(modelReleaseInfo.getCdamount()) + " USDT");
        tvDestruction.setText(BigDecimalUtils.formatServiceNumber(modelReleaseInfo.getXhamount()) + " HS");
        tvRate.setText("1 USDT = " + BigDecimalUtils.div("1", modelReleaseInfo.getHuilv() + "", 8) + " HS");
        tvWallet.setText(BigDecimalUtils.formatServiceNumber(modelReleaseInfo.getSfamount()) + " HS");
        tvBenjin.setText(BigDecimalUtils.formatServiceNumber(modelReleaseInfo.getUsdtsfamount()) + " USDT");

    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }
}
