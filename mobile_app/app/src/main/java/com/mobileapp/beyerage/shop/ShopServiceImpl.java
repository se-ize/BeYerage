package com.mobileapp.beyerage.shop;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.mobileapp.beyerage.MainActivity;
import com.mobileapp.beyerage.dto.Beverage;

public class ShopServiceImpl extends MainActivity implements ShopService{

    @Override
    public void voiceGuidance(TextToSpeech tts, String msg) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void findUserWantBeverage(TextToSpeech tts, Beverage beverage) {

        String msg = "검색결과 ";
        if(beverage == null) msg += "해당 음료는 존재하지 않습니다.";
        else msg += beverage;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
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
    public void findNearConvStore(TextToSpeech tts, String msg) {
        Log.d("가까운 편의점 안내 access: ", msg);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("현재 위치기반 가장 가까운 편의점은," + msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("현재 위치기반 가장 가까운 편의점은," + msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void defaultGuidance(TextToSpeech tts) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("근처 편의점 안내는 첫번째 버튼," +
                    "음료 위치 안내는 두번째 버튼," +
                    "인기있는 음료 추천은 세번째 버튼을 터치해주세요. " +
                    "다시 듣고 싶으시면 화면을 아래로 당겨주세요.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("근처 편의점 안내는 첫번째 버튼," +
                    "음료 위치 안내는 두번째 버튼," +
                    "인기있는 음료 추천은 세번째 버튼을 터치해주세요. " +
                    "다시 듣고 싶으시면 화면을 아래로 당겨주세요.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
