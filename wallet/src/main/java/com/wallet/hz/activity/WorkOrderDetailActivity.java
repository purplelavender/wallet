package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.WorkOrderDetailAdapter;
import com.wallet.hz.model.WorkOrder;
import com.wallet.hz.presenter.WorkOrderDetailContract;
import com.wallet.hz.presenter.WorkOrderDetailPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.imagePicker.ImagePicker;
import share.exchange.framework.imagePicker.bean.ImageItem;
import share.exchange.framework.imagePicker.ui.ImagePreviewActivity;
import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: WorkOrderDetailActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:30
 */
public class WorkOrderDetailActivity extends BaseAppMVPActivity<WorkOrderDetailPresenter> implements WorkOrderDetailContract.View {

    @BindView(R.id.rv_order)
    XRecyclerView rvOrder;

    private WorkOrderDetailAdapter mOrderDetailAdapter;

    public static void startIntentResult(Activity activity, int requestCode, WorkOrder workOrder) {
        Intent intent = new Intent(activity, WorkOrderDetailActivity.class);
        intent.putExtra("workorder", workOrder);
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
        setToolTitleClick(getString(R.string.mine_work_order), null, null, null);
        setToolRightClick(getString(R.string.order_close), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public WorkOrderDetailPresenter injectDependencies() {
        return new WorkOrderDetailPresenter(this, mAppContext);
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
        LoginDialogUtil.getInstance().showLoginDialog(WorkOrderDetailActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mOrderDetailAdapter == null) {
            mOrderDetailAdapter = new WorkOrderDetailAdapter(getPresenter().getWorkOrders(), mAppContext);
            rvOrder.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvOrder.setAdapter(mOrderDetailAdapter);
            rvOrder.setLoadingMoreEnabled(false);
            rvOrder.setPullRefreshEnabled(false);

            mOrderDetailAdapter.setViewClickedListener(new WorkOrderDetailAdapter.OnViewClickedListener() {
                @Override
                public void onPhotoView(WorkOrder workorder) {
                    ArrayList<ImageItem> media = new ArrayList<>();
                    ArrayList<String> imageList = new ArrayList<>();
                    imageList.addAll(StringUtil.getUrlStrList(workorder.getImgs(), "|"));
                    for (String s : imageList) {
                        ImageItem localMedia = new ImageItem();
                        localMedia.setPath(s);
                        media.add(localMedia);
                    }
                    Intent intent = new Intent(WorkOrderDetailActivity.this, ImagePreviewActivity.class);
                    intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                    intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, media);
                    intent.putExtra(ImagePreviewActivity.ISORIGIN, false);
                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
                }

                @Override
                public void onReply(WorkOrder workOrder) {
                    WorkOrderAddActivity.startIntentResult(WorkOrderDetailActivity.this, workOrder, 110);
                }
            });
        }
    }

    @Override
    public void onEndOrderSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
