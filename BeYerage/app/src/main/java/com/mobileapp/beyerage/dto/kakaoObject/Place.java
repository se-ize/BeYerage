package com.mobileapp.beyerage.dto.kakaoObject;

import com.google.gson.annotations.SerializedName;

public class Place implements Comparable<Place> {
    @SerializedName("id")
    private String id;           // 장소 ID
    @SerializedName("place_name")
    private String place_name;    // 장소명, 업체명
    @SerializedName("category_name")
    private String category_name;   // 카테고리 이름
    @SerializedName("category_group_code")
    private String category_group_code;    // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
    @SerializedName("category_group_name")
    private String category_group_name;   // 중요 카테고리만 그룹핑한 카테고리 그룹명
    @SerializedName("phone")
    private String phone;       // 전화번호
    @SerializedName("address_name")
    private String address_name;   // 전체 지번 주소
    @SerializedName("road_address_name")
    private String road_address_name;    // 전체 도로명 주소
    @SerializedName("x")
    private String x;         // X 좌표값 혹은 longitude
    @SerializedName("y")
    private String y;            // Y 좌표값 혹은 latitude
    @SerializedName("place_url")
    private String place_url;    // 장소 상세페이지 URL
    @SerializedName("distance")
    private String distance;      // 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter

    public String getPlace_name() {
        return place_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Place place) {
        if (this.distance.compareTo(place.distance) > 0) return -1;
        else return 1;
    }

    @Override
    public String toString() {
        return "" + place_name + '\'' +
                ", 전체 도로명 주소는," + road_address_name + '\'' +
                ", 거리는," + distance + "미터 입니다.";
    }
}
