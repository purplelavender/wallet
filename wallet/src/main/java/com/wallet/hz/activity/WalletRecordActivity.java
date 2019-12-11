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
import com.wallet.hz.adapter.MySpinnerAdapter;
import com.wallet.hz.adapter.WalletRecordAdapter;
import com.wallet.hz.model.CoinDetail;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.presenter.WalletRecordContract;
import com.wallet.hz.presenter.WalletRecordPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: WalletRecordActivity
 * @Description: 钱包历史记录页面
 * @Author: ZL
 * @CreateDate: 2019/11/07 14:41
 */
public class WalletRecordActivity extends BaseAppMVPActivity<WalletRecordPresenter> implements WalletRecordContract.View {

    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.tv_left)
    TextView tvBack;
    @BindView(R.id.sp_history_type)
    AppCompatSpinner spType;
    @BindView(R.id.rv_history_record)
    XRecyclerView rvRecord;

    private WalletRecordAdapter mAdapter;
    private int type = 0;
    private boolean isFirst = true;
    private String coinName = "";
    private ArrayList<String> nameList = new ArrayList<>();

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, WalletRecordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wallet_history_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);
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
                } else {
                    type = position;
                    coinName = nameList.get(position);
                    rvRecord.refresh();
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
        getPresenter().getWalletCoinList();
        getPresenter().getRecordList(type, coinName, true);
    }

    @Override
    public WalletRecordPresenter injectDependencies() {
        return new WalletRecordPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(WalletRecordActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new WalletRecordAdapter(getPresenter().getWalletRecords(), mAppContext);
            rvRecord.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvRecord.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvRecord.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    CoinDetail coinDetail = (CoinDetail) item;
                    CoinRecordDetailActivity.startIntent(WalletRecordActivity.this, coinDetail);
                }
            });

            rvRecord.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getRecordList(type, coinName, true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getRecordList(type, coinName, false);
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
            if (rvRecord != null) {
                rvRecord.loadMoreComplete();
                rvRecord.refreshComplete();
            }
        }
        showEmpty();
    }

    @Override
    public void onTypeListSuccess(ArrayList<WalletInfo> walletInfos) {
        nameList.add(0, getString(R.string.state_mingxi_type_all));
        nameList.add(1, getString(R.string.wallet_in));
        nameList.add(2, getString(R.string.wallet_out));
        if (walletInfos != null && walletInfos.size() > 0) {
            for (WalletInfo info : walletInfos) {
                nameList.add(info.getCoinName());
            }
        }
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(nameList, mAppContext);
        spType.setAdapter(spinnerAdapter);
    }

    private void showEmpty() {
        if (mAdapter.getItemCount() > 0) {
            rvRecord.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        } else {
            rvRecord.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }
}
