package share.exchange.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.util.Util;

import share.exchange.framework.R;

/**
 *
 * @ClassName:      GlideImageUtil
 * @Description:    glide图片加载
 * @Author:         ZL
 * @CreateDate:     2019/08/12 10:17
 */
public class GlideImageUtil {


    /**
     * 通用图片加载（自定义options，不带监听）
     *
     * @param context
     * @param imageUrl  图片路径
     * @param imageView 显示控件
     */
    public static void with(Context context, String imageUrl, ImageView imageView) {
        if (!isFinishing(context) && Util.isOnMainThread()) {
            with(context, imageUrl, imageView, R.drawable.ic_pictures_error);
        }
    }

    /**
     * 通用图片加载（自定义options，带监听）
     *
     * @param context
     * @param imageUrl  图片路径
     * @param imageView 显示控件
     * @param listener  成功失败监听
     */
    public static void with(Context context, String imageUrl, ImageView imageView, RequestListener listener) {
        if (!isFinishing(context) && Util.isOnMainThread()) {
            Glide.with(context)
                    .load(imageUrl)
                    .listener(listener)
                    .into(imageView);
        }
    }

    /**
     * 普通图片加载(默认的options,不带监听)
     * 默认的显示类型，整个图片都放控件中（）
     *
     * @param context
     * @param imageUrl  图片路径
     * @param imageView 显示控件
     */
    public static void with(Context context, String imageUrl, ImageView imageView, int defaultImg) {
        if (!isFinishing(context) && Util.isOnMainThread()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(defaultImg) //加载等待时图片
                    .error(defaultImg) //加载失败时图片
                    .dontAnimate() //防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                    .priority(Priority.HIGH)
                    .into(imageView);
        }
    }

    private static boolean isFinishing(Context context){
        if (context instanceof Activity) {
            return ((Activity) context).isFinishing();
        }
        return false;
    }

}
