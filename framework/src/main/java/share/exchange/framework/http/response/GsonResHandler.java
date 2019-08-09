package share.exchange.framework.http.response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.utils.StringUtil;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public abstract class GsonResHandler<T> implements IResponseHandler {

    private Type mType;

    public GsonResHandler() {
        Type superclass = getClass().getGenericSuperclass();//反射获取带泛型的class
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;//获取所有泛型
        //TODO  GSON解析暂时也先不管
//        mType = $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);//将泛型转为type
    }

    private Type getType() {
        return mType;
    }

    @Override
    public final void onSuccess(final Response response) {
        final ResponseBody body = response.body();
        String resBodyStr = "";
        try {
            resBodyStr = body.string();
        } catch (IOException e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(response.code(), "read response body failed");
                }
            });
            return;
        } finally {
            body.close();
        }

        // 解决报文包含empty时解析失败
        if (StringUtil.isContain(resBodyStr, "\"empty\"")) {
            resBodyStr = resBodyStr.replace("\"empty\"", "{}");
        }
        final String finalBodyStr = resBodyStr;
        try {
            // 解决报文不包含status时抛出异常
            if (StringUtil.isContain(finalBodyStr, "<html")
                    || !StringUtil.isContain(finalBodyStr, "status")) {
                OkDroid.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailed(404, "expected begin_object but was string");
                    }
                });
            } else {
                OkDroid.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
//                        T fromJson = (T) new Gson().fromJson(finalBodyStr, getType());
//                        if (fromJson != null) {
//                            onSuccess(response.code(), fromJson);
//                        } else {
//                            onFailed(response.code(), "fail parse gson, body=" + finalBodyStr);
//                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailed(response.code(), "fail parse gson, body=" + finalBodyStr);
                }
            });
        }
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onProgress(long progress, long total) {

    }
}
