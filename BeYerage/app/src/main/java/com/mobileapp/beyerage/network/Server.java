package com.mobileapp.beyerage.network;

import android.util.Log;
import com.mobileapp.beyerage.dto.Beverage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-3-36-89-34.ap-northeast-2.compute.amazonaws.com:3333")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private BeverageAPI getBeverageAPI() {
        return retrofit.create(BeverageAPI.class);
    }

    public void getUserWantBeverage(String beverageName) {

        BeverageAPI beverageAPI = getBeverageAPI();
        beverageAPI.getBeverageData(beverageName).enqueue(new Callback<Beverage>() {
            @Override
            public void onResponse(Call<Beverage> call, Response<Beverage> response) {
                Beverage beverage = response.body();
                Log.d("getBeverage name= ",beverage.getName());
            }

            @Override
            public void onFailure(Call<Beverage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getMostFreqBeverage(){

        BeverageAPI beverageAPI = getBeverageAPI();
        beverageAPI.getFreqBeverageData().enqueue(new Callback<Beverage>() {
            @Override
            public void onResponse(Call<Beverage> call, Response<Beverage> response) {
                Beverage beverage = response.body();
                Log.d("getFreqBeverage name= ", beverage.getName());
            }

            @Override
            public void onFailure(Call<Beverage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
