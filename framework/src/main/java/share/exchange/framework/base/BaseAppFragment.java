package share.exchange.framework.base;import android.app.Activity;import android.content.Context;import android.graphics.drawable.Drawable;import android.os.Bundle;import android.support.annotation.Nullable;import android.support.v4.app.Fragment;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.FrameLayout;import android.widget.TextView;import java.lang.reflect.Field;import butterknife.ButterKnife;import butterknife.Unbinder;import share.exchange.framework.R;import share.exchange.framework.dialog.LoadingProgressDialog;import share.exchange.framework.utils.EnvironmentUtil;import share.exchange.framework.widget.BaseStateLayout;import share.exchange.framework.widget.CommonToast;/** * @ClassName: BaseAppFragment * @Description: 应用Fragment的基类 * @Author: ZL * @CreateDate: 2019/08/05 14:31 */public abstract class BaseAppFragment extends Fragment {    private LoadingProgressDialog mLodingDialog;    protected final String TAG = this.getClass().getSimpleName();    protected Context mAppContext;    protected Activity mActivity;    protected View mContentView;    private BaseStateLayout mStateView;    private FrameLayout mToolbar;    private TextView tvBaseTitle;    private TextView tvBaseLeft;    private TextView tvBaseRight;    @Nullable    protected Unbinder unbinder;    @Override    public void onDestroyView() {        super.onDestroyView();        try {            if (null != unbinder) {                unbinder.unbind();            }        } catch (Exception e) {            e.printStackTrace();        }    }    @Override    public void onAttach(Activity activity) {        super.onAttach(activity);        initContext();    }    @Override    public void onAttach(Context context) {        super.onAttach(context);        initContext();    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        if (null == mContentView) {            mContentView = inflater.inflate(R.layout.base_fragment, container, false);            mStateView = (BaseStateLayout) mContentView.findViewById(R.id.state_view);            int layout = getLayoutResId();            if (layout != 0) {                mStateView.addStatLayout(BaseStateLayout.STATE_SUCCESS, layout);            }            View view = mStateView.getStateView(BaseStateLayout.STATE_SUCCESS);            unbinder = ButterKnife.bind(this, view);            // 初始化Toolbar标题栏            initToolbar();            // 下级正常页面的处理            initView(savedInstanceState);            initEvent();            processLogic();        }        return mContentView;    }    @Override    public void onDetach() {        super.onDetach();        try {//ChildFragmentManager出现No activity            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");            childFragmentManager.setAccessible(true);            childFragmentManager.set(this, null);        } catch (NoSuchFieldException e) {            throw new RuntimeException(e);        } catch (IllegalAccessException e) {            throw new RuntimeException(e);        }    }    @Override    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {        super.onViewStateRestored(savedInstanceState);        initContext();    }    /**     * 初始化Toolbar相关     *     * @return     */    public void initToolbar() {        mToolbar = (FrameLayout) mContentView.findViewById(R.id.toolbar);        tvBaseTitle = (TextView) mContentView.findViewById(R.id.tv_title);        tvBaseLeft = (TextView) mContentView.findViewById(R.id.tv_left);        tvBaseRight = (TextView) mContentView.findViewById(R.id.tv_right);    }    /**     * 设置Toolbar是否显示     *     * @param isShow     */    public void showTitleBar(Boolean isShow) {        mToolbar.setVisibility(isShow ? View.VISIBLE : View.GONE);    }    /**     * 设置Toolbar标题为文字及监听     *     * @param titleTxt     * @param leftDrawable     * @param rightDrawable     * @param listener     */    public void setToolTitleClick(String titleTxt, Drawable leftDrawable, Drawable rightDrawable, View.OnClickListener listener) {        tvBaseTitle.setText(titleTxt);        if (leftDrawable != null) {            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());        }        if (rightDrawable != null) {            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());        }        tvBaseTitle.setCompoundDrawables(leftDrawable, null, rightDrawable, null);        tvBaseTitle.setCompoundDrawablePadding(EnvironmentUtil.dip2px(mAppContext, 5));        tvBaseTitle.setOnClickListener(listener);    }    /**     * 设置Toolbar左边为文字及监听     *     * @param leftTxt     * @param leftDrawable     * @param rightDrawable     * @param listener     */    public void setToolLeftClick(String leftTxt, Drawable leftDrawable, Drawable rightDrawable, View.OnClickListener listener) {        tvBaseLeft.setText((String) leftTxt);        if (leftDrawable != null) {            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());        }        if (rightDrawable != null) {            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());        }        tvBaseLeft.setCompoundDrawables(leftDrawable, null, rightDrawable, null);        tvBaseLeft.setCompoundDrawablePadding(EnvironmentUtil.dip2px(mAppContext, 5));        tvBaseLeft.setOnClickListener(listener);    }    /**     * 设置Toolbar右边为文字及监听     *     * @param moreTxt     * @param leftDrawable     * @param rightDrawable     * @param listener     */    public void setToolRightClick(String moreTxt, Drawable leftDrawable, Drawable rightDrawable, View.OnClickListener listener) {        tvBaseRight.setText((String) moreTxt);        if (leftDrawable != null) {            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());        }        if (rightDrawable != null) {            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());        }        tvBaseRight.setCompoundDrawables(leftDrawable, null, rightDrawable, null);        tvBaseRight.setCompoundDrawablePadding(EnvironmentUtil.dip2px(mAppContext, 5));        tvBaseRight.setOnClickListener(listener);    }    /**     * 显示加载进行中布局     */    protected void showLoadingStateLayout() {        mStateView.showStateView(BaseStateLayout.STATE_LOADING);    }    /**     * 显示加载成功布局     */    protected void showSuccessStateLayout() {        mStateView.showStateView(BaseStateLayout.STATE_SUCCESS);    }    /**     * 显示加载错误/网络出错布局     */    protected void showErrorStateLayout() {        mStateView.showStateView(BaseStateLayout.STATE_ERROR);        mStateView.getStateView(BaseStateLayout.STATE_ERROR).findViewById(R.id.txt_error)                .setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View view) {                        errorLoadData();                    }                });    }    /**     * 显示数据为空布局     */    protected void showEmptyStateLayout() {        mStateView.showStateView(BaseStateLayout.STATE_EMPTY);    }    /**     * 显示需要登录布局     */    protected void showLoginStateLayout() {        mStateView.showStateView(BaseStateLayout.STATE_LOGIN);        mStateView.getStateView(BaseStateLayout.STATE_LOGIN).findViewById(R.id.txt_login)                .setOnClickListener(new View.OnClickListener() {                    @Override                    public void onClick(View view) {                        goLoginActivity();                    }                });    }    protected boolean isLoginStateLayout() {        return mStateView.getCurrentState() == BaseStateLayout.STATE_LOGIN;    }    protected void goLoginActivity() {    }    protected void errorLoadData() {    }    /**     * 布局文件资源ID     */    protected abstract int getLayoutResId();    /**     * 负责有关handler等对象,界面ui组件的初始化     */    protected abstract void initView(Bundle savedInstanceState);    /**     * 负责有关界面ui组件事件的初始化     */    protected abstract void initEvent();    /**     * 加载需要第一时间请求的网络数据     */    protected abstract void processLogic();    /**     *     */    protected void initContext() {        try {            this.mActivity = getActivity();            this.mAppContext = getActivity().getApplicationContext();        } catch (Exception e) {            e.printStackTrace();        }    }    @Override    public void onPause() {        super.onPause();    }    @Override    public void onResume() {        super.onResume();    }    @Override    public void onDestroy() {        super.onDestroy();    }    /**     * @param toastType 提示类型     * @param message   提示文字     */    public void showToast(CommonToast.ToastType toastType, String message) {        CommonToast.showToast(getActivity(), toastType, message);    }    /**     * @param message     * @param cancelable     * @description 显示加载提示     */    public void showLoading(String message, boolean cancelable) {        if (null != mActivity && !mActivity.isFinishing()) {            if (null == mLodingDialog) {                this.mLodingDialog = new LoadingProgressDialog(mActivity);            }            mLodingDialog.show();            mLodingDialog.setMessage(message);            mLodingDialog.setCancelable(cancelable);        }    }    /**     * @param message     * @description 显示加载提示     */    public void showLoading(String message) {        showLoading(message, true);    }    /**     * @description 隐藏加载提示     */    public void hideLoading() {        if (null != mLodingDialog && !mActivity.isFinishing()) {            mLodingDialog.dismiss();            mLodingDialog = null;        }    }}