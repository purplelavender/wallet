package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.presenter.GatheringContract;
import com.wallet.hz.presenter.GatheringPresenter;
import com.wallet.hz.utils.GlideLoader;
import com.wallet.hz.utils.LoginDialogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.CommonConst;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.imagePicker.ImagePicker;
import share.exchange.framework.imagePicker.bean.ImageItem;
import share.exchange.framework.imagePicker.ui.ImageGridActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.BitmapUtil;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.GlideImageUtil;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;
import share.exchange.framework.widget.MyScrollView;

/**
 * @ClassName: GatheringActivity
 * @Description: 货币收款页面
 * @Author: ZL
 * @CreateDate: 2019/10/24 15:12
 */
public class GatheringActivity extends BaseAppMVPActivity<GatheringPresenter> implements GatheringContract.View {

    @BindView(R.id.tv_gathering_code)
    TextView tvCode;
    @BindView(R.id.tv_gathering_shot)
    TextView tvShot;
    @BindView(R.id.tv_gathering_rule)
    TextView tvRule;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    //特殊币种的页面显示
    @BindView(R.id.ll_new_coin)
    LinearLayout llNewCoin;
    @BindView(R.id.et_gathering_count)
    EditText etCount;
    @BindView(R.id.et_gathering_address)
    EditText etAddress;
    @BindView(R.id.iv_gathering_add)
    ImageView ivAdd;
    @BindView(R.id.iv_gathering_add_del)
    ImageView ivAddDel;
    @BindView(R.id.iv_gathering_lizi)
    ImageView ivLiZi;
    @BindView(R.id.tv_gathering_submit)
    TextView tvSubmit;
    @BindView(R.id.scroll_view)
    MyScrollView scrollView;

    private String name = "", liziPath = "";
    private WalletInfo mWalletInfo;
    private ArrayList<ImageItem> selectList = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<>();
    private boolean isSpecial = false;

    public static void startIntent(Activity activity, WalletInfo walletInfo) {
        Intent intent = new Intent(activity, GatheringActivity.class);
        intent.putExtra("wallet", walletInfo);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gathering;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        tvRule.setText(getString(R.string.wallet_gathering_rule, name, name, name));

        tvRule.setVisibility(isSpecial ? View.GONE : View.VISIBLE);
        tvSubmit.setVisibility(!isSpecial ? View.GONE : View.VISIBLE);
        llNewCoin.setVisibility(!isSpecial ? View.GONE : View.VISIBLE);
        initLiZiPhoto();
        changeBottomPhotoView();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnvironmentUtil.copyTextContent(mAppContext, tvCode.getText().toString());
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_copy_success));
            }
        });
        tvShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0, 0);
                BitmapUtil.screenShot(GatheringActivity.this);
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactsPermission();
            }
        });
        ivAddDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectList.clear();
                imageList.clear();
                changeBottomPhotoView();
            }
        });
        ivLiZi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GatheringPhotoActivity.startIntent(GatheringActivity.this, name);
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEdit();
            }
        });
        etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (BigDecimalUtils.isEightPoint(s.toString())){
                    etCount.setText(BigDecimalUtils.decimalPoint(s.toString(), 8));
                    etCount.setSelection(etCount.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initCurrentData() {
        onAddressSuccess(mWalletInfo.getAddress());
        getPresenter().createImage(mWalletInfo.getAddress());
    }

    @Override
    public GatheringPresenter injectDependencies() {
        mWalletInfo = (WalletInfo) getIntent().getSerializableExtra("wallet");
        name = mWalletInfo.getCoinName();
        isSpecial = mWalletInfo.isSpecialCoin();
        return new GatheringPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(GatheringActivity.this);
    }

    @Override
    public void onAddressSuccess(String code) {
        tvCode.setText(code);
    }

    @Override
    public void onCreateSuccess(Bitmap bitmap) {
        ivCode.setImageBitmap(bitmap);
    }

    @Override
    public void onImgUploadSuccess(String img) {
        String count = etCount.getText().toString();
        String address = etAddress.getText().toString();
        getPresenter().specialGathering(address, mWalletInfo.getAddress(), count, mWalletInfo.getCoinName(), img);
    }

    @Override
    public void onGatheringSuccess() {
        etCount.setText("");
        etAddress.setText("");
        selectList.clear();
        imageList.clear();
        changeBottomPhotoView();
        scrollView.scrollTo(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1500 && data != null) {
            ArrayList<ImageItem> tempList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (tempList != null && tempList.size() > 0) {
                selectList.clear();
                selectList.addAll(tempList);
            }
            changeBottomPhotoView();
        }
    }

    /**
     * 获取SDcard读写权限
     */
    private void requestContactsPermission() {
        performCodeWithPermission(getString(R.string.permission_app_photo), CommonConst.REQUEST_PERMISSION_STORAGE, CommonConst.PERMISSION_CAMERA_STORAGE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        requestContactsPermission();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        if (perms.size() == CommonConst.PERMISSION_CAMERA_STORAGE.length) {
            selectPhotos();
        }
    }

    /**
     * 选择图片
     */
    private void selectPhotos() {
        // 获取屏幕宽度
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideLoader());      //设置图片加载器
        imagePicker.setShowCamera(true);                    //显示拍照按钮
        imagePicker.setMultiMode(false);                    //多选单选模式
        imagePicker.setCrop(false);                         //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                 //是否按矩形区域保存

        Intent intent = new Intent(GatheringActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, selectList);
        startActivityForResult(intent, 1500);
    }

    private void changeBottomPhotoView() {
        if (selectList.size() > 0) {
            String path = selectList.get(0).getPath();
            imageList.clear();
            imageList.add(path);
            GlideImageUtil.with(mAppContext, path, ivAdd);
            ivAddDel.setVisibility(View.VISIBLE);
        } else {
            ivAdd.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.gongdan_icon_tianjiatupian));
            ivAddDel.setVisibility(View.GONE);
        }
    }

    private void initLiZiPhoto() {
            int language = SpUtil.getLanguage(mAppContext);
            if (language == LanguageEnums.ENGLISH.getKey()) {
                if (StringUtil.isEqual("VDS", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_yingwen));
                } else if (StringUtil.isEqual("DDAM", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
                }
            } else if (language == LanguageEnums.SIMPLIFIED_CHINESE.getKey()) {
                if (StringUtil.isEqual("VDS", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_lizi));
                } else if (StringUtil.isEqual("DDAM", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
                }
            } else if (language == LanguageEnums.TRADITIONAL_CHINESE.getKey()) {
                if (StringUtil.isEqual("VDS", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_fanti));
                } else if (StringUtil.isEqual("DDAM", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
                }
            } else {
                if (StringUtil.isEqual("VDS", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_yingwen));
                } else if (StringUtil.isEqual("DDAM", name)) {
                    ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
                }
            }
    }

    private void checkEdit() {
        String count = etCount.getText().toString();
        String address = etAddress.getText().toString();
        if (StringUtil.isEmpty(count)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.wallet_new_count_tag));
            return;
        }
        if (StringUtil.isEmpty(address)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.wallet_new_address_tag));
            return;
        }
        if (imageList.size() <= 0) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.wallet_new_photo_empty));
            return;
        }
        ArrayList<File> files = new ArrayList<>();
        File file = new File(imageList.get(0));
        if (file.exists() && file.length() > 2 * 1024 *1024L) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.wallet_new_photo_big));
            return;
        }
        files.add(file);
        getPresenter().uploadImage(files);
    }
}
