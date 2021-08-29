package com.mobileapp.beyerage.shop;

import android.speech.tts.TextToSpeech;
import com.mobileapp.beyerage.dto.Beverage;

public interface ShopService {
    //디폴트 문장
    void defaultGuidance(TextToSpeech tts);
    //사용자가 원하는 음료를 말하도록 유도
    void voiceGuidance(TextToSpeech tts);
    //원하는 음료 안내
    void findUserWantBeverage(TextToSpeech tts, Beverage beverage);
    //추천 음료 안내
    void recommendBeverage(TextToSpeech tts, Beverage beverage);
    //kakao 맵이 시작될 때 맵을 찾는 안내
    void voiceGuidanceMapStart(TextToSpeech tts);
    //근처 편의점에 대한 정보를 안내
    void findNearConvStore(TextToSpeech tts, String msg);
}
