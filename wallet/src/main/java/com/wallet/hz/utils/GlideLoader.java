package com.wallet.hz.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wallet.hz.R;

import share.exchange.framework.imagePicker.loader.ImageLoader;

/**
 * Created by MMM on 2018/1/12.
 * 图片选择器图片加载动画
 */
public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path)
                .thumbnail(0.5f)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void displayImage(Context context, String path, ImageView imageView, int width, int height) {
        Glide.with(context).load(path)
                .thumbnail(0.5f)
                .override(width, height)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
