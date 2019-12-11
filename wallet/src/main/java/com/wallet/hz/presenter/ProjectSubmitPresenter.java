package com.wallet.hz.presenter;

import android.content.Context;

import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: ProjectSubmitPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 19:20
 */
public class ProjectSubmitPresenter extends BasePresenter<ProjectSubmitContract.View> implements ProjectSubmitContract.Presenter {

    private Context mContext;

    public ProjectSubmitPresenter(ProjectSubmitContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void submitProject(int type, String name, String web, String paper, String mail, String exchange, String detail) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("names", name);
        map.put("guanwang", web);
        map.put("baipishu", paper);
        map.put("jiaoyisuo", exchange);
        map.put("email", mail);
        map.put("contents", detail);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_PROJECT_SUBMIT, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_add_success));
                getView().onSubmitSuccess();
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
}
