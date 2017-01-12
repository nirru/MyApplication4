package com.shareads.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/3/2016.
 */

    public class MyBookListModel {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("result")
        @Expose
        private List<Result> result = new ArrayList<Result>();

        /**
         *
         * @return
         * The message
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         * The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         *
         * @return
         * The result
         */
        public List<Result> getResult() {
            return result;
        }

        /**
         *
         * @param result
         * The result
         */
        public void setResult(List<Result> result) {
            this.result = result;
        }



    public class Result {

        @SerializedName("bookuniqueid")
        @Expose
        private String bookuniqueid;
        @SerializedName("userid")
        @Expose
        private String userid;
        @SerializedName("categoryid")
        @Expose
        private String categoryid;
        @SerializedName("title")
        @Expose
        private String title;
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
        @SerializedName("discountprice")
        @Expose
        private String discountprice;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("frontimage")
        @Expose
        private String frontimage;
        @SerializedName("backimage")
        @Expose
        private String backimage;
        @SerializedName("bookstatus")
        @Expose
        private String bookstatus;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("reload_new")
        @Expose
        private String reloadNew;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("publishingyear")
        @Expose
        private String publishingyear;
        @SerializedName("termsandconditions")
        @Expose
        private String termsandconditions;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("dateposted")
        @Expose
        private String dateposted;

        public String getBookuniqueid() {
            return bookuniqueid;
        }

        public void setBookuniqueid(String bookuniqueid) {
            this.bookuniqueid = bookuniqueid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getBackimage() {
            return backimage;
        }

        public void setBackimage(String backimage) {
            this.backimage = backimage;
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

        public String getReloadNew() {
            return reloadNew;
        }

        public void setReloadNew(String reloadNew) {
            this.reloadNew = reloadNew;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getDateposted() {
            return dateposted;
        }

        public void setDateposted(String dateposted) {
            this.dateposted = dateposted;
        }

    }


}


