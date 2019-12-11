package com.wallet.hz.model;

/**
 * @ClassName: AwardDestruction
 * @Description: 奖金池、销毁池对象
 * @Author: ZL
 * @CreateDate: 2019/11/10 11:18
 */
public class AwardDestruction {

    /**
     * "bonusMoney": 0,
     *   "destroyMoney": 0,
     *   "id": 0
     */

    private double bonusMoney;
    private double destroyMoney;
    private int id;

    public double getBonusMoney() {
        return bonusMoney;
    }

    public void setBonusMoney(double bonusMoney) {
        this.bonusMoney = bonusMoney;
    }

    public double getDestroyMoney() {
        return destroyMoney;
    }

    public void setDestroyMoney(double destroyMoney) {
        this.destroyMoney = destroyMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
