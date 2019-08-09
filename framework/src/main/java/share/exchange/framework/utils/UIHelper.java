package share.exchange.framework.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.File;
import java.lang.reflect.Field;

/**
 *
 * @ClassName:      UIHelper
 * @Description:    UI操作工具类
 * @Author:         ZL
 * @CreateDate:     2019/08/02 16:36
 */
public class UIHelper {

    private static final String TAG = "UIHelper";

    private UIHelper() {
    }

    /**
     * 计算ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;
        listView.setLayoutParams(params);
    }

    private static long lastClickTime;

    /**
     * @return
     * @description 判断是否快速点击多次
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * @return
     * @description 判断是否快速点击多次
     */
    public static boolean isFastDoubleClick(long peroid) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < peroid) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 关闭默认局部刷新动画
     *
     * @param recyclerView
     */
    public static void closeDefaultAnimator(RecyclerView recyclerView) {
        try {
            recyclerView.getItemAnimator().setChangeDuration(0);
            recyclerView.getItemAnimator().setRemoveDuration(0);
            recyclerView.getItemAnimator().setMoveDuration(0);
            recyclerView.getItemAnimator().setAddDuration(0);
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        } catch (Exception e) {
        }
    }

    /**
     * 设置带状态的背景
     *
     * @param context
     * @param idNormal
     * @param idPressed
     * @param idFocused
     * @return
     */
    public static StateListDrawable addStateDrawable(Context context, int idNormal, int idPressed, int idFocused) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : ContextCompat.getDrawable(context, idNormal);
        Drawable pressed = idPressed == -1 ? null : ContextCompat.getDrawable(context, idPressed);
        Drawable focus = idFocused == -1 ? null : ContextCompat.getDrawable(context, idFocused);
        //注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        //所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        sd.addState(new int[]{android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_enabled}, normal);
        sd.addState(new int[]{}, normal);
        return sd;
    }

    /**
     * 设置Shape,corners,Gradient
     *
     * @param context
     * @param strokeWidth   边框宽度
     * @param roundRadius   圆角半径
     * @param idStrokeColor 边框颜色id
     * @param idFillColor   内部填充颜色 id
     * @return
     */
    public static GradientDrawable addGradientDrawable(Context context, int strokeWidth, int roundRadius, int idStrokeColor, int idFillColor) {
        int strokeColor = ContextCompat.getColor(context, idStrokeColor);
        int fillColor = ContextCompat.getColor(context, idFillColor);
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    /**
     * 设置Shape,corners,Gradient
     *
     * @param context
     * @param strokeWidth 边框宽度
     * @param roundRadius 圆角半径
     * @param strokeColor 边框颜色id
     * @param fillColor   内部填充颜色 id
     * @return
     */
    public static GradientDrawable addGradientDrawable2(Context context, int strokeWidth, int roundRadius, int strokeColor, int fillColor) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }


    /**
     * 设置点击选中的不同样式的背景
     *
     * @param context              * @param strokeWidth   边框宽度
     * @param roundRadius          圆角半径
     * @param idStrokeColor        选中和按下的边框颜色 id
     * @param idFillColor          选中和按下的内部填充颜色 id
     * @param defalutIdStrokeColor 默认效果的内部填充颜色 id
     * @param defalutIdFillColor   默认效果的边框颜色 id
     * @return
     */
    public static StateListDrawable addStateDrawable(Context context, int strokeWidth, int roundRadius, int idStrokeColor, int idFillColor, int defalutIdStrokeColor, int defalutIdFillColor) {
        GradientDrawable gd = UIHelper.addGradientDrawable(context, strokeWidth, roundRadius, idStrokeColor, idFillColor);
        GradientDrawable gd2 = UIHelper.addGradientDrawable(context, strokeWidth, roundRadius, defalutIdStrokeColor, defalutIdFillColor);
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, gd);
        bg.addState(new int[]{android.R.attr.state_selected}, gd);
        bg.addState(new int[]{}, gd2);
        return bg;
    }

    /**
     * 滚动到顶部
     *
     * @param view
     * @param <T>
     */
    public static <T> void scrollToTop(final T view) {
        if (null != view) {
            if (view instanceof ScrollView) {
                final ScrollView scrollView = (ScrollView) view;
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                    }

                }, 100);
            } else if (view instanceof NestedScrollView) {
                final NestedScrollView scrollView = (NestedScrollView) view;
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(NestedScrollView.FOCUS_UP);
                    }

                }, 100);
            } else if (view instanceof RecyclerView) {
                final RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.smoothScrollToPosition(0);
            } else if (view instanceof ListView) {
                final ListView listView = (ListView) view;
                listView.smoothScrollToPosition(0);
            }
        }
    }

    /**
     * 滚动到底部
     *
     * @param view
     * @param <T>
     */
    public static <T> void scrollToBottom(final T view) {
        if (null != view) {
            if (view instanceof ScrollView) {
                final ScrollView scrollView = (ScrollView) view;
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }

                }, 100);
            } else if (view instanceof NestedScrollView) {
                final NestedScrollView scrollView = (NestedScrollView) view;
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
                    }

                }, 100);
            } else if (view instanceof RecyclerView) {
                final RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            } else if (view instanceof ListView) {
                final ListView listView = (ListView) view;
                listView.smoothScrollToPosition(listView.getAdapter().getCount() - 1);
            }
        }
    }

    /**
     * Web相关Setting
     *
     * @param webView
     */
    public static void initWebSettings(WebView webView) {
        WebSettings s = webView.getSettings();
        s.setBuiltInZoomControls(false);//支持缩放
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容大小
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true); //支持js脚本
        s.setSupportZoom(true); // 允许缩放
        s.setUseWideViewPort(true); // 任意比例缩放
        s.setLoadWithOverviewMode(true);
        s.setAllowFileAccess(true);// 启用或禁止WebView访问文件数据
        s.setNeedInitialFocus(true);// 设置是否可以访问文件
        // enable navigator.geolocation
        s.setGeolocationEnabled(true);
        // enable Web Storage: localStorage, sessionStorage
        s.setDomStorageEnabled(true);

        //提高渲染的优先级
        s.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //使把图片加载放在最后来加载渲染
        s.setBlockNetworkImage(false);
        // 开启H5(APPCache)缓存功能
        s.setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        String packageName = webView.getContext().getPackageName();
        s.setGeolocationDatabasePath("/data/data/" + packageName + "/databases/");

        s.setAppCachePath("/data/data/" + packageName + "/webview/");
        s.setDatabasePath("/data/data/" + packageName + "/webview/");
        if (EnvironmentUtil.isNetworkConnected(webView.getContext())) {
            s.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            s.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

    }


    /**
     * web销毁处理,放在 super.onDestroy(); 之前，避免某些奇葩问题
     */
    public static void destoryWebView(Context context, WebView webView) {
        try {
            if (webView != null) {
                String packageName = webView.getContext().getPackageName();
                releaseAllWebViewCallback();
                webView.removeAllViews();
                // in android 5.1(sdk:21) we should invoke this to avoid memory leak
                ((ViewGroup) webView.getParent()).removeView(webView);
                webView.setTag(null);
                webView.clearHistory();
                webView.clearFormData();
                webView.clearCache(true);
                webView.destroy();

                try {
                    // 清空webview cache缓存的数据
                    String cacheDir = "/data/data/" + packageName + "/webview/";
                    FileUtil.deleteDirectory(new File(cacheDir));

                    context.deleteDatabase("webview.db");
                    context.deleteDatabase("webviewCache.db");
                    context.getCacheDir().delete();
                    //清空所有Cookie
                    CookieSyncManager.createInstance(context);  //Create a singleton CookieSyncManager within a context
                    CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
                    cookieManager.removeAllCookie();// Removes all cookies.
                    CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
                } catch (Exception e) {
                    Log.d(TAG, Log.getStackTraceString(e));
                }

                webView.setWebChromeClient(null);
                webView.setWebViewClient(null);
                webView = null;
            }
        } catch (Exception e) {
            Log.d(TAG, Log.getStackTraceString(e));
        }
    }

    /**
     * destroy方法里调用很大程度上可以避免webview 内存泄露
     */
    public static void releaseAllWebViewCallback() {
        if (Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            } catch (IllegalAccessException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            } catch (ClassNotFoundException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            } catch (IllegalAccessException e) {
                Log.d(TAG, Log.getStackTraceString(e));
            }
        }
    }


}
