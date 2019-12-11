package com.wallet.hz.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.GoogleStepOne;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.HashMap;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.executor.AsyncExecutor;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: BindGoogleOnePresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 10:20
 */
public class BindGoogleOnePresenter extends BasePresenter<BindGoogleOneContract.View> implements BindGoogleOneContract.Presenter {

    private Context mContext;

    public BindGoogleOnePresenter(BindGoogleOneContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void getGoogleSecret() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.get(TAG, ApiConstant.URL_GUGE_CODE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                GoogleStepOne googleStepOne = (GoogleStepOne) JSON.parseObject(response, GoogleStepOne.class);
                String secret = googleStepOne.getSecret();
                if (!StringUtil.isEmpty(secret)) {
                    getView().onSecretSuccess(secret);
                    String username = AppSpUtil.getUserName(mContext);
                    String data = "otpauth://totp/" + username + "?secret=" + secret + "&issuer=https://www.hilbert.vip";
                    createImage(data);
                } else {
                    getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.no_network));
                }
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

    private void createImage(final String secret) {
        executor.execute(new AsyncExecutor.Worker<Bitmap>() {
            @Override
            protected Bitmap doInBackground() {
                int size = EnvironmentUtil.dip2px(mContext, 120);
                return QRCodeEncoder.syncEncodeQRCode(secret, size, Color.BLACK);
            }

            @Override
            protected void onPostExecute(Bitmap data) {
                if (data != null) {
                    getView().onSecretImage(data);
                } else {
                    getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_google_rqcode_error));
                }
            }
        });
    }
}
