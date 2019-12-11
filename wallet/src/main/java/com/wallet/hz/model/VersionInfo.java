package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: VersionInfo
 * @Description: 版本更新接口返回信息
 * @Author: ZL
 * @CreateDate: 2019/11/08 02:59
 */
public class VersionInfo implements Serializable {

    /**
     *     {
     *     "androidurl":"http://jiankanglian.oss-cn-beijing.aliyuncs.com/Hilbert_Space.apk",
     *     "contents":"app上线",
     *     "createTime":"2019-11-08 01:46:06",
     *     "h5url":"http://download.hilbertspace.vip/#/resign",
     *     "id":1,
     *     "iosurl":"http://jiankanglian.oss-cn-beijing.aliyuncs.com/Hilbert_Space.ipa",
     *     "type":1,
     *     "urls":"11",
     *     "versionCode":"1.0.0",
     *     "versionName":"1.0app"
     * }
     */

    private int id;
    private String versionCode;
    private String versionName;
    private String urls;
    private String createTime;
    private String contents;
    private int type;
    private String androidurl;
    private String iosurl;
    private String h5url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAndroidurl() {
        return androidurl;
    }

    public void setAndroidurl(String androidurl) {
        this.androidurl = androidurl;
    }

    public String getIosurl() {
        return iosurl;
    }

    public void setIosurl(String iosurl) {
        this.iosurl = iosurl;
    }

    public String getH5url() {
        return h5url;
    }

    public void setH5url(String h5url) {
        this.h5url = h5url;
    }
}
