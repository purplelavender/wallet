package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wallet.hz.R;
import com.wallet.hz.model.WorkOrder;
import com.wallet.hz.presenter.WorkOrderAddContract;
import com.wallet.hz.presenter.WorkOrderAddPresenter;
import com.wallet.hz.utils.GlideLoader;
import com.wallet.hz.utils.LoginDialogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.CommonConst;
import share.exchange.framework.imagePicker.ImagePicker;
import share.exchange.framework.imagePicker.bean.ImageItem;
import share.exchange.framework.imagePicker.ui.ImageGridActivity;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.GlideImageUtil;

/**
 * @ClassName: WorkOrderAddActivity
 * @Description: 新建或回复工单
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:35
 */
public class WorkOrderAddActivity extends BaseAppMVPActivity<WorkOrderAddPresenter> implements WorkOrderAddContract.View {

    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.et_order_title)
    EditText etTitle;
    @BindView(R.id.et_order_mail)
    EditText etMail;
    @BindView(R.id.et_order_detail)
    EditText etDetail;
    @BindView(R.id.image_one)
    ImageView ivOne;
    @BindView(R.id.image_two)
    ImageView ivTwo;
    @BindView(R.id.image_three)
    ImageView ivThree;
    @BindView(R.id.image_four)
    ImageView ivFour;
    @BindView(R.id.image_five)
    ImageView ivFive;
    @BindView(R.id.iv_one_del)
    ImageView ivOneDel;
    @BindView(R.id.iv_two_del)
    ImageView ivTwoDel;
    @BindView(R.id.iv_three_del)
    ImageView ivThreeDel;
    @BindView(R.id.iv_four_del)
    ImageView ivFourDel;
    @BindView(R.id.iv_five_del)
    ImageView ivFiveDel;
    @BindView(R.id.ll_one)
    FrameLayout llOne;
    @BindView(R.id.ll_two)
    FrameLayout llTwo;
    @BindView(R.id.ll_three)
    FrameLayout llThree;
    @BindView(R.id.ll_four)
    FrameLayout llFour;
    @BindView(R.id.ll_five)
    FrameLayout llFive;

    private boolean isAdd = true;
    private WorkOrder mWorkOrder;
    private ArrayList<ImageItem> selectList = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<>();

    public static void startIntentResult(Activity activity, WorkOrder workOrder, int requestCode) {
        Intent intent = new Intent(activity, WorkOrderAddActivity.class);
        intent.putExtra("workOrder", workOrder);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_work_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        llTitle.setVisibility(isAdd ? View.VISIBLE : View.GONE);

        calculateImage();
        changeBottomPhotoView();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(isAdd ? getString(R.string.order_new) : getString(R.string.order_reply_order), null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public WorkOrderAddPresenter injectDependencies() {
        mWorkOrder = (WorkOrder) getIntent().getSerializableExtra("workOrder");
        isAdd = mWorkOrder == null;
        return new WorkOrderAddPresenter(this, mAppContext);
    }

    @OnClick({R.id.ll_photo, R.id.tv_order_submit, R.id.iv_one_del, R.id.iv_two_del, R.id.iv_three_del, R.id.iv_four_del, R.id.iv_five_del})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_photo:
                requestContactsPermission();
                break;
            case R.id.tv_order_submit:
                if (imageList.size() > 0) {
                    ArrayList<File> files = new ArrayList<>();
                    for (String path : imageList) {
                        files.add(new File(path));
                    }
                    getPresenter().uploadImg(files);
                } else {
                    onImgUploadSuccess("");
                }
                break;
            case R.id.iv_one_del:
                selectList.remove(0);
                changeBottomPhotoView();
                break;
            case R.id.iv_two_del:
                selectList.remove(1);
                changeBottomPhotoView();
                break;
            case R.id.iv_three_del:
                selectList.remove(2);
                changeBottomPhotoView();
                break;
            case R.id.iv_four_del:
                selectList.remove(3);
                changeBottomPhotoView();
                break;
            case R.id.iv_five_del:
                selectList.remove(4);
                changeBottomPhotoView();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(WorkOrderAddActivity.this);
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
     * 新建或回复工单成功
     */
    @Override
    public void onAddOrEeplySuccess() {
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 图片上传成功后，新建或回复工单
     * @param imgList
     */
    @Override
    public void onImgUploadSuccess(String imgList) {
        String title = etTitle.getText().toString();
        String content = etDetail.getText().toString();
        String mail = etMail.getText().toString();
        if (isAdd) {
            getPresenter().addWorkOrder(title, content, mail, imgList);
        } else {
            getPresenter().replyWorkOrder(mWorkOrder.getId() + "", content, mail, imgList);
        }
    }

    /**
     * 获取SDcard读写权限
     */
    private void requestContactsPermission() {
        performCodeWithPermission(getString(R.string.permission_app_storage), CommonConst.REQUEST_PERMISSION_STORAGE, CommonConst.PERMISSION_STORAGE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        requestContactsPermission();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        if (perms.size() == CommonConst.PERMISSION_STORAGE.length) {
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
        imagePicker.setMultiMode(true);                    //多选单选模式
        imagePicker.setCrop(false);                         //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                 //是否按矩形区域保存
        imagePicker.setSelectLimit(5);                 //选中数量限制

        Intent intent = new Intent(WorkOrderAddActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, selectList);
        startActivityForResult(intent, 1500);
    }

    /**
     * 重新计算图片控件的宽高
     */
    private void calculateImage() {
        int wight = (EnvironmentUtil.screenWidth(mAppContext) - EnvironmentUtil.dip2px(mAppContext, 40)) / 5;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(wight, wight);
        params.setMargins(EnvironmentUtil.dip2px(mAppContext, 5), 0, 0, 0);
        ivOne.setLayoutParams(params);
        ivTwo.setLayoutParams(params);
        ivThree.setLayoutParams(params);
        ivFour.setLayoutParams(params);
        ivFive.setLayoutParams(params);
    }

    /**
     * 根据选中的图片来更改底部图片控件的显示状况
     */
    private void changeBottomPhotoView() {
        imageList.clear();
        for (ImageItem localMedia : selectList) {
            imageList.add(localMedia.getPath());
        }
        int size = imageList.size();
        switch (size) {
            case 1:
                GlideImageUtil.with(mAppContext, imageList.get(0), ivOne);
                ivOne.setVisibility(View.VISIBLE);
                ivTwo.setImageResource(R.drawable.gongdan_icon_tianjiatupian);
                ivTwo.setVisibility(View.VISIBLE);
                ivThree.setVisibility(View.INVISIBLE);
                ivFour.setVisibility(View.INVISIBLE);
                ivFive.setVisibility(View.INVISIBLE);

                ivOneDel.setVisibility(View.VISIBLE);
                ivTwoDel.setVisibility(View.GONE);
                ivThreeDel.setVisibility(View.GONE);
                ivFourDel.setVisibility(View.GONE);
                ivFiveDel.setVisibility(View.GONE);
                break;
            case 2:
                GlideImageUtil.with(mAppContext, imageList.get(0), ivOne);
                ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(1), ivTwo);
                ivTwo.setVisibility(View.VISIBLE);
                ivThree.setImageResource(R.drawable.gongdan_icon_tianjiatupian);
                ivThree.setVisibility(View.VISIBLE);
                ivFour.setVisibility(View.INVISIBLE);
                ivFive.setVisibility(View.INVISIBLE);

                ivOneDel.setVisibility(View.VISIBLE);
                ivTwoDel.setVisibility(View.VISIBLE);
                ivThreeDel.setVisibility(View.GONE);
                ivFourDel.setVisibility(View.GONE);
                ivFiveDel.setVisibility(View.GONE);
                break;
            case 3:
                GlideImageUtil.with(mAppContext, imageList.get(0), ivOne);
                ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(1), ivTwo);
                ivTwo.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(2), ivThree);
                ivThree.setVisibility(View.VISIBLE);
                ivFour.setImageResource(R.drawable.gongdan_icon_tianjiatupian);
                ivFour.setVisibility(View.VISIBLE);
                ivFive.setVisibility(View.INVISIBLE);

                ivOneDel.setVisibility(View.VISIBLE);
                ivTwoDel.setVisibility(View.VISIBLE);
                ivThreeDel.setVisibility(View.VISIBLE);
                ivFourDel.setVisibility(View.GONE);
                ivFiveDel.setVisibility(View.GONE);
                break;
            case 4:
                GlideImageUtil.with(mAppContext, imageList.get(0), ivOne);
                ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(1), ivTwo);
                ivTwo.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(2), ivThree);
                ivThree.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(3), ivFour);
                ivFour.setVisibility(View.VISIBLE);
                ivFive.setImageResource(R.drawable.gongdan_icon_tianjiatupian);
                ivFive.setVisibility(View.VISIBLE);

                ivOneDel.setVisibility(View.VISIBLE);
                ivTwoDel.setVisibility(View.VISIBLE);
                ivThreeDel.setVisibility(View.VISIBLE);
                ivFourDel.setVisibility(View.VISIBLE);
                ivFiveDel.setVisibility(View.GONE);
                break;
            case 5:
                GlideImageUtil.with(mAppContext, imageList.get(0), ivOne);
                ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(1), ivTwo);
                ivTwo.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(2), ivThree);
                ivThree.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(3), ivFour);
                ivFour.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mAppContext, imageList.get(4), ivFive);
                ivFive.setVisibility(View.VISIBLE);

                ivOneDel.setVisibility(View.VISIBLE);
                ivTwoDel.setVisibility(View.VISIBLE);
                ivThreeDel.setVisibility(View.VISIBLE);
                ivFourDel.setVisibility(View.VISIBLE);
                ivFiveDel.setVisibility(View.VISIBLE);
                break;
            case 0:
            default:
                ivOne.setImageResource(R.drawable.gongdan_icon_tianjiatupian);
                ivOne.setVisibility(View.VISIBLE);
                ivTwo.setVisibility(View.INVISIBLE);
                ivThree.setVisibility(View.INVISIBLE);
                ivFour.setVisibility(View.INVISIBLE);
                ivFive.setVisibility(View.INVISIBLE);

                ivOneDel.setVisibility(View.GONE);
                ivTwoDel.setVisibility(View.GONE);
                ivThreeDel.setVisibility(View.GONE);
                ivFourDel.setVisibility(View.GONE);
                ivFiveDel.setVisibility(View.GONE);
                break;
        }
    }
}
