package share.exchange.framework.base;

import share.exchange.framework.widget.CommonToast;

/**
 *
 * @ClassName:      BaseMVP
 * @Description:    java类作用描述
 * @Author:         ZL
 * @CreateDate:     2019/08/02 16:33
 */
public interface BaseMVP {

    /**
     * Main contract between the Android classes
     */
    interface IView<P extends MvpPresenter> {

        /* build up your presenter with all its necessary dependencies in here */
        P injectDependencies();
    }

    interface MvpView<P extends MvpPresenter> extends IView<P> {

        void showLoading(String message);

        void showLoading(String message, boolean cancelable);

        void hideLoading();

        void showLoginDialog();

        void showToast(CommonToast.ToastType toastType, String message);

        boolean isFragmentAdd();

        /* getter to the view's presenter */
        P getPresenter();
    }

    interface MvpPresenter {

        void createView();

        void destroyView();
    }

}
