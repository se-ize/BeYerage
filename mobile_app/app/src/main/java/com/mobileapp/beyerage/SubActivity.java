package com.mobileapp.beyerage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mobileapp.beyerage.dto.Beverage;
import com.mobileapp.beyerage.network.BeverageAPI;
import com.mobileapp.beyerage.network.BeverageAPIController;
import com.mobileapp.beyerage.network.KakaoAPIController;
import com.mobileapp.beyerage.shop.ShopService;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;


public class SubActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener{

    private static final AppConfig appConfig = new AppConfig();
    //음성 서비스
    private static final ShopService shopService = appConfig.shopService();
    //카카오 API 컨트롤러
    private static final KakaoAPIController kakaoAPIController = new KakaoAPIController();
    //Text to Speech 변수 선언
    private TextToSpeech tts;
    //kakao api key
    private static final String API_KEY = "KakaoAK ac630fe1cb94f321ea8304474e644b3b";

    private static final String LOG_TAG = "SubActivity";
    private MapView mapView;
    private RelativeLayout mapViewContainer;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    private double current_latitude;
    private double current_longitude;

    MapPoint currentMapPoint;

    private BackPressCloseHandler backPressCloseHandler;
    //HTTP API 호출 클래스
    private static final BeverageAPIController beverageAPIController = new BeverageAPIController();
    //STT를 사용할 intent 와 SpeechRecognizer 초기화
    private SpeechRecognizer sRecognizer;
    //퍼미션 체크를 위한 변수
    private final int PERMISSION = 1;
    //음성인식 결과를 담는 변수
    private String userVoice = "";
    //로그 확인용
    private String tag;

    //해시키 찾기
    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_sub);
        //TTS 설정
        setTTS();
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        //당겨서 메뉴 안내
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shopService.reGuide(tts);
                refreshLayout.setRefreshing(false);
            }
        });

        Button findBeverageButton = (Button) findViewById(R.id.findBeverageButton);
        Button mostFreqBeverageButton = (Button) findViewById(R.id.mostFreqBeverageButton);

        /**
         * 원하는 음료 안내
         */
        //버튼 클릭시 음성 안내 서비스 호출
        findBevButtonEvent(findBeverageButton);

        /**
         * 추천 음료 안내
         */
        mostFreqButtonEvent(mostFreqBeverageButton);


        //퍼미션 체크
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }

        //해시키 가져오기
        getHashKey();
        //Mapview 세팅
        setMapview();

        /**
         * Kakao API controller
         * 현재 위치기반으로 가까운 편의점 조회해서 음성안내
         */
        Handler handler = new Handler();
        handler.postDelayed(() -> kakaoAPIController.getNearbyConv(mapView, tts, API_KEY, current_longitude, current_latitude), 5000);
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
    public class findFreqBeverageAsyncTask extends AsyncTask<Void, Void, Beverage> {

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

    private void setMapview() {
        Toast.makeText(this, "맵을 로딩중입니다", Toast.LENGTH_LONG).show();

        mapView = new MapView(this);
        mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        mapView.setMapViewEventListener(this);

        setCurrentLocationUpdate();
    }

    private void setCurrentLocationUpdate() {
        //맵 리스너 (현재위치 업데이트)
        mapView.setCurrentLocationEventListener(this);

        //현위치 찾기
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        mapView.setCurrentLocationRadius(500);
        mapView.zoomOut(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TTS shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        //mapView shutdown!
        if(mapViewContainer != null) mapViewContainer.removeAllViews();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {

        //내 위치 찾기
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);

        //지도 중심 이동
        mapView.setMapCenterPoint(currentMapPoint, true);

        //전역변수로 현재 좌표 저장
        current_latitude = mapPointGeo.latitude;
        current_longitude = mapPointGeo.longitude;
        Log.d(LOG_TAG, "현재위치: " + current_latitude + "  " + current_longitude);

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        Log.i(LOG_TAG, "onCurrentLocationUpdateFailed");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        Log.i(LOG_TAG, "onCurrentLocationUpdateCancelled");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
    }


    //ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            //요청 코드가 PERMISSIONS_REQUEST_CODE이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            //모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져오도록 함
            }
            else {
                //거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(SubActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    shopService.voiceGuidance(tts, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.");
                    finish();
                }else {
                    Toast.makeText(SubActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다.", Toast.LENGTH_LONG).show();
                    shopService.voiceGuidance(tts, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다.");
                }
            }
        }
    }
    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        //1. 위치 퍼미션을 가지고 있는지 체크
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(SubActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            //2. 이미 퍼미션을 가지고 있다면
            //( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            //3. 위치 값을 가져올 수 있음

        } else { //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요. 2가지 경우(3-1, 4-1)가 있음
            //3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(SubActivity.this, REQUIRED_PERMISSIONS[0])) {
                //3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있음
                Toast.makeText(SubActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                shopService.voiceGuidance(tts, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.");
                //3-3. 사용자게에 퍼미션 요청함. 요청 결과는 onRequestPermissionResult에서 수신됨
                ActivityCompat.requestPermissions(SubActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                //4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 함
                //요청 결과는 onRequestPermissionResult에서 수신됨
                ActivityCompat.requestPermissions(SubActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }

    }

    //GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SubActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }


    private void setTTS(){
        //TTS를 생성하고 OnInitListener로 초기화
        tts = new TextToSpeech(this, status -> {
            if(status == TextToSpeech.SUCCESS) {
                //언어 선택
                tts.setLanguage(Locale.KOREAN);
            }
        });
    }
}
