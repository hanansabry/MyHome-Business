package com.app.myhomebusiness.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {

    private MenuItem item;
    private int quantity;
    private double totalPrice;

    public OrderItem() {
    }


    protected OrderItem(Parcel in) {
        item = in.readParcelable(MenuItem.class.getClassLoader());
        quantity = in.readInt();
        totalPrice = in.readDouble();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(item, flags);
        dest.writeInt(quantity);
        dest.writeDouble(totalPrice);
    }
}
