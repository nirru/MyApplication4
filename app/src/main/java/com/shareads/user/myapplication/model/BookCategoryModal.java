package com.shareads.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ericbasendra on 02/01/17.
 */

public class BookCategoryModal {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<BookCategoryResult> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BookCategoryResult> getResult() {
        return result;
    }

    public void setResult(List<BookCategoryResult> result) {
        this.result = result;
    }
}
