package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.ServiceAgreement;
import com.wallet.hz.presenter.ServiceAgreementContract;
import com.wallet.hz.presenter.ServiceAgreementPresenter;
import com.wallet.hz.utils.AppSpUtil;

import java.util.ArrayList;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.SpUtil;

/**
 * @ClassName: ServiceAgreementActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/11 16:38
 */
public class ServiceAgreementActivity extends BaseAppMVPActivity<ServiceAgreementPresenter> implements ServiceAgreementContract.View {

    @BindView(R.id.tv_service_agreement)
    TextView tvAgreement;

    private int type = 0;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, ServiceAgreementActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_agreement;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        tvAgreement.setMovementMethod(ScrollingMovementMethod.getInstance());
        int language = SpUtil.getLanguage(mAppContext);
        if (language == LanguageEnums.ENGLISH.getKey()) {
            tvAgreement.setText(ResourcesUtil.fromHtml(AppSpUtil.getUserEnglishInfo(mAppContext)));
        } else if (language == LanguageEnums.SIMPLIFIED_CHINESE.getKey()){
            tvAgreement.setText(ResourcesUtil.fromHtml(AppSpUtil.getUserJianTiInfo(mAppContext)));
        } else if (language == LanguageEnums.TRADITIONAL_CHINESE.getKey()){
            tvAgreement.setText(ResourcesUtil.fromHtml(AppSpUtil.getUserFanTiInfo(mAppContext)));
        }
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getServiceAgreement();
    }

    @Override
    public ServiceAgreementPresenter injectDependencies() {
        return new ServiceAgreementPresenter(this, mAppContext);
    }

    @Override
    public void onAgreeSuccess(ArrayList<ServiceAgreement> serviceAgreementList) {
        int language = SpUtil.getLanguage(mAppContext);
        try {
            if (serviceAgreementList != null && serviceAgreementList.size() > 0) {
                if (language == LanguageEnums.ENGLISH.getKey()) {
                    tvAgreement.setText(ResourcesUtil.fromHtml(serviceAgreementList.get(4).getContents()));
                } else if (language == LanguageEnums.SIMPLIFIED_CHINESE.getKey()){
                    tvAgreement.setText(ResourcesUtil.fromHtml(serviceAgreementList.get(5).getContents()));
                } else if (language == LanguageEnums.TRADITIONAL_CHINESE.getKey()){
                    tvAgreement.setText(ResourcesUtil.fromHtml(serviceAgreementList.get(3).getContents()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
