package share.exchange.framework.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import share.exchange.framework.executor.AsyncExecutor;
import share.exchange.framework.utils.luban.Luban;

/**
 * @description:鲁班图片压缩算法
 * @author: wragony
 * @createDate: 2016/11/18 15:09
 * @company: 杭州天音
 */
public class ImageCompressUtils {

    private static final String TAG = ImageCompressUtils.class.getName();

    //单位KB
    private static final int MINI_SIZE = 150;

    private final File mCacheDir;

    private AsyncExecutor executor;
    private ArrayList<File> mFiles;

    private int minSize = MINI_SIZE;

    private Context context;

    /**
     * 默认压缩质量为70
     */
    private int quality = 70;

    public static Handler handler = new Handler(Looper.getMainLooper());

    private ImageCompressUtils(Context context, String cacheDir) {
        this.context = context.getApplicationContext();
        mCacheDir = ImageCompressUtils.getPhotoCacheDir(cacheDir);
        executor = new AsyncExecutor();
    }

    public static ImageCompressUtils from(Context context, String cacheDir) {
        return new ImageCompressUtils(context, cacheDir);
    }

    /**
     * Returns a directory with the given name in the private cache directory of the application to use to store
     * retrieved media and thumbnails.
     *
     * @param cacheDir
     */
    public static File getPhotoCacheDir(String cacheDir) {
        File result = new File(cacheDir);
        if (!result.mkdirs()) {
            result.mkdirs();
        }
        return result;
    }

    public ImageCompressUtils load(ArrayList<String> paths) {
        mFiles = new ArrayList<>();
        for (String path : paths) {
            File mFile = new File(path);
            mFiles.add(mFile);
        }
        return this;
    }

    /**
     * 设置压缩质量
     *
     * @param quality 压缩质量，[0,100]
     */
    public ImageCompressUtils setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public ImageCompressUtils setSkipSize(int minSize) {
        this.minSize = minSize;
        return this;
    }

    public void execute(final OnCompressListener listener) {

        if (mFiles == null) {
            throw new NullPointerException("the image file cannot be null, please call .load() before this method!");
        }

        if (listener == null) {
            throw new NullPointerException("the listener must be attached!");
        }

        CompressWorker worker = new CompressWorker(mFiles, listener);
        executor.execute(worker);
    }

    public void cancel() {
        if (executor != null) {
            AsyncExecutor.shutdownNow();
        }
    }

    private class CompressWorker extends AsyncExecutor.Worker<ArrayList<File>> {

        private ArrayList<File> mOrignals;
        private OnCompressListener listener;

        public CompressWorker(ArrayList<File> files, OnCompressListener l) {
            mOrignals = files;
            listener = l;
        }

        @Override
        protected ArrayList<File> doInBackground() {
            ArrayList<File> files = new ArrayList<>();
            try {
                for (File file : mOrignals) {
                    File compressedFile = Luban.with(context)
                            .ignoreBy(minSize)
                            .setFocusAlpha(false)
                            .setQuality(quality)
                            .setTargetDir(mCacheDir.getAbsolutePath())
                            .get(file.getAbsolutePath());

                    if (null != compressedFile && compressedFile.exists()) {
                        files.add(compressedFile);
                    }
                }
            } catch (final Exception ex) {
                Log.e(TAG, Log.getStackTraceString(ex));
                Looper.prepare();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(ex);
                    }
                });
                Looper.loop();
            }
            return files;
        }

        @Override
        protected void onPostExecute(ArrayList<File> data) {
            listener.onSuccess(data);
        }
    }

    public interface OnCompressListener {

        /**
         * Fired when a compression returns successfully, override to handle in your own code
         */
        void onSuccess(ArrayList<File> file);

        /**
         * Fired when a compression fails to complete, override to handle in your own code
         */
        void onError(Throwable e);
    }


}
