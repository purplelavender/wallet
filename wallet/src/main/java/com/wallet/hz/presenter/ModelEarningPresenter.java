package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.ModelReleaseInfo;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ModelEarningPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/04 11:10
 */
public class ModelEarningPresenter extends BasePresenter<ModelEarningContract.View> implements ModelEarningContract.Presenter {

    private Context mContext;
    private ArrayList<ModelReleaseInfo> mModelReleaseInfos = new ArrayList<>();

    public ModelEarningPresenter(ModelEarningContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getModelEarningList(boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MODEL_EARNING_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                if (page == 1) {
                    mModelReleaseInfos.clear();
                }
                try {
                    ArrayList<ModelReleaseInfo> tempList = (ArrayList<ModelReleaseInfo>) JSON.parseArray(response, ModelReleaseInfo.class);
                    if (tempList != null && tempList.size() > 0) {
                        mModelReleaseInfos.addAll(tempList);
                        page ++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onNoLogin() {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                if (!getView().isFragmentAdd()) {
                    return;
                }
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().updateAdapter();
            }
        });
    }

    public ArrayList<ModelReleaseInfo> getModelReleaseInfos() {
        return mModelReleaseInfos;
    }
}
