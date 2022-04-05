package com.app.myhomebusiness.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BusinessOrder implements Parcelable {

    private String id;
    private String date;
    private String phone;
    private String address;
    private double totalPrice;
    private ArrayList<OrderItem> orderItems;
    private String status;

    public BusinessOrder() {
    }

    protected BusinessOrder(Parcel in) {
        id = in.readString();
        date = in.readString();
        phone = in.readString();
        address = in.readString();
        totalPrice = in.readDouble();
        status = in.readString();
    }

    public static final Creator<BusinessOrder> CREATOR = new Creator<BusinessOrder>() {
        @Override
        public BusinessOrder createFromParcel(Parcel in) {
            return new BusinessOrder(in);
        }

        @Override
        public BusinessOrder[] newArray(int size) {
            return new BusinessOrder[size];
        }
    };

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

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(date);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeDouble(totalPrice);
        dest.writeString(status);
    }
}
