package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: WorkOrderAddPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:18
 */
public class WorkOrderAddPresenter extends BasePresenter<WorkOrderAddContract.View> implements WorkOrderAddContract.Presenter {

    private Context mContext;

    public WorkOrderAddPresenter(WorkOrderAddContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void uploadImg(ArrayList<File> files) {
        getView().showLoading(mContext.getString(R.string.interface_upload_photo));
        apiManager.upload(TAG, ApiConstant.URL_UPLOAD, null, files, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                try {
                    // {"msg":"请求成功","code":0,"url":"http://shansong.oss-cn-beijing.aliyuncs.com/20191109/8afff3d044b6446eb5289496347f30ba.jpg,http://shansong.oss-cn-beijing.aliyuncs.com/20191109/79ebad36389b43efb8ec73f0e8d430bd.jpg"}
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    String img = jsonObject.getString("url");
                    getView().onImgUploadSuccess(img);
                } catch (Exception e) {
                    e.printStackTrace();
                    getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_error_upload));
                }
            }

            @Override
            public void onNoLogin() {
                getView().hideLoading();
                getView().showLoginDialog();

            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().hideLoading();
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    @Override
    public void addWorkOrder(String title, String content, String mail, String imgs) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("contents", content);
        map.put("emails", mail);
        map.put("imgs", imgs);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ORDER_INSERT, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().hideLoading();
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_add_success));
                getView().onAddOrEeplySuccess();
            }

            @Override
            public void onNoLogin() {
                getView().hideLoading();
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().hideLoading();
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }

    @Override
    public void replyWorkOrder(String id, String content, String mail, String imgs) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("contents", content);
        map.put("emails", mail);
        map.put("imgs", imgs);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ORDER_REPLY, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().hideLoading();
                getView().onAddOrEeplySuccess();
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_reply_success));
            }

            @Override
            public void onNoLogin() {
                getView().hideLoading();
                getView().showLoginDialog();
            }

            @Override
            public void onFailed(int statusCode, String errMsg) {
                getView().hideLoading();
                getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
            }
        });
    }
}
