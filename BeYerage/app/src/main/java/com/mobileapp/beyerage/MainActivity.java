package com.mobileapp.beyerage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.mobileapp.beyerage.dto.Beverage;
import com.mobileapp.beyerage.network.BeverageAPI;
import com.mobileapp.beyerage.network.Server;
import com.mobileapp.beyerage.shop.ShopService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //컨트롤러
    private static final AppConfig appConfig = new AppConfig();
    //음성 서비스
    private static final ShopService shopService = appConfig.shopService();
    //HTTP API 호출 클래스
    private static final Server server = new Server();
    //TTS 변수 선언
    private TextToSpeech tts, tts2;
    //STT를 사용할 intent 와 SpeechRecognizer 초기화
    private SpeechRecognizer sRecognizer;
    private Intent intent;
    //음성인식 결과를 담을 변수
    private String result = new String();
    //DB에 담을 변수
    private String httpResponse = new String();
    private String param = new String();
    //퍼미션 체크를 위한 변수
    private final int PERMISSION = 1;

    private SpeechRecognizer speechRecognizer;
    //음성 허용 확인
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * http connection
         */
//        Beverage findBeverage = server.getUserWantBeverage("콜라");
//        Beverage findBeverage2 = server.getUserWantBeverage("사이다");

        /* TTS, STT */

        //TTS 환경설정
        checkTTS();

        Button button = (Button)findViewById(R.id.button);

        //버튼 클릭시 음성 안내 서비스 호출
        button.setOnClickListener(view -> {
            //음성안내 시작
            shopService.voiceGuidance(tts);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //음성인식 시작
                    startSTT();
                }
            }, 10000);
            // 10초 딜레이 첨부
        });
    }

    public class VoiceTask extends AsyncTask<String, Integer, String> {
        //AsyncTask < 보낼 내용이 string, 진행상황 업데이트할 때 integer로 전달, AsyncTask가 끝난 뒤 결과값은 String >
        String str = null;

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                getVoice();
            } catch (Exception e) {
                // TODO: handle exception
            }
            result = str;
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
            } catch (Exception e) {
                Log.d("onActivityResult", "getImageURL exception");
            }
        }
    }

    private void getVoice() {
        Intent intent = new Intent();
        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        String language = "ko-KR";

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        startActivityForResult(intent, 2);
    }

    //음성인식 환경설정
    private void startSTT() {
        //STT 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }
        VoiceTask voiceTask = new VoiceTask();
        voiceTask.execute();
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {}

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }
            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        }
        @Override
        public void onPartialResults(Bundle partialResults) {}
        @Override
        public void onEvent(int eventType, Bundle params) {}
    };


    private void checkTTS(){
        //TTS를 생성하고 OnInitListener로 초기화
        tts = new TextToSpeech(this, status -> {
            if(status == TextToSpeech.SUCCESS) {
                //언어 선택
                tts.setLanguage(Locale.KOREAN);
            }
        });
    }

    @Override
    protected void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (tts2 != null) {
            tts2.stop();
            tts2.shutdown();
        }
        super.onDestroy();
    }
}