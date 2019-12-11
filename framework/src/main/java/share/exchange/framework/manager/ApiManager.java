package share.exchange.framework.manager;

import android.util.Log;

import java.io.File;
import java.util.List;
import java.util.Map;

import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.response.IResponseDownloadHandler;
import share.exchange.framework.http.response.IResponseHandler;
import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: ApiManager
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/08/12 09:52
 */
public class ApiManager {

    private static ApiManager instance;

    private ApiManager() {
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new ApiManager();
                }
            }
        }
        return instance;
    }

    /**
     * get请求
     * @param tag     请求TAG
     * @param url     请求地址
     * @param params  接口参数
     * @param responseHandler
     */
    public void get(String tag, String url, Map<String, Object> params, IResponseHandler responseHandler) {
        Log.d(tag, StringUtil.getApiURL(url, params));
        OkDroid.getInstance().get().tag(tag).url(url).params(params).enqueue(responseHandler);
    }

    /**
     * post请求
     * @param tag
     * @param url
     * @param params
     * @param responseHandler
     */
    public void post(String tag, String url, Map<String, Object> params, IResponseHandler responseHandler) {
        Log.d(tag, StringUtil.getApiURL(url, params));
        OkDroid.getInstance().post().tag(tag).url(url).params(params).enqueue(responseHandler);
    }

    /**
     * 下载
     * @param tag
     * @param url
     * @param filePath  文件下载地址
     * @param responseHandler
     */
    public void down(String tag, String url, String filePath, IResponseDownloadHandler responseHandler) {
        OkDroid.getInstance().download().tag(tag).url(url).filePath(filePath).enqueue(responseHandler);
    }

    /**
     * 上传
     * @param tag
     * @param url
     * @param params
     * @param fileList  上传文件列表
     * @param responseHandler
     */
    public void upload(String tag, String url, Map<String, Object> params, List<File> fileList, IResponseHandler responseHandler) {
        OkDroid.getInstance().upload().tag(tag).url(url).params(params).files("files", fileList).enqueue(responseHandler);
    }

    /**
     * 取消某一页面的接口请求
     * @param tag
     */
    public void cancel(String tag) {
        OkDroid.getInstance().cancel(tag);
    }

    /**
     * 取消所有的接口请求
     */
    public void cancelAll(){
        OkDroid.getInstance().cancel("");
    }
}
