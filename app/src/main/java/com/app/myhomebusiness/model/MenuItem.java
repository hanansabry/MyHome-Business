package com.app.myhomebusiness.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MenuItem implements Parcelable, Serializable {

    private String id;
    private String name;
    private String desc;
    private String category;
    private double price;
    private String imageUrl;

    public MenuItem() {
    }

    protected MenuItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        desc = in.readString();
        category = in.readString();
        price = in.readDouble();
        imageUrl = in.readString();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeString(imageUrl);
    }
}
