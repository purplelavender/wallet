package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.wallet.hz.R;
import com.wallet.hz.presenter.AddressAddContract;
import com.wallet.hz.presenter.AddressAddPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.CommonConst;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: AddressAddActivity
 * @Description: 添加转账地址
 * @Author: ZL
 * @CreateDate: 2019/10/24 16:41
 */
public class AddressAddActivity extends BaseAppMVPActivity<AddressAddPresenter> implements AddressAddContract.View {

    @BindView(R.id.et_add_address)
    EditText etAddress;
    @BindView(R.id.et_add_mark)
    EditText etMark;

    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, AddressAddActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public AddressAddPresenter injectDependencies() {
        return new AddressAddPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 122) {
            String code = data.getStringExtra("code");
            etAddress.setText(code);
        }
    }

    @OnClick({R.id.iv_add_sao, R.id.tv_add_save})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_add_sao:
                requestCamera();
                break;
            case R.id.tv_add_save:
                addCheck();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(AddressAddActivity.this);
    }

    /**
     * 验证输入框输入
     */
    private void addCheck() {
        String address = etAddress.getText().toString();
        String mark = etMark.getText().toString();
        if (StringUtil.isEmpty(address)) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_address_empty));
            return;
        }
        getPresenter().addCoinAddress(address, mark);
    }

    private void requestCamera(){
        performCodeWithPermission(getString(R.string.permission_app_camera), CommonConst.REQUEST_PERMISSION_CAMERA, CommonConst.PERMISSION_CAMERA_STORAGE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        requestCamera();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        QRCodeActivity.startIntentResult(AddressAddActivity.this, 122);
    }
}
