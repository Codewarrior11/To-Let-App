package com.infinity.tolet.appel.to_let.findview.models;

public class item {

    int pro_pic;
    String name;
    String type;
    String address;
    String month;
    String postId;
    int price;

    public item(String postId, int pro_pic, String name, String type, String address, String month, int price) {
        this.pro_pic = pro_pic;
        this.name = name;
        this.type = type;
        this.address = address;
        this.month = month;
        this.price = price;
        this.postId=postId;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getPro_pic() {
        return pro_pic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getMonth() {
        return month;
    }

    public int getPrice() {
        return price;
    }

    public void setPro_pic(int pro_pic) {
        this.pro_pic = pro_pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
