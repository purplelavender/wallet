package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.AddressListAdapter;
import com.wallet.hz.model.CoinAddress;
import com.wallet.hz.presenter.AddressBenContract;
import com.wallet.hz.presenter.AddressBenPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: AddressBenActivity
 * @Description: 转账地址列表页面
 * @Author: ZL
 * @CreateDate: 2019/10/24 16:12
 */
public class AddressBenActivity extends BaseAppMVPActivity<AddressBenPresenter> implements AddressBenContract.View {

    @BindView(R.id.rv_order)
    XRecyclerView rvAddress;

    private AddressListAdapter mAdapter;
    private int deletePosition = 0;

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, AddressBenActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_work_order_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.wallet_address_ben), null, null, null);
        setToolRightClick("", null, ResourcesUtil.getDrawable(mAppContext, R.drawable.icon_tianjia), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressAddActivity.startIntentResult(AddressBenActivity.this, 123);
            }
        });
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getAddressList(true);
    }

    @Override
    public AddressBenPresenter injectDependencies() {
        return new AddressBenPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initCurrentData();
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(AddressBenActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new AddressListAdapter(getPresenter().getCoinAddresses(), mAppContext);
            rvAddress.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvAddress.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvAddress.setAdapter(mAdapter);

            rvAddress.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getAddressList(true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getAddressList(false);
                }
            });

            mAdapter.setOnClickedListener(new AddressListAdapter.OnClickedListener() {
                @Override
                public void onDelete(CoinAddress coinAddress) {
                    deletePosition = getPresenter().getCoinAddresses().indexOf(coinAddress);
                    getPresenter().deleteAddress(coinAddress.getId());
                }

                @Override
                public void onItemClick(CoinAddress coinAddress) {
                    Intent intent = new Intent();
                    intent.putExtra("code", coinAddress.getAddress());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
            rvAddress.loadMoreComplete();
            rvAddress.refreshComplete();
        }
        showEmpty();
    }

    @Override
    public void onDeleteSuccess() {
        getPresenter().getCoinAddresses().remove(deletePosition);
        updateAdapter();
    }

    private void showEmpty() {
        if (mAdapter.getItemCount() > 0) {
            showSuccessStateLayout();
        } else {
            showEmptyStateLayout();
        }
    }
}
