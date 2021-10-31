package com.mobileapp.beyerage.dto;

import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("id")
    private Long id;
    @SerializedName("text")
    private String text;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
