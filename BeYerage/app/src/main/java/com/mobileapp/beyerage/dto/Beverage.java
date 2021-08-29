package com.mobileapp.beyerage.dto;

import com.google.gson.annotations.SerializedName;

public class Beverage {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private int price;
    @SerializedName("type")
    private BottleType type;
    @SerializedName("size")
    private int size;
    @SerializedName("row")
    private int row;
    @SerializedName("column")
    private int column;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public BottleType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "" + name + '\'' +
                ", 가격은" + price +
                "원, 종류는" + type +
                ", 크기는" + size +
                "ml,위치는" + row +
                "행" + column + "열에 위치합니다";
    }
}
