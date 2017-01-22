package com.shareads.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ericbasendra on 01/01/17.
 */

public class UserWishList {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<UserWishListResult> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserWishListResult> getResult() {
        return result;
    }

    public void setResult(List<UserWishListResult> result) {
        this.result = result;
    }
}
