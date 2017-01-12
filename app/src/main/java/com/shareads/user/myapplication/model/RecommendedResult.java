package com.shareads.user.myapplication.model;

/**
 * Created by ericbasendra on 19/12/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecommendedResult {

    @SerializedName("bookuniqueid")
    @Expose
    private String bookuniqueid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discountprice")
    @Expose
    private String discountprice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("frontimage")
    @Expose
    private String frontimage;

    public String getBookuniqueid() {
        return bookuniqueid;
    }

    public void setBookuniqueid(String bookuniqueid) {
        this.bookuniqueid = bookuniqueid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountprice() {
        return discountprice;
    }

    public void setDiscountprice(String discountprice) {
        this.discountprice = discountprice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFrontimage() {
        return frontimage;
    }

    public void setFrontimage(String frontimage) {
        this.frontimage = frontimage;
    }
}
