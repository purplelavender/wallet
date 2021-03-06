package share.exchange.framework.http.builder;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.response.IResponseHandler;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public abstract class BaseRequestBuilder<T extends BaseRequestBuilder> {

    protected String mUrl;//请求地址
    protected Object mTag;
    protected Map<String, String> mHeaders;//请求头
    protected OkDroid mOkDroid;

    public BaseRequestBuilder(OkDroid mOkDroid) {
        this.mOkDroid = mOkDroid;
    }

    /**
     * 设置url
     *
     * @param url
     * @return
     */
    public T url(String url) {
        this.mUrl = url;
        return (T) this;
    }

    /**
     * 设置tag
     *
     * @param tag
     * @return
     */
    public T tag(Object tag) {
        this.mTag = tag;
        return (T) this;
    }

    /**
     * 添加头
     *
     * @param headers
     * @return
     */
    public T headers(Map<String, String> headers) {
        this.mHeaders = headers;
        return (T) this;
    }

    /**
     * 添加单个头
     *
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, String value) {
        if (this.mHeaders == null) {
            this.mHeaders = new HashMap<>();
        }
        this.mHeaders.put(key, value);
        return (T) this;
    }

    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        if (headers == null) {
            headers = new Hashtable<>();
            headers.put("X-Requested-With", "XMLHttpRequest");
        }
        if (headers == null || headers.isEmpty()) {
            return;
        }
        Headers.Builder headerBuilder = new Headers.Builder();
        for (String key :
                headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    /**
     * 异步请求
     *
     * @param responseHandler
     */
    public abstract void enqueue(final IResponseHandler responseHandler);
}
