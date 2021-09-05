package com.mobileapp.beyerage.network;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mobileapp.beyerage.dto.kakaoObject.Place;
import com.mobileapp.beyerage.dto.kakaoObject.ResultSearchKeyword;
import com.mobileapp.beyerage.shop.ShopService;
import com.mobileapp.beyerage.shop.ShopServiceImpl;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Collections;
import java.util.List;

public class KakaoAPIController {

    private ShopService shopService = new ShopServiceImpl();
    /**
     * HTTP API 를 설계하기 위한 Retrofit 객체 생성
     */
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private KakaoAPI getKakaoAPI(){
        return retrofit.create(KakaoAPI.class);
    }

    public void getNearbyConv(MapView mapView, TextToSpeech tts, String key, double x, double y){
        KakaoAPI kakaoAPI = getKakaoAPI();
        kakaoAPI.getSearchCategory(key, "CS2", Double.toString(x), Double.toString(y), 500)
                .enqueue(new Callback<ResultSearchKeyword>() {
                    @Override
                    public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                        if(response.isSuccessful()){

                            if(response.body() != null){
                                List<Place> places = response.body().getDocuments();
                                Log.d("근처 편의점 API 호출", places.get(0).getPlace_name());

                                int tagNum = 10;
                                for (Place place : places) {
                                    MapPOIItem marker = new MapPOIItem(); //마커 생성
                                    marker.setItemName(place.getPlace_name());
                                    marker.setTag(tagNum++);
                                    double x = Double.parseDouble(place.getY()); //latitude
                                    double y = Double.parseDouble(place.getX()); //longitude

                                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x, y);
                                    marker.setMapPoint(mapPoint);
                                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); //마커타입 설정
                                    mapView.addPOIItem(marker);
                                }

                                //제일 가까운 편의점 가장 앞쪽에 배치
                                Collections.sort(places);

                                shopService.findNearConvStore(tts, places.get(0).toString());

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
