package com.wallet.hz.model;

import java.io.Serializable;

/**
 * @ClassName: PriceRate
 * @Description: 汇率
 * @Author: ZL
 * @CreateDate: 2019/11/04 09:57
 */
public class PriceRate implements Serializable {

    /**
     * "id": 0,
     *   "name": "string",
     *   "price": 0
     */

    private int id;
    private String name;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
