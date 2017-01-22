package com.shareads.user.myapplication.model;

/**
 * Created by ericbasendra on 19/12/16.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopSelling {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<TopSellingResult> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TopSellingResult> getResult() {
        return result;
    }

    public void setResult(List<TopSellingResult> result) {
        this.result = result;
    }

}
