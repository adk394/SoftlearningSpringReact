package com.example.core.entities.order.model;

import com.example.shared.exceptions.BuildException;

public class OrderDetail {

    private String ref;
    private double price, discount;
    private int amount;

    public OrderDetail(String ref, double price, double discount, int amount) {
        this.ref = ref;
        this.price = price;
        this.discount = discount;
        this.amount = amount;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) throws BuildException {
        if (amount < 0) {
            throw new BuildException("amount cannot be < 0");
        }
        this.amount = amount;
    }

    public double getDetailCost() {
        return (this.price - this.discount) * this.amount;
    }

    public String getDetail() {
        return this.getRef() + "," + this.getPrice() + "," + this.getDiscount() + "," + this.getAmount();
    }

}
