package com.app.myhomebusiness.model;

import android.view.Menu;

import java.util.HashMap;

public class BusinessOrder {

    private String id;
    private String date;
    private String phone;
    private String address;
    private double totalPrice;
    private HashMap<String, OrderItem> orderItems;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HashMap<String, OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(HashMap<String, OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
