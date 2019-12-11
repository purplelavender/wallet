package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.FinancialDetailAdapter;
import com.wallet.hz.adapter.MySpinnerAdapter;
import com.wallet.hz.presenter.FinancialDetailContract;
import com.wallet.hz.presenter.FinancialDetailPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: FinancialDetailActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 09:36
 */
public class FinancialDetailActivity extends BaseAppMVPActivity<FinancialDetailPresenter> implements FinancialDetailContract.View {

    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.tv_left)
    TextView tvBack;
    @BindView(R.id.sp_financial_type)
    AppCompatSpinner spType;
    @BindView(R.id.rv_financial_detail)
    XRecyclerView rvFinancial;

    private int type = 0;
    private FinancialDetailAdapter mAdapter;
    private boolean isFirst = true;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, FinancialDetailActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_financial_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);

        String[] languages = getResources().getStringArray(R.array.mingxi_type);
        ArrayList<String> languageList = new ArrayList<>();
        for (int i = 0; i < languages.length; i++) {
            languageList.add(languages[i]);
        }
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(languageList, mAppContext);
        spType.setAdapter(spinnerAdapter);
    }

    @Override
    protected void initEvent() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 第一次加载（即初始化的默认加载）
                if (isFirst) {
                    //不做任何处理
                    type = 0;
                } else {
                    type = position;
                    rvFinancial.refresh();
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
        getPresenter().getFinancialList(type, true);
    }

    @Override
    public FinancialDetailPresenter injectDependencies() {
        return new FinancialDetailPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(FinancialDetailActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new FinancialDetailAdapter(getPresenter().getFinancialDetailInfos(), mAppContext);
            rvFinancial.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvFinancial.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvFinancial.setAdapter(mAdapter);

            rvFinancial.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getFinancialList(type, true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getFinancialList(type, false);
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
            if (rvFinancial != null) {
                rvFinancial.loadMoreComplete();
                rvFinancial.refreshComplete();
            }
        }
        showEmpty();
    }

    private void showEmpty() {
        if (mAdapter.getItemCount() > 0) {
            rvFinancial.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        } else {
            rvFinancial.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }
}
