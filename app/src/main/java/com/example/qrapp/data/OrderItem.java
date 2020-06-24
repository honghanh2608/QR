package com.example.qrapp.data;

public class OrderItem{
    private String barcode;
    private int quantity;
    private String name;
    private long price;

    public OrderItem(){

    }

    public OrderItem(String barcode, int quantity, String name, long price) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
