package com.mobileapp.beyerage.shop;

import android.speech.tts.TextToSpeech;

public interface ShopService {
    //고객이 버튼 클릭 시 음성안내
    void voiceGuidance(TextToSpeech tts);
    //음료 위치안내
    void voiceGuidance2(TextToSpeech tts, String result);
    //지원 편의점 안내
    void voiceGuidance3(TextToSpeech tts);
}
