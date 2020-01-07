package share.exchange.framework.http.callback;

import android.app.Activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import share.exchange.framework.R;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.response.IResponseDownloadHandler;
import share.exchange.framework.manager.AppManager;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public class MyDownloadCallback implements Callback {

    private IResponseDownloadHandler mResponseDownloadHandler;
    private String mFilePath;
    private long mCompleteBytes;

    public MyDownloadCallback(IResponseDownloadHandler mResponseDownloadHandler, String mFilePath, long mCompleteBytes) {
        this.mResponseDownloadHandler = mResponseDownloadHandler;
        this.mFilePath = mFilePath;
        this.mCompleteBytes = mCompleteBytes;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            //判断超时异常
            //判断连接异常
            Activity activity = AppManager.getAppManager().currentActivity();
            String errStr = "";
            if (activity != null && !activity.isFinishing()) {
                errStr = activity.getString(R.string.no_network);
            }
            final String finalErrStr = errStr;
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseDownloadHandler.onFailed(finalErrStr);
                }
            });
        } else {
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseDownloadHandler.onFailed(e.toString());
                }
            });
        }
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        ResponseBody body = response.body();
        try {
            if (response.isSuccessful()) {
                OkDroid.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mResponseDownloadHandler != null) {
                            mResponseDownloadHandler.onStart(response.body().contentLength());
                        }
                    }
                });

                try {
                    if (response.header("Content-Range") == null || response.header("Content-Range").length() == 0) {
                        mCompleteBytes = 0;
                    }

                    saveFile(response, mFilePath, mCompleteBytes);

                    final File file = new File(mFilePath);
                    OkDroid.mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mResponseDownloadHandler != null) {
                                mResponseDownloadHandler.onFinish(file);
                            }
                        }
                    });
                } catch (IOException e) {
                    if (call.isCanceled()) {//主动取消
                        OkDroid.mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mResponseDownloadHandler != null) {
                                    mResponseDownloadHandler.onCancel();
                                }
                            }
                        });
                    } else {
                        OkDroid.mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mResponseDownloadHandler != null) {
                                    mResponseDownloadHandler.onCancel();
                                }
                            }
                        });
                    }
                }
            } else {
                OkDroid.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mResponseDownloadHandler != null) {
                            mResponseDownloadHandler.onFailed("failed status " + response.code());
                        }
                    }
                });
            }
        } finally {
            if (body != null) {
                body.close();
            }
        }
    }

    /**
     * 保存文件
     *
     * @param response
     * @param filePath
     * @param completeBytes
     */
    private void saveFile(Response response, String filePath, long completeBytes) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[3 * 1024];//每次读3kb
        int len;
        RandomAccessFile file = null;

        try {
            is = response.body().byteStream();
            file = new RandomAccessFile(filePath, "rwd");
            if (completeBytes > 0) {
                file.seek(completeBytes);
            }
            long completeLen = 0;
            final long totalLen = response.body().contentLength();
            while ((len = is.read(buf)) != -1) {
                file.write(buf, 0, len);
                completeLen += len;
                //写入文件的进度
                final long finalCompleteLen = completeLen;
                OkDroid.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mResponseDownloadHandler != null) {
                            mResponseDownloadHandler.onProgress(finalCompleteLen, totalLen);
                        }
                    }
                });
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
