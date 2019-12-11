package com.wallet.hz.model;

/**
 * @ClassName: JoinInfo
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/10 12:03
 */
public class JoinInfo {

    /**
     * {"failCount":0,"successCount":0.0010}
     */

    private double failCount;
    private double successCount;

    public double getFailCount() {
        return failCount;
    }

    public void setFailCount(double failCount) {
        this.failCount = failCount;
    }

    public double getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(double successCount) {
        this.successCount = successCount;
    }

    /**
     * 是否需要预约
     * @return
     */
    public boolean isNeedYuYue() {
        return Double.compare(getFailCount(), 0) > 0;
    }
}
