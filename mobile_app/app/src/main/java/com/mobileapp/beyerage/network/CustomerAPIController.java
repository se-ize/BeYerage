package com.mobileapp.beyerage.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerAPIController {

    private final String tag = "CustomerAPIController";

    /**
     * HTTP API 를 설계하기 위한 Retrofit 객체 생성
     */
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-3-36-89-34.ap-northeast-2.compute.amazonaws.com:3333")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public CustomerAPI getCustomerAPI() {
        return retrofit.create(CustomerAPI.class);
    }
}
