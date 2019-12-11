package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
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
 * @ClassName: ProjectApplyActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 17:58
 */
public class ProjectApplyActivity extends BaseAppMVPActivity<ServiceAgreementPresenter> implements ServiceAgreementContract.View {

    @BindView(R.id.tv_project_content)
    TextView tvContent;
    @BindView(R.id.tv_project_agree)
    TextView tvAgree;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, ProjectApplyActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_apply;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        int language = SpUtil.getLanguage(mAppContext);
        if (language == LanguageEnums.ENGLISH.getKey()) {
            tvContent.setText(ResourcesUtil.fromHtml(AppSpUtil.getUserEnglishPaper(mAppContext)));
        } else if (language == LanguageEnums.SIMPLIFIED_CHINESE.getKey()){
            tvContent.setText(ResourcesUtil.fromHtml(AppSpUtil.getUserJianTiPaper(mAppContext)));
        } else if (language == LanguageEnums.TRADITIONAL_CHINESE.getKey()){
            tvContent.setText(ResourcesUtil.fromHtml(AppSpUtil.getUserFanTiPaper(mAppContext)));
        }
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_project_apply), null, null, null);
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectSubmitActivity.startIntentForResult(ProjectApplyActivity.this, 100);
            }
        });
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getServiceAgreement();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            finish();
            ProjectApplyListActivity.startIntent(ProjectApplyActivity.this);
        }
    }

    @Override
    public void onAgreeSuccess(ArrayList<ServiceAgreement> serviceAgreementList) {
        int language = SpUtil.getLanguage(mAppContext);
        try {
            if (serviceAgreementList != null && serviceAgreementList.size() > 0) {
                if (language == LanguageEnums.ENGLISH.getKey()) {
                    tvContent.setText(ResourcesUtil.fromHtml(serviceAgreementList.get(0).getContents()));
                } else if (language == LanguageEnums.SIMPLIFIED_CHINESE.getKey()){
                    tvContent.setText(ResourcesUtil.fromHtml(serviceAgreementList.get(1).getContents()));
                } else if (language == LanguageEnums.TRADITIONAL_CHINESE.getKey()){
                    tvContent.setText(ResourcesUtil.fromHtml(serviceAgreementList.get(2).getContents()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServiceAgreementPresenter injectDependencies() {
        return new ServiceAgreementPresenter(this, mAppContext);
    }
}
