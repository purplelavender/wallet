package share.exchange.framework.http.builder;

import android.text.TextUtils;

import java.util.Map;

import okhttp3.Request;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.callback.MyCallback;
import share.exchange.framework.http.response.IResponseHandler;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public class GetBuilder extends BaseHasParamRequestBuilder<GetBuilder> {

    public GetBuilder(OkDroid mOkDroid) {
        super(mOkDroid);
    }

    @Override
    public void enqueue(final IResponseHandler responseHandler) {

        try {
            if (TextUtils.isEmpty(mUrl)) {
                throw new IllegalArgumentException("url can not be null");
            }

            if (mParams != null && mParams.size() > 0) {
                mUrl = appendParams(mUrl, mParams);
            }

            Request.Builder builder = new Request.Builder().url(mUrl).get();
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            Request request = builder.build();
            mOkDroid.getOkHttpClient().newCall(request)
                    .enqueue(new MyCallback(responseHandler));
        } catch (final Exception e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    responseHandler.onFailed(0, e.toString());
                }
            });
        }

    }

    /**
     * 拼接请求参数
     *
     * @param url
     * @param params
     * @return
     */
    private String appendParams(String url, Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        sb.append(url + "?");
        for (String key :
                params.keySet()) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
