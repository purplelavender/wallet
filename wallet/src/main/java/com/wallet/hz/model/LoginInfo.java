package com.wallet.hz.model;

/**
 * @ClassName: LoginInfo
 * @Description: 登录接口返回信息
 * @Author: ZL
 * @CreateDate: 2019/11/06 11:12
 */
public class LoginInfo {

    /**
     * {
     *   "msg": "请求成功",
     *   "code": 0,
     *   "expire": 43199992,
     *   "token": "a272be1446a747c8845ad3ea39ae12c3"
     * }
     */

    private String msg;
    private int code;
    private String expire;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
