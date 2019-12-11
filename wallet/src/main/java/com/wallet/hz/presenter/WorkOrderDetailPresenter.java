package com.wallet.hz.presenter;

import android.content.Context;

import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.WorkOrder;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: WorkOrderDetailPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:17
 */
public class WorkOrderDetailPresenter extends BasePresenter<WorkOrderDetailContract.View> implements WorkOrderDetailContract.Presenter {

    private Context mContext;
    private ArrayList<WorkOrder> mWorkOrders = new ArrayList<>();

    public WorkOrderDetailPresenter(WorkOrderDetailContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void endOrder(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ORDER_END, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().onEndOrderSuccess();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    public ArrayList<WorkOrder> getWorkOrders() {
        return mWorkOrders;
    }
}
