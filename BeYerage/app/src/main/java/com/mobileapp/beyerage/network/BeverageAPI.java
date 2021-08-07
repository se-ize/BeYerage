package com.mobileapp.beyerage.network;

import com.mobileapp.beyerage.dto.Beverage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface BeverageAPI {

    @GET("/beverageWithLocInfo")
    Call<List<Beverage>> getBeverageData(@Query("beverageName") String beverageName);

    @GET("/mostFreqBeverageInfo")
    Call<List<Beverage>> getFreqBeverageData();
}
