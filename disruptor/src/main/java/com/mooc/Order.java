package com.mooc;

/**
 * @author maoyi
 * @version V1.0
 * date 2020/7/10 2:58 PM
 * Description: TODO
 */
public class Order {
    private String id;
    private double amount;

    public String getId() {
        return id;
    }

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Order setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
