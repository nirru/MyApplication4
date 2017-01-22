package com.shareads.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ericbasendra on 24/12/16.
 */

public class BookReviewModal {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<BookReviewResult> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BookReviewResult> getResult() {
        return result;
    }

    public void setResult(List<BookReviewResult> result) {
        this.result = result;
    }
}
