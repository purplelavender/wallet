package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.GroupFriend;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: GroupListPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/29 15:12
 */
public class GroupListPresenter extends BasePresenter<GroupListContract.View> implements GroupListContract.Presenter {

    private Context mContext;
    private ArrayList<GroupFriend> mGroupFriends = new ArrayList<>();

    public GroupListPresenter(GroupListContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getGroupFriendList(String name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("codes", name);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_INVITE_EARNING_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                mGroupFriends.clear();
                try {
                    ArrayList<GroupFriend> tempList = (ArrayList<GroupFriend>) JSON.parseArray(response, GroupFriend.class);
                    if (tempList != null && tempList.size() > 0) {
                        mGroupFriends.addAll(tempList);
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
                getView().updateAdapter();
            }
        });
    }

    public ArrayList<GroupFriend> getGroupFriends() {
        return mGroupFriends;
    }
}
