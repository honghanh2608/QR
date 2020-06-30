package com.example.qrapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryProduct {
    private int quantity;
    @SerializedName("created_at")
    @Expose
    private long createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("properties")
    @Expose
    private String properties;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("mfg")
    @Expose
    private String mfg;
    @SerializedName("exp")
    @Expose
    private String exp;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("price")
    @Expose
    private long price;
    @SerializedName("barcode")
    @Expose
    private String barcode;

    public int getQuantity() {
        return quantity;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProperties() {
        return properties;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getCount() {
        return count;
    }

    public String getMfg() {
        return mfg;
    }

    public String getExp() {
        return exp;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public long getPrice() {
        return price;
    }

    public String getBarcode() {
        return barcode;
    }
}
