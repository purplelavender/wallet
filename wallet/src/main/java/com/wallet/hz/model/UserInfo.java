package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: UserInfo
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/30 09:51
 */
public class UserInfo implements Serializable {

    /**
     * "avatar": "string",
     *   "codes": "string",
     *   "createTime": "2019-11-04T09:18:43.354Z",
     *   "email": "string",
     *   "guges": "string",
     *   "mobile": "string",
     *   "parentCode": "string",
     *   "phone": "string",
     *   "qrcodeurl": "string",
     *   "starts": 0,
     *   "userId": 0,
     *   "username": "string",
     *   "zhujici": "string"
     */

    private String avatar;
    private String codes;
    private String createTime;
    private String email;
    private String guges;
    private String mobile;
    private String parentCodes;
    private String phone;
    private String qrcodeurl;
    private int starts;
    private int userId;
    private String username;
    private String zhujici;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getParentCodes() {
        return parentCodes;
    }

    public void setParentCodes(String parentCodes) {
        this.parentCodes = parentCodes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuges() {
        return guges;
    }

    public void setGuges(String guges) {
        this.guges = guges;
    }

    public int getStarts() {
        return starts;
    }

    public void setStarts(int starts) {
        this.starts = starts;
    }

    public String getZhujici() {
        return zhujici;
    }

    public void setZhujici(String zhujici) {
        this.zhujici = zhujici;
    }
}
