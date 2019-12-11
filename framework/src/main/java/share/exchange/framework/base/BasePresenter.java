package share.exchange.framework.base;

import java.lang.ref.WeakReference;

import share.exchange.framework.executor.AsyncExecutor;
import share.exchange.framework.manager.ApiManager;

/**
 *
 * @ClassName:      BasePresenter
 * @Description:    java类作用描述
 * @Author:         ZL
 * @CreateDate:     2019/08/02 16:33
 */
public abstract class BasePresenter<V extends BaseMVP.MvpView> implements BaseMVP.MvpPresenter {

    protected final String TAG = getClass().getSimpleName();

    protected V mView;
    protected WeakReference<V> mViewRef; // view 的弱引用 当内存不足释放内存

    protected AsyncExecutor executor;
    protected ApiManager apiManager;
    protected int page = 1;

    public BasePresenter() {
        executor = new AsyncExecutor();
    }

    public BasePresenter(V view) {
        this.mView = view;
        mViewRef = new WeakReference<>(view);
        if (getView() == null) {
            throw new IllegalArgumentException("View cannot be null");
        }
        executor = new AsyncExecutor();
        apiManager = ApiManager.getInstance();
    }

    @Override
    public void destroyView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        apiManager.cancel(TAG);
    }

    public V getView() {
        if (null != mViewRef) {
            return mViewRef.get();
        } else {
            return mView;
        }
    }

}