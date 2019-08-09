package share.exchange.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;

/**
 *
 * @ClassName:      GlideCatchUtil
 * @Description:    清除glide缓存
 * @Author:         ZL
 * @CreateDate:     2019/08/06 10:25
 */
public class GlideCatchUtil {
    /**
     * 清除图片磁盘缓存，调用Glide自带方法（也可以直接删除指定的文件夹）
     *
     * @param context 最好传 SstTinkerApplicationLike.instance
     * @return
     */
    public static boolean clearCacheDiskSelf(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清除Glide内存缓存
     *
     * @param context 最好传 SstTinkerApplicationLike.instance
     * @return
     */
    public static boolean clearCacheMemory(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void onDestory(Activity activity) {
        //处理glide IllegalArgumentException You cannot start a load for a destroyed activity
        try {
            if (Util.isOnMainThread() && !activity.isFinishing()) {
                Glide.with(activity).pauseRequests();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
