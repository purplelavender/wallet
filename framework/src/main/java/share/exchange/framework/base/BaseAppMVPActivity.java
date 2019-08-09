/** * */package share.exchange.framework.base;import android.os.Bundle;/** * * @ClassName:      BaseAppMVPActivity * @Description:    应用Activity的基类 * @Author:         ZL * @CreateDate:     2019/08/05 14:31 */public abstract class BaseAppMVPActivity<P extends BaseMVP.MvpPresenter> extends BaseAppActivity implements BaseMVP.MvpView<P> {    private P mPresenter;    @Override    protected void initView(Bundle savedInstanceState) {        super.initView(savedInstanceState);        mPresenter = injectDependencies();        if (getPresenter() == null) {            throw new IllegalArgumentException("You must inject the dependencies before retrieving the presenter");        }        mPresenter.createView();    }    @Override    protected void onDestroy() {        super.onDestroy();        if (null != mPresenter) {            mPresenter.destroyView();        }    }    @Override    public P getPresenter() {        return mPresenter;    }}