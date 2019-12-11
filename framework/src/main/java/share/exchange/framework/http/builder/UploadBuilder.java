package share.exchange.framework.http.builder;

import android.text.TextUtils;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import share.exchange.framework.http.OkDroid;
import share.exchange.framework.http.body.ReqProgressBody;
import share.exchange.framework.http.callback.MyCallback;
import share.exchange.framework.http.response.IResponseHandler;
import share.exchange.framework.utils.StringUtil;

/**
 * Created by MMM on 2018/1/10.
 * e_mail：xiaoexiao51@163.com
 */
public class UploadBuilder extends BaseHasParamRequestBuilder<UploadBuilder> {

    private Map<String, File> mFiles;
    private String mFileKey;
    private List<File> mFileLists;
    private List<MultipartBody.Part> mExtraParts;

    public UploadBuilder(OkDroid mOkDroid) {
        super(mOkDroid);
    }

    public UploadBuilder files(Map<String, File> files) {
        this.mFiles = files;
        return this;
    }

    public UploadBuilder files(String key, List<File> files) {
        this.mFileKey = key;
        this.mFileLists = files;
        return this;
    }

    public UploadBuilder addFile(String key, File file) {
        if (this.mFiles == null) {
            mFiles = new HashMap<>();
        }
        mFiles.put(key, file);
        return this;
    }

    public UploadBuilder addFile(String key, String fileName, byte[] fileContent) {
        if (this.mExtraParts == null) {
            this.mExtraParts = new ArrayList<>();
        }
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), fileContent);
        this.mExtraParts.add(MultipartBody.Part.create(Headers.of("Content-Disposition",
                "form-data; name=\"" + key + "\"; filename=\"" + fileName + "\""),
                fileBody));
        return this;
    }

    @Override
    public void enqueue(final IResponseHandler responseHandler) {
        try {
            if (TextUtils.isEmpty(mUrl)) {
                throw new IllegalArgumentException("url can not be null");
            }
            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);
            if (mTag != null) {
                builder.tag(mTag);
            }

            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            appendParams(multipartBuilder, mParams);// add params
            if (StringUtil.isEmpty(mFileKey)) {
                appendFiles(multipartBuilder, mFiles);
            } else {
                appendFiles(multipartBuilder, mFileLists);
            }
            appendParts(multipartBuilder, mExtraParts);

            builder.post(new ReqProgressBody(multipartBuilder.build(), responseHandler));
            Request request = builder.build();
            mOkDroid.getOkHttpClient().newCall(request).enqueue(new MyCallback(responseHandler));
        } catch (final Exception e) {
            e.printStackTrace();
            OkDroid.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    responseHandler.onFailed(0, e.getMessage());
                }
            });
        }
    }

    private void appendParts(MultipartBody.Builder builder, List<MultipartBody.Part> parts) {
        if (parts != null && parts.size() > 0) {
            for (int i = 0, size = parts.size(); i < size; i++) {
                builder.addPart(parts.get(i));
            }
        }
    }

    private void appendFiles(MultipartBody.Builder builder, Map<String, File> files) {
        if (files != null && !files.isEmpty()) {
            RequestBody fileBody;
            File file = null;
            String fileName = null;
            for (String key : files.keySet()) {
                file = files.get(key);
                if (file != null) {
                    fileName = file.getName();
                    fileBody = RequestBody.create(MediaType.parse(getMimeType(fileName)), file);
                    builder.addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"" + key + "\"; filename=\"" + fileName + "\""),
                            fileBody);
                }
            }
        }
    }

    private void appendFiles(MultipartBody.Builder builder, List<File> files) {
        if (files != null && files.size() != 0) {
            RequestBody fileBody;
            String fileName = null;
            for (File file : files) {
                if (file != null && file.exists()) {
                    fileName = file.getName();
                    fileBody = RequestBody.create(MediaType.parse(getMimeType(fileName)), file);
                    builder.addFormDataPart(mFileKey, fileName, fileBody);
                }
            }
        }
    }

    private String getMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    private void appendParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key :
                    params.keySet()) {
                if (params.get(key) != null) {
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                            RequestBody.create(null, params.get(key).toString()));
                }
            }
        }
    }
}
