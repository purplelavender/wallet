package share.exchange.framework.http.response;

import okhttp3.Response;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public interface IResponseHandler {

    void onSuccess(Response response);

    void onFailed(int statusCode, String errMsg);

    void onProgress(long progress, long total);
}
