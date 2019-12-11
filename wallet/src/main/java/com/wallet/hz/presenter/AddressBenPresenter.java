package com.wallet.hz.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.model.CoinAddress;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.util.ArrayList;
import java.util.HashMap;

import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: AddressBenPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 16:09
 */
public class AddressBenPresenter extends BasePresenter<AddressBenContract.View> implements AddressBenContract.Presenter {

    private Context mContext;
    private ArrayList<CoinAddress> mCoinAddresses = new ArrayList<>();

    public AddressBenPresenter(AddressBenContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {
        getView().updateAdapter();
    }

    @Override
    public void getAddressList(boolean isFirst) {
        if (isFirst) {
            page = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", ApiConstant.PAGE_LIMIT);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.get(TAG, ApiConstant.URL_ADDRESS_LIST, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                if (page == 1) {
                    mCoinAddresses.clear();
                }
                try {
                    ArrayList<CoinAddress> tempList = (ArrayList<CoinAddress>) JSON.parseArray(response, CoinAddress.class);
                    if (tempList != null && tempList.size() > 0) {
                        mCoinAddresses.addAll(tempList);
                        page ++;
                    }
                }catch (Exception e) {
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

    @Override
    public void deleteAddress(int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_ADDRESS_DELETE, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_delete_success));
                getView().onDeleteSuccess();
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

    public ArrayList<CoinAddress> getCoinAddresses() {
        return mCoinAddresses;
    }
}
