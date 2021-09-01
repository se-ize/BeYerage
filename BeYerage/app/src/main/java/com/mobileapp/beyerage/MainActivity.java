package com.mobileapp.beyerage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobileapp.beyerage.dto.Beverage;
import com.mobileapp.beyerage.network.BeverageAPI;
import com.mobileapp.beyerage.network.BeverageAPIController;
import com.mobileapp.beyerage.shop.ShopService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity{
    //컨트롤러
    private static final AppConfig appConfig = new AppConfig();
    //음성 서비스
    private static final ShopService shopService = appConfig.shopService();
    //HTTP API 호출 클래스
    private static final BeverageAPIController beverageAPIController = new BeverageAPIController();
    //TTS 변수 선언
    private TextToSpeech tts;
    //STT를 사용할 intent 와 SpeechRecognizer 초기화
    private SpeechRecognizer sRecognizer;
    //퍼미션 체크를 위한 변수
    private final int PERMISSION = 1;
    //음성인식 결과를 담는 변수
    private String userVoice = "";
    //로그 확인용
    private String tag = "MainActivity";

    //해시키 발급
    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null)
                return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //해시키 로그에 출력
        Log.e("GR_KeyHash",getKeyHash(MainActivity.this));

        // Get permission
        String[] permission_list = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(MainActivity.this, permission_list, 1);

        /* TTS, STT */

        //TTS 환경설정
        setTTS();
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        Button findBeverageButton = (Button) findViewById(R.id.findBeverageButton);
        Button mostFreqBeverageButton = (Button) findViewById(R.id.mostFreqBeverageButton);
        Button closeConvStoreButton = (Button) findViewById(R.id.closeConvenienceStoreButton);

        //당겨서 메뉴 안내
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                               @Override
                                               public void onRefresh() {
                                                   shopService.defaultGuidance(tts);
                                                   refreshLayout.setRefreshing(false);
                                               }
                                           });

        /**
         * 원하는 음료 안내
         */
        //버튼 클릭시 음성 안내 서비스 호출
        findBevButtonEvent(findBeverageButton);

        /**
         * 추천 음료 안내
         */
        mostFreqButtonEvent(mostFreqBeverageButton);

        /**
         * 근처 편의점 안내
         */
        //버튼 클릭시 음성 안내 서비스 호출
        closeConvStoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            startActivity(intent);

            shopService.voiceGuidance(tts, "현재 위치를 파악중입니다.");
        });
    }

    private void findBevButtonEvent(Button findBeverageButton) {

        //버튼 클릭시 원하는 음료 안내 서비스 호출
        findBeverageButton.setOnClickListener(view -> {
            shopService.voiceGuidance(tts, "찾으실 음료를 말씀해주세요");
            new Handler().postDelayed(() -> {
                //음성인식 시작
                Intent intent = setSTTPermission();
                sRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
                sRecognizer.setRecognitionListener(listener);
                sRecognizer.startListening(intent);

            }, 2200);
            // 2.2초 딜레이 첨부

        });
    }

    private void mostFreqButtonEvent(Button mostFreqBeverageButton) {

        //버튼 클릭시 음성 안내 서비스 호출
        mostFreqBeverageButton.setOnClickListener(view -> {
            new findFreqBeverageAsyncTask().execute();
        });
    }

    /**
     * 비동기식 방식 HTTP CONNECTION
     * 가장 많이 찾는 음료를 가져옴
     */
    public class findFreqBeverageAsyncTask extends AsyncTask<Void, Void, Beverage>{

        @Override
        protected Beverage doInBackground(Void... params) {
            BeverageAPI beverageAPI = beverageAPIController.getBeverageAPI();
            Call<Beverage> freqBeverageData = beverageAPI.getFreqBeverageData();
            try{
                return freqBeverageData.execute().body();
            } catch (Exception e){
                e.printStackTrace();
                Log.d(tag,"Network IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Beverage beverage) {
            super.onPostExecute(beverage);
            if(beverage != null){
                shopService.recommendBeverage(tts, beverage);
                Log.d("getFreqBeverage name= ", beverage.getName());
                Toast.makeText(getApplicationContext(), "추천 음료명 : " + beverage.getName(),Toast.LENGTH_SHORT).show();
            } else {
                shopService.recommendBeverage(tts, null);
                Log.d("getFreqBeverage name= ", null);
            }

        }
    }

    /**
     * 비동기식 방식 HTTP CONNECTION
     * 사용자가 원하는 음료를 가져옴
     */
    public class findBeverageAsyncTask extends AsyncTask<Void, Void, Beverage> {

        @Override
        protected Beverage doInBackground(Void... params) {
            BeverageAPI beverageAPI = beverageAPIController.getBeverageAPI();
            Call<Beverage> findBeverageData = beverageAPI.getBeverageData(userVoice);
            try {
                return findBeverageData.execute().body();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(tag,"Network IOException");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Beverage beverage) {
            super.onPostExecute(beverage);
            if(beverage != null){
                shopService.findUserWantBeverage(tts, beverage);
                Log.d("getWantBeverage name=", beverage.getName());
                Toast.makeText(getApplicationContext(), "찾으신 음료명 : " + beverage.getName(),Toast.LENGTH_SHORT).show();
            } else {
                shopService.findUserWantBeverage(tts, null);
                Log.d(tag, "없는 음료 검색");
                Toast.makeText(getApplicationContext(), "없는 음료 검색",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private Intent setIntentForVoiceRec() {
        //사용자에게 음성을 요구하고 음성 인식기를 통해 전송하는 활동 시작
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        //음석을 번역할 언어 설정
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        return intent;
    }

    //음성인식 환경설정
    private Intent setSTTPermission() {
        //STT 퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }
        // STT intent 설정
        Intent intent = setIntentForVoiceRec();
        return intent;
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
                    message = "퍼미션 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃 에러";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음 에러";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER BUSY 에러";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버에 문제";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류";
                    break;
            }
            //사용자에게 오류 안내
            if(error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS){
                shopService.voiceGuidance(tts, message + "가 발생하였습니다. 액세스 허용을 해주세요.");
            } else {
                shopService.voiceGuidance(tts, message + "가 발생하였습니다.");
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            userVoice = "";

            ArrayList<String> matches = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (String match : matches) {
                userVoice += match;
            }

            findBeverageAsyncTask voiceTask = new findBeverageAsyncTask();
            voiceTask.execute();

        }
        @Override
        public void onPartialResults(Bundle partialResults) {}
        @Override
        public void onEvent(int eventType, Bundle params) {}
    };


    private void setTTS(){
        //TTS를 생성하고 OnInitListener로 초기화
        tts = new TextToSpeech(this, status -> {
            if(status == TextToSpeech.SUCCESS) {
                //언어 선택
                tts.setLanguage(Locale.KOREAN);
                shopService.defaultGuidance(tts);
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

        super.onDestroy();
    }
}