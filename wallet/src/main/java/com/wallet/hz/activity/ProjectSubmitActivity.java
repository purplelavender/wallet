package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.adapter.MySpinnerAdapter;
import com.wallet.hz.presenter.ProjectSubmitContract;
import com.wallet.hz.presenter.ProjectSubmitPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ProjectSubmitActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 19:19
 */
public class ProjectSubmitActivity extends BaseAppMVPActivity<ProjectSubmitPresenter> implements ProjectSubmitContract.View {

    @BindView(R.id.sp_project_coin)
    AppCompatSpinner spCoin;
    @BindView(R.id.et_project_name)
    EditText etName;
    @BindView(R.id.et_project_web)
    EditText etWeb;
    @BindView(R.id.et_project_paper)
    EditText etPaper;
    @BindView(R.id.et_project_mail)
    EditText etMail;
    @BindView(R.id.et_project_detail)
    EditText etDetail;
    @BindView(R.id.et_project_exchange)
    EditText etExchange;
    @BindView(R.id.tv_project_submit)
    TextView tvSubmit;

    private int type = 0;
    private boolean isFirst = true;

    public static void startIntentForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ProjectSubmitActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_apply_submit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        String[] languages = getResources().getStringArray(R.array.project_type);
        ArrayList<String> languageList = new ArrayList<>();
        languageList.add(0, getString(R.string.project_coin_attribute));
        for (int i = 0; i < languages.length; i++) {
            languageList.add(languages[i]);
        }
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(languageList, mAppContext);
        spCoin.setAdapter(spinnerAdapter);
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_project_apply), null, null, null);
        setToolRightClick(getString(R.string.wallet_history_record), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectApplyListActivity.startIntent(ProjectSubmitActivity.this);
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyEditCheck();
            }
        });
        spCoin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 第一次加载（即初始化的默认加载）
                if (isFirst) {
                    //不做任何处理
                } else {
                    type = position;
                }
                isFirst = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public ProjectSubmitPresenter injectDependencies() {
        return new ProjectSubmitPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ProjectSubmitActivity.this);
    }

    @Override
    public void onSubmitSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    private void replyEditCheck() {
        String name = etName.getText().toString();
        String web = etWeb.getText().toString();
        String paper = etPaper.getText().toString();
        String exchange = etExchange.getText().toString();
        String mail = etMail.getText().toString();
        String detail = etDetail.getText().toString();
        if (StringUtil.isEmpty(name)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_project_name_empty));
            return;
        }
        if (StringUtil.isEmpty(web)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_project_web_empty));
            return;
        }
        if (StringUtil.isEmpty(paper)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_project_paper_empty));
            return;
        }
        if (StringUtil.isEmpty(exchange)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_project_exchange_empty));
            return;
        }
        if (StringUtil.isEmpty(mail)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_project_mail_empty));
            return;
        }
        if (StringUtil.isEmpty(detail)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_project_mark_empty));
            return;
        }
        if (type <= 0) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_project_coin_empty));
            return;
        }
        getPresenter().submitProject(type, name, web, paper, mail, exchange, detail);
    }
}
