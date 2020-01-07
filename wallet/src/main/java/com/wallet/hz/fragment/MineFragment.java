package com.wallet.hz.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.activity.FinancialDetailActivity;
import com.wallet.hz.activity.InviteShareActivity;
import com.wallet.hz.activity.LanguageChangeActivity;
import com.wallet.hz.activity.LoginActivity;
import com.wallet.hz.activity.MyGroupActivity;
import com.wallet.hz.activity.PasswordManagerActivity;
import com.wallet.hz.activity.ProjectApplyActivity;
import com.wallet.hz.activity.SafeBindActivity;
import com.wallet.hz.activity.WorkOrderAddActivity;
import com.wallet.hz.dialog.VersionUpdateDialog;
import com.wallet.hz.model.UserInfo;
import com.wallet.hz.model.VersionInfo;
import com.wallet.hz.presenter.MainContract;
import com.wallet.hz.presenter.MainPresenter;
import com.wallet.hz.utils.AppSpUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.constant.CommonConst;
import share.exchange.framework.manager.AppManager;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.AutoSizeTextView;
import share.exchange.framework.widget.CircleImageView;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: MineFragment
 * @Description: 我的
 * @Author: ZL
 * @CreateDate: 2019/10/21 14:30
 */
public class MineFragment extends BaseAppMVPFragment<MainPresenter> implements MainContract.View {

    @BindView(R.id.civ_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    AutoSizeTextView tvName;
    @BindView(R.id.tv_user_id)
    AutoSizeTextView tvId;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

    private String filePath = Environment.getExternalStorageDirectory() + "/Hilbert";
    private String fileName = filePath + "/hilbert.apk";
    private String downUrl = "";

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);
        String versionCode = EnvironmentUtil.getVersionName(mAppContext);
        tvVersionName.setText(versionCode);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void processLogic() {
        getPresenter().getUserInfo();
    }

    @Override
    public MainPresenter injectDependencies() {
        return new MainPresenter(this, mAppContext);
    }

    @OnClick({R.id.ll_mine_group, R.id.ll_mine_invite, R.id.ll_mine_language, R.id.tv_mine_out, R.id.ll_mine_password, R.id.ll_mine_project, R.id
            .ll_mine_safe, R.id.ll_mine_version, R.id.ll_mine_work, R.id.ll_mine_money})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_mine_money:
                FinancialDetailActivity.startIntent(getActivity());
                break;
            case R.id.ll_mine_group:
                MyGroupActivity.startIntent(getActivity());
                break;
            case R.id.ll_mine_invite:
                InviteShareActivity.startIntent(getActivity());
                break;
            case R.id.ll_mine_language:
                LanguageChangeActivity.startIntent(getActivity());
                break;
            case R.id.ll_mine_password:
                PasswordManagerActivity.startIntent(getActivity());
                break;
            case R.id.ll_mine_version:
                getPresenter().checkVersion();
                break;
            case R.id.ll_mine_project:
                ProjectApplyActivity.startIntent(getActivity());
                break;
            case R.id.ll_mine_safe:
                SafeBindActivity.startIntent(getActivity());
                break;
            case R.id.tv_mine_out:
                AppSpUtil.logout(mAppContext);
                AppManager.getAppManager().appExit();
                LoginActivity.startIntent(getActivity());
                break;
            case R.id.ll_mine_work:
                WorkOrderAddActivity.startIntentResult(getActivity(), null, 155);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMineSuccess(UserInfo userInfo) {
        if (userInfo != null) {
            tvName.setText(userInfo.getUsername());
            tvId.setText(getString(R.string.mine_uid, userInfo.getCodes()));
        }
    }

    @Override
    public void onVersionSuccess(final VersionInfo versionInfo) {
        if (versionInfo != null) {
            String versionCode = EnvironmentUtil.getVersionName(mAppContext);
            String webVersionCode = versionInfo.getVersionCode();
            if (StringUtil.compareVersion(webVersionCode, versionCode) > 0) {
                VersionUpdateDialog versionUpdateDialog = new VersionUpdateDialog(getActivity());
                versionUpdateDialog.build(versionInfo.getContents(), versionInfo.getType() == 1).setOnViewClicked(new VersionUpdateDialog.OnViewClicked() {

                    @Override
                    public void onSure() {
                        downUrl = versionInfo.getAndroidurl();
                        requestContactsPermission();
                    }
                });
                versionUpdateDialog.show();
            } else {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_version_new));
            }
        }
    }

    @Override
    public void installNewApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(mAppContext, "com.hilbert.hz.provider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        //查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置setDataAndType的方法之后
        getActivity().startActivity(intent);
    }

    public void updateData(){
        processLogic();
    }

    /**
     * 获取SDcard读写权限
     */
    private void requestContactsPermission() {
        performCodeWithPermission(getString(R.string.permission_app_storage), CommonConst.REQUEST_PERMISSION_STORAGE, CommonConst
                .PERMISSION_STORAGE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        requestContactsPermission();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        if (perms.size() == CommonConst.PERMISSION_STORAGE.length) {
            getPresenter().downApk(downUrl, fileName);
        }
    }
}
