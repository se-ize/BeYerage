package com.mobileapp.beyerage.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.mobileapp.beyerage.dto.Beverage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BeverageAPIController {

    private Beverage beverage = new Beverage();
    private String tag = "BeverageAPIController";

    /**
     * HTTP API 를 설계하기 위한 Retrofit 객체 생성
     */
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-3-36-89-34.ap-northeast-2.compute.amazonaws.com:3333")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public BeverageAPI getBeverageAPI() {
        return retrofit.create(BeverageAPI.class);
    }

    /**
     * 동기식 HTTP CONNECTION
     * Database에서 사용자가 원하는 음료를 찾아오는 메서드
     * @return Beverage Object
     */
    public Beverage getUserWantBeverage(String beverageName) {

        BeverageAPI beverageAPI = getBeverageAPI();
        beverageAPI.getBeverageData(beverageName).enqueue(new Callback<Beverage>() {
            @Override
            public void onResponse(Call<Beverage> call, Response<Beverage> response) {
                if(response.isSuccessful()){
                    beverage = response.body();
                    if(beverage != null) Log.d("getBeverage name= ", beverage.getName());
                    else Log.d("getBeverage name= ",null);
                } else {
                    Log.d("onResponse: 실패", null);
                }

            }

            @Override
            public void onFailure(Call<Beverage> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return beverage;
    }

    /**
     * 동기식 HTTP CONNECTION
     * Database에서 빈도(Frequency)가 가장 높은 음료를 찾아오는 메서드
     * @return Beverage Object
     */
    public Beverage getMostFreqBeverage(){

        BeverageAPI beverageAPI = getBeverageAPI();
        beverageAPI.getFreqBeverageData().enqueue(new Callback<Beverage>() {
            @Override
            public void onResponse(Call<Beverage> call, Response<Beverage> response) {
                if(response.isSuccessful()){
                    beverage = response.body();
                    if(beverage != null) Log.d("getFreqBeverage name= ", beverage.getName());
                    else Log.d("getFreqBeverage name= ", null);
                } else {
                    Log.d("onResponse: 실패", null);
                }

            }

            @Override
            public void onFailure(Call<Beverage> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return beverage;
    }
}
