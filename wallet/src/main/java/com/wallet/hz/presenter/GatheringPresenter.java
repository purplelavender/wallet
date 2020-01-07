package com.wallet.hz.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.alibaba.fastjson.JSONObject;
import com.wallet.hz.R;
import com.wallet.hz.constant.ApiConstant;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.InterfaceErrorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.executor.AsyncExecutor;
import share.exchange.framework.http.response.JsonResHandler;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.FileUtil;
import share.exchange.framework.utils.ImageCompressUtils;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: GatheringPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 15:11
 */
public class GatheringPresenter extends BasePresenter<GatheringContract.View> implements GatheringContract.Presenter {

    private Context mContext;

    public GatheringPresenter(GatheringContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void destroyView() {
        super.destroyView();
        FileUtil.clearDirectory(new File(FileUtil.getFullPath(mContext, "image/temp/")));
    }

    @Override
    public void createImage(final String secret) {
        executor.execute(new AsyncExecutor.Worker<Bitmap>() {
            @Override
            protected Bitmap doInBackground() {
                int size = EnvironmentUtil.dip2px(mContext, 200);
                return QRCodeEncoder.syncEncodeQRCode(secret, size, Color.BLACK);
            }

            @Override
            protected void onPostExecute(Bitmap data) {
                if (data != null) {
                    getView().onCreateSuccess(data);
                } else {
                    getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_google_rqcode_error));
                }
            }
        });
    }

    @Override
    public void uploadImage(final ArrayList<File> files) {
        getView().showLoading(mContext.getString(R.string.interface_upload_photo));
        ArrayList<String> imageList = new ArrayList<>();
        for (File file : files) {
            imageList.add(file.getAbsolutePath());
        }
        ImageCompressUtils imageCompresser = ImageCompressUtils.from(mContext, FileUtil.getFullPath(mContext, "image/temp/"))
                .load(imageList);
        imageCompresser.execute(new ImageCompressUtils.OnCompressListener() {
            @Override
            public void onSuccess(ArrayList<File> file) {
                upload(file);
            }

            @Override
            public void onError(Throwable e) {
                upload(files);
            }
        });
    }

    private void upload(ArrayList<File> files) {
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
                if (statusCode == -1) {
                    getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.wallet_new_photo_upload_fail));
                } else {
                    getView().showToast(CommonToast.ToastType.TEXT, InterfaceErrorUtil.changeLanguageErrorString(mContext, errMsg));
                }
            }
        });
    }

    @Override
    public void specialGathering(String fromAddress, String toAddress, String count, String name, String image) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("toaddress", toAddress);
        map.put("fromaddress", fromAddress);
        map.put("amount", count);
        map.put("biname", name.toLowerCase());
        map.put("images", image);
        map.put("token", AppSpUtil.getUserToken(mContext));
        apiManager.post(TAG, ApiConstant.URL_WALLET_SPECIAL_GATHERING, map, new JsonResHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                getView().hideLoading();
                getView().onGatheringSuccess();
                getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.interface_submit_success));
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
