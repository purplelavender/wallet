package share.exchange.framework.http.response;

import java.io.File;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public abstract class IResponseDownloadHandler {

    public abstract void onFinish(File downloadFile);

    public abstract void onProgress(long progress, long total);

    public abstract void onFailed(String errMsg);

    public void onStart(long total) {

    }

    public void onCancel() {

    }
}
