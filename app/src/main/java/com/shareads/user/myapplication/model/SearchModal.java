package com.shareads.user.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ericbasendra on 29/12/16.
 */

public class SearchModal {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private List<SearchResult> results = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }

}
