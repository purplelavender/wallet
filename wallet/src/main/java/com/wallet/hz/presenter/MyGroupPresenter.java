package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.GroupInfo;
import com.wallet.hz.model.GroupInfoData;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: MyGroupPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/29 15:11
 */
public class MyGroupPresenter extends BasePresenter<MyGroupContract.View> implements MyGroupContract.Presenter {

    private Context mContext;
    private ArrayList<GroupInfoData> mGroupInfoData = new ArrayList<>();

    public MyGroupPresenter(MyGroupContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getGroupInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_GROUP_INFO, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    GroupInfo groupInfo = JSON.parseObject(response, GroupInfo.class);
                    ArrayList<GroupInfoData> tempList = groupInfo.getShrecordEntity();
                    if (tempList != null && tempList.size() > 0) {
                        mGroupInfoData.clear();
                        mGroupInfoData.addAll(tempList);
                    }
                    getView().onGroupSuccess(groupInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().onGroupSuccess(null);
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
                getView().onGroupSuccess(null);
                getView().updateAdapter();
            }
        });
    }

    public ArrayList<GroupInfoData> getGroupInfoData() {
        return mGroupInfoData;
    }
}
