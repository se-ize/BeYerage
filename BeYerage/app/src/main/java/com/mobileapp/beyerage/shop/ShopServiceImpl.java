package com.mobileapp.beyerage.shop;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.mobileapp.beyerage.MainActivity;
import com.mobileapp.beyerage.R;
import com.mobileapp.beyerage.dto.Beverage;
import com.mobileapp.beyerage.network.Server;

public class ShopServiceImpl extends MainActivity implements ShopService{
    String tag;

    @Override
    public void voiceGuidance(TextToSpeech tts) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("드가자", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("드가자", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void findUserWantBeverage(TextToSpeech tts, Beverage beverage) {

        // 일단 이거 빼고
        Log.d(tag, "◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎3번◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎◎");
        // String msg = "찾으시는 음료는 " + beverage;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("여기까지 오는지", TextToSpeech.QUEUE_FLUSH, null, null);
            Log.d(tag, "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼4번▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼");
        } else {
            tts.speak("여기까지", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void recommendBeverage(TextToSpeech tts, Beverage beverage) {

        String msg = "사람들이 가장 많이 찾는 음료는 " + beverage;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void voiceGuidance3(TextToSpeech tts) {
        //지원 편의점에서 버튼을 클릭했을 때 음성안내
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("현재 사용자의 위치를 기반으로, 지원되는 편의점은 경기대학교 제2공학관 4층 편의점이며, 위치는 경기도 수원시 영통구 광교산로 154-42입니다.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("현재 사용자의 위치를 기반으로, 지원되는 편의점은 경기대학교 제2공학관 4층 편의점이며, 위치는 경기도 수원시 영통구 광교산로 154-42입니다.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
