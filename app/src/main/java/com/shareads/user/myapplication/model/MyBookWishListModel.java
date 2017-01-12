package com.shareads.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/3/2016.
 */

public class MyBookWishListModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<Result>();

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The result
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("wishlistid")
        @Expose
        private String wishlistid;
        @SerializedName("bookid")
        @Expose
        private String bookid;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("booktitle")
        @Expose
        private String booktitle;
        @SerializedName("categoryid")
        @Expose
        private String categoryid;
        @SerializedName("categoryName")
        @Expose
        private String categoryName;
        @SerializedName("isbnnumber")
        @Expose
        private String isbnnumber;
        @SerializedName("author")
        @Expose
        private String author;
        @SerializedName("publication")
        @Expose
        private String publication;
        @SerializedName("noofpages")
        @Expose
        private String noofpages;
        @SerializedName("edition")
        @Expose
        private String edition;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("binding")
        @Expose
        private String binding;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("frontimage")
        @Expose
        private String frontimage;
        @SerializedName("backimage")
        @Expose
        private String backimage;
        @SerializedName("contentimage")
        @Expose
        private String contentimage;
        @SerializedName("spineimage")
        @Expose
        private String spineimage;
        @SerializedName("bookstatus")
        @Expose
        private String bookstatus;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("publishingyear")
        @Expose
        private String publishingyear;
        @SerializedName("termsandconditions")
        @Expose
        private String termsandconditions;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("status")
        @Expose
        private String status;

        public String getWishlistid() {
            return wishlistid;
        }

        public void setWishlistid(String wishlistid) {
            this.wishlistid = wishlistid;
        }

        public String getBookid() {
            return bookid;
        }

        public void setBookid(String bookid) {
            this.bookid = bookid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getBooktitle() {
            return booktitle;
        }

        public void setBooktitle(String booktitle) {
            this.booktitle = booktitle;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getIsbnnumber() {
            return isbnnumber;
        }

        public void setIsbnnumber(String isbnnumber) {
            this.isbnnumber = isbnnumber;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublication() {
            return publication;
        }

        public void setPublication(String publication) {
            this.publication = publication;
        }

        public String getNoofpages() {
            return noofpages;
        }

        public void setNoofpages(String noofpages) {
            this.noofpages = noofpages;
        }

        public String getEdition() {
            return edition;
        }

        public void setEdition(String edition) {
            this.edition = edition;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getBinding() {
            return binding;
        }

        public void setBinding(String binding) {
            this.binding = binding;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getBackimage() {
            return backimage;
        }

        public void setBackimage(String backimage) {
            this.backimage = backimage;
        }

        public String getContentimage() {
            return contentimage;
        }

        public void setContentimage(String contentimage) {
            this.contentimage = contentimage;
        }

        public String getSpineimage() {
            return spineimage;
        }

        public void setSpineimage(String spineimage) {
            this.spineimage = spineimage;
        }

        public String getBookstatus() {
            return bookstatus;
        }

        public void setBookstatus(String bookstatus) {
            this.bookstatus = bookstatus;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPublishingyear() {
            return publishingyear;
        }

        public void setPublishingyear(String publishingyear) {
            this.publishingyear = publishingyear;
        }

        public String getTermsandconditions() {
            return termsandconditions;
        }

        public void setTermsandconditions(String termsandconditions) {
            this.termsandconditions = termsandconditions;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


    }
}