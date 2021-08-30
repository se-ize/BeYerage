package com.mobileapp.beyerage.shop;

import android.speech.tts.TextToSpeech;
import com.mobileapp.beyerage.dto.Beverage;

public interface ShopService {
    //디폴트 문장
    void defaultGuidance(TextToSpeech tts);
    //사용자가 원하는 음료안내, 음성인식 액세스 등 음성안내에 이용
    void voiceGuidance(TextToSpeech tts, String msg);
    //원하는 음료 안내
    void findUserWantBeverage(TextToSpeech tts, Beverage beverage);
    //추천 음료 안내
    void recommendBeverage(TextToSpeech tts, Beverage beverage);
    //근처 편의점에 대한 정보를 안내
    void findNearConvStore(TextToSpeech tts, String msg);
}
