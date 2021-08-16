package com.mobileapp.beyerage.shop;

import android.speech.tts.TextToSpeech;
import com.mobileapp.beyerage.dto.Beverage;

public interface ShopService {
    //고객이 버튼 클릭 시 음성안내
    void voiceGuidance(TextToSpeech tts);
    //추천 음료 안내
    void recommendBeverage(TextToSpeech tts, Beverage beverage);
    //지원 편의점 안내
    void voiceGuidance3(TextToSpeech tts);
}
