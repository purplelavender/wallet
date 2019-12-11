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
import share.exchange.framework.widget.CustomCountDownTimer;

/**
 * @ClassName: BindPhoneMailPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 14:46
 */
public class BindPhoneMailPresenter extends BasePresenter<BindPhoneMailContract.View> implements BindPhoneMailContract.Presenter {

    private Context mContext;
    private CodeTimer mCodeTimer;

    public BindPhoneMailPresenter(BindPhoneMailContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void destroyView() {
        super.destroyView();
        stopTimer();
    }

    @Override
    public void getPhoneCode(String phone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_PHONECODES, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_send_success));
                startTimer();
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

    @Override
    public void getMailCode(String mail) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", mail);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_MAILCODES, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_send_success));
                startTimer();
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

    @Override
    public void bindPhone(String password, String phone, String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", password);
        map.put("phone", phone);
        map.put("codes", code);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_BING_PHONE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_bind_success));
                getView().onBindSuccess();
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

    @Override
    public void bindMail(String password, String email, String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", password);
        map.put("email", email);
        map.put("codes", code);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_BING_MAIL, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_bind_success));
                getView().onBindSuccess();
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

    public class CodeTimer extends CustomCountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CodeTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getView().setGetCodeText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            getView().setGetCodeText(mContext.getString(R.string.bind_get_code_again));
        }
    }

    private void startTimer() {
        if (mCodeTimer == null) {
            mCodeTimer = new CodeTimer(60000, 1000);
        }
        mCodeTimer.start();
    }

    private void stopTimer() {
        if (mCodeTimer != null) {
            mCodeTimer.cancel();
            mCodeTimer = null;
        }
    }
}
