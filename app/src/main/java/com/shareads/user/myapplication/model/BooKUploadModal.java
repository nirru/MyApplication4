package com.shareads.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ericbasendra on 29/12/16.
 */

public class BooKUploadModal {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bookid")
    @Expose
    private Integer bookid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

}
