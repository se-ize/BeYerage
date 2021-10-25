package com.mobileapp.beyerage.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CustomerAPI {

    @POST("/addCustomerVoice")
    Call<String> addCustomerVoice(@Body String text);

}
