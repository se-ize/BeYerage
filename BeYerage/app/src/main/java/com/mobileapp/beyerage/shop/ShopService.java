package com.mobileapp.beyerage.shop;

import android.speech.tts.TextToSpeech;
import com.mobileapp.beyerage.dto.Beverage;

public interface ShopService {
    //사용자가 원하는 음료를 말하도록 유도
    void voiceGuidance(TextToSpeech tts);
    //원하는 음료 안내
    void findUserWantBeverage(TextToSpeech tts, Beverage beverage);
    //추천 음료 안내
    void recommendBeverage(TextToSpeech tts, Beverage beverage);
    //지원 편의점 안내
    void voiceGuidance3(TextToSpeech tts);
}
