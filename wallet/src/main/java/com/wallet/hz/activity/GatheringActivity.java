package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.presenter.GatheringContract;
import com.wallet.hz.presenter.GatheringPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.BitmapUtil;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: GatheringActivity
 * @Description: 货币收款页面
 * @Author: ZL
 * @CreateDate: 2019/10/24 15:12
 */
public class GatheringActivity extends BaseAppMVPActivity<GatheringPresenter> implements GatheringContract.View {

    @BindView(R.id.tv_gathering_code)
    TextView tvCode;
    @BindView(R.id.tv_gathering_shot)
    TextView tvShot;
    @BindView(R.id.tv_gathering_rule)
    TextView tvRule;
    @BindView(R.id.iv_code)
    ImageView ivCode;

    private String name = "";
    private WalletInfo mWalletInfo;

    public static void startIntent(Activity activity, WalletInfo walletInfo) {
        Intent intent = new Intent(activity, GatheringActivity.class);
        intent.putExtra("wallet", walletInfo);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gathering;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        tvRule.setText(getString(R.string.wallet_gathering_rule, name, name, name));
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnvironmentUtil.copyTextContent(mAppContext, tvCode.getText().toString());
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_copy_success));
            }
        });
        tvShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapUtil.screenShot(GatheringActivity.this);
            }
        });
    }

    @Override
    protected void initCurrentData() {
        onAddressSuccess(mWalletInfo.getAddress());
        getPresenter().createImage(mWalletInfo.getAddress());
    }

    @Override
    public GatheringPresenter injectDependencies() {
        mWalletInfo = (WalletInfo) getIntent().getSerializableExtra("wallet");
        name = mWalletInfo.getCoinName();
        return new GatheringPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(GatheringActivity.this);
    }

    @Override
    public void onAddressSuccess(String code) {
        tvCode.setText(code);
    }

    @Override
    public void onCreateSuccess(Bitmap bitmap) {
        ivCode.setImageBitmap(bitmap);
    }
}
