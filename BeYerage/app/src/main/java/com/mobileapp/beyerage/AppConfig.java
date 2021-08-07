package com.mobileapp.beyerage;

import com.mobileapp.beyerage.shop.ShopService;
import com.mobileapp.beyerage.shop.ShopServiceImpl;

public class AppConfig {

    public ShopService shopService(){
        return new ShopServiceImpl();
    }

}
