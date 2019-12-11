package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
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
 * @ClassName: WorkOrderListPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:15
 */
public class WorkOrderListPresenter extends BasePresenter<WorkOrderListContract.View> implements WorkOrderListContract.Presenter {

    private Context mContext;
    private ArrayList<WorkOrder> mWorkOrders = new ArrayList<>();

    public WorkOrderListPresenter(WorkOrderListContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getOrderList(int userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.get(TAG, ApiConstant.URL_ORDER_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                mWorkOrders.clear();
                try {
                    ArrayList<WorkOrder> workOrders = (ArrayList<WorkOrder>) JSON.parseArray(response, WorkOrder.class);
                    if (workOrders != null && workOrders.size() > 0) {
                        mWorkOrders.addAll(workOrders);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().onOrderListFailed();
            }
        });
    }

    public ArrayList<WorkOrder> getWorkOrders() {
        return mWorkOrders;
    }
}
