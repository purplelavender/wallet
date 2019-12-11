package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.ProjectApply;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ProjectApplyListPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 17:27
 */
public class ProjectApplyListPresenter extends BasePresenter<ProjectApplyListContract.View> implements ProjectApplyListContract.Presenter {

    private Context mContext;
    private ArrayList<ProjectApply> mProjectApplies = new ArrayList<>();

    public ProjectApplyListPresenter(ProjectApplyListContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getProjectList(boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_PROJECT_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (page == 1) {
                    mProjectApplies.clear();
                }
                try {
                    ArrayList<ProjectApply> tempList = (ArrayList<ProjectApply>) JSON.parseArray(response, ProjectApply.class);
                    if (tempList != null && tempList.size() > 0) {
                        mProjectApplies.addAll(tempList);
                        page ++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().updateAdapter();
            }

            @Override
            public void onNoLogin() {
                getView().showLoginDialog();
                getView().updateAdapter();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                getView().updateAdapter();
            }
        });
    }

    public ArrayList<ProjectApply> getProjectApplies() {
        return mProjectApplies;
    }
}
