package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wallet.hz.R;
import com.wallet.hz.adapter.MainViewPagerAdapter;
import com.wallet.hz.dialog.VersionUpdateDialog;
import com.wallet.hz.fragment.MarketFragment;
import com.wallet.hz.fragment.MineFragment;
import com.wallet.hz.fragment.ModelFragment;
import com.wallet.hz.fragment.WalletFragment;
import com.wallet.hz.model.UserInfo;
import com.wallet.hz.model.VersionInfo;
import com.wallet.hz.presenter.MainContract;
import com.wallet.hz.presenter.MainPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.constant.CommonConst;
import share.exchange.framework.manager.AppManager;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.HackyViewPager;

public class MainActivity extends BaseAppMVPActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.vp_main)
    HackyViewPager vpMain;
    @BindView(R.id.bnve)
    BottomNavigationViewEx bnve;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int position = 0;
    private String filePath = Environment.getExternalStorageDirectory() + "/Hilbert";
    private String fileName = filePath + "/hilbert.apk";
    private String downUrl = "";

    public static void startIntent(Activity activity, int position) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("position", position);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);
        getPresenter().getUserInfo();
        getPresenter().getCoinBalance();
        initBottomBar();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public MainPresenter injectDependencies() {
        position = getIntent().getIntExtra("position", 0);
        return new MainPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(MainActivity.this);
    }

    /**
     * 底部导航菜单
     */
    private void initBottomBar() {
        fragments.add(WalletFragment.newInstance());
        fragments.add(MarketFragment.newInstance());
        fragments.add(ModelFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(adapter);
        vpMain.setLocked(true);
        vpMain.setOffscreenPageLimit(adapter.getCount());

        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setItemIconTintList(null);
        bnve.setTextVisibility(true);
        bnve.setSmallTextSize(12);
        bnve.setLargeTextSize(13);
        bnve.setIconSize(28f, 28f);
        bnve.setupWithViewPager(vpMain);
        bnve.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.tab_wallet:
                                WalletFragment walletFragment = (WalletFragment) fragments.get(0);
                                if (walletFragment.isAdded()) {
                                    walletFragment.updateData();
                                }
                                break;
                            case R.id.tab_market:
                                break;
                            case R.id.tab_model:
                                ModelFragment modelFragment = (ModelFragment) fragments.get(2);
                                if (modelFragment.isAdded()) {
                                    modelFragment.updateData();
                                }
                                break;
                            case R.id.tab_mine:
                                MineFragment mineFragment = (MineFragment) fragments.get(3);
                                if (mineFragment.isAdded()) {
                                    mineFragment.updateData();
                                }
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
        bnve.setCurrentItem(position);
    }

    @Override
    public void onMineSuccess(UserInfo userInfo) {
        if (userInfo != null) {
            getPresenter().checkVersion();
        } else {
            AppSpUtil.logout(mAppContext);
            AppManager.getAppManager().appExit();
            LoginActivity.startIntent(MainActivity.this);
        }
    }

    @Override
    public void onVersionSuccess(final VersionInfo versionInfo) {
        if (versionInfo != null) {
            String versionCode = EnvironmentUtil.getVersionName(mAppContext);
            String webVersionCode = versionInfo.getVersionCode();
            String inviteUrl = versionInfo.getH5url();
            AppSpUtil.setUserInviteUrl(mAppContext, inviteUrl);
            if (StringUtil.compareVersion(webVersionCode, versionCode) > 0) {
                VersionUpdateDialog versionUpdateDialog = new VersionUpdateDialog(MainActivity.this);
                versionUpdateDialog.build(versionInfo.getContents(), versionInfo.getType() == 1).setOnViewClicked(new VersionUpdateDialog
                        .OnViewClicked() {

                    @Override
                    public void onSure() {
                        downUrl = versionInfo.getAndroidurl();
                        requestContactsPermission();
                    }
                });
                versionUpdateDialog.show();
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
        startActivity(intent);
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
