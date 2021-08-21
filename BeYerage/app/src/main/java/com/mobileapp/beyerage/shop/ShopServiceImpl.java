package com.mobileapp.beyerage.shop;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.mobileapp.beyerage.MainActivity;
import com.mobileapp.beyerage.R;
import com.mobileapp.beyerage.dto.Beverage;

import org.w3c.dom.Text;

public class ShopServiceImpl extends MainActivity implements ShopService{
    String tag;

    @Override
    public void voiceGuidance(TextToSpeech tts) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("찾으실 음료를 말씀해주세요", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("찾으실 음료를 말씀해주세요", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void findUserWantBeverage(TextToSpeech tts, Beverage beverage) {

        String msg = "검색결과 " + beverage;

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
    public void voiceGuidance3(TextToSpeech tts) {
        //지원 편의점에서 버튼을 클릭했을 때 음성안내
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("현재 사용자의 위치를 기반으로, 지원되는 편의점은 경기대학교 제2공학관 4층 편의점이며, 위치는 경기도 수원시 영통구 광교산로 154-42입니다.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("현재 사용자의 위치를 기반으로, 지원되는 편의점은 경기대학교 제2공학관 4층 편의점이며, 위치는 경기도 수원시 영통구 광교산로 154-42입니다.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void defaultGuidance(TextToSpeech tts) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            //QUEUE_FLUSH: Queue 값을 초기화한 후 값을 넣는다.
            tts.speak("음료 위치 안내는 첫번째 버튼,\n" +
                    "인기있는 음료 추천은 두번째 버튼,\n" +
                    "근처 편의점 안내는 세번째 버튼을 터치해주세요.\n" +
                    "다시 듣고 싶으시면 화면을 아래로 당겨주세요.", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak("음료 위치 안내는 첫번째 버튼,\n" +
                    "인기있는 음료 추천은 두번째 버튼,\n" +
                    "근처 편의점 안내는 세번째 버튼을 터치해주세요.\n" +
                    "다시 듣고 싶으시면 화면을 아래로 당겨주세요.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
