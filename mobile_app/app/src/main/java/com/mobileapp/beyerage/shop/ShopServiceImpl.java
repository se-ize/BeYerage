package com.mobileapp.beyerage.shop;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.mobileapp.beyerage.MainActivity;
import com.mobileapp.beyerage.dto.Beverage;

public class ShopServiceImpl extends MainActivity implements ShopService {

    @Override
    public void voiceGuidance(TextToSpeech tts, String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void findUserWantBeverage(TextToSpeech tts, Beverage beverage) {

        String msg = "검색결과 ";
        if (beverage == null) msg += "해당 음료는 존재하지 않습니다.";
        else msg += beverage;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void recommendBeverage(TextToSpeech tts, Beverage beverage) {

        String msg = "사람들이 가장 많이 찾는 음료는 " + beverage;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void findNearConvStore(TextToSpeech tts, String msg) {
        Log.d("가까운 편의점 안내 access: ", msg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("현재 위치기반 가장 가까운 편의점은," + msg + " 편의점 문을 열고 앞으로 직진하시면 정면에 음료수 냉장고가 있습니다." +
                    " 해당 편의점 내의 음료 위치 안내는 하단의 좌측 버튼," +
                    "가장 인기 있는 음료 안내는 하단의 우측 버튼을 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("현재 위치기반 가장 가까운 편의점은," + msg + " 편의점 문을 열고 앞으로 직진하시면 정면에 음료수 냉장고가 있습니다." +
                    " 해당 편의점 내의 음료 위치 안내는 하단의 좌측 버튼," +
                    "가장 인기 있는 음료 안내는 하단의 우측 버튼을 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void defaultGuidance(TextToSpeech tts) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("화면을 터치하시면 음료 안내 서비스를 실행합니다.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("화면을 터치하시면 음료 안내 서비스를 실행합니다.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void reGuide(TextToSpeech tts) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("편의점 내의 음료 위치 안내는 하단의 좌측 버튼," +
                    "가장 인기 있는 음료 안내는 하단의 우측 버튼을 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("편의점 내의 음료 위치 안내는 하단의 좌측 버튼," +
                    "가장 인기 있는 음료 안내는 하단의 우측 버튼을 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void vocQuestion(TextToSpeech tts) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("고객의소리", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("고객의소리", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
