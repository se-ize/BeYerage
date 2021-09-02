import tensorflow as tf
import cv2 as cv
import numpy as np
import ConnectAndData
import TTS_gtts_2 as TTS_gtts
import time
import Server_Connect
from datetime import datetime

#bp = np.loadtxt('first_dataset_2.csv', delimiter=',', dtype=np.float32)
#x_test = bp[:, 0:-1]
#y_test = bp[:, [-1]]


def nothing(x):
    pass

######### 색상 설정 #############
color = [ 83 , 89 , 105]

one_pixel = np.uint8([[color]])
hsv = cv.cvtColor(one_pixel, cv.COLOR_BGR2HSV)
hsv = hsv[0][0]
threshold = 50
lower_blue1 = np.array([hsv[0], threshold, threshold])
upper_blue1 = np.array([180, 255, 255])
lower_blue2 = np.array([0, threshold, threshold])
upper_blue2 = np.array([hsv[0]+10-180, 255, 255])
lower_blue3 = np.array([hsv[0]-10, threshold, threshold])
upper_blue3 = np.array([hsv[0], 255, 255])
#################################

cv.namedWindow('img_color')
cv.namedWindow('img_result')

videoname = 'RealCheck_' + str(datetime.today().month) + str(datetime.today().day) + '.avi'
cam = cv.VideoWriter(videoname,cv.VideoWriter_fourcc('D', 'I', 'V', 'X'),25,(640,480))
cap = cv.VideoCapture(0)
# 카메라를 연결합니다.

with open("connect_data.txt", 'r',encoding='UTF8') as f:
            x = list(f.readlines())
            xy_name = []
            for i in range(0,len(x)):
                x_name = list(x[i].split(','))
                xy_name.extend(x_name)
            #print(xy_name)

b_number = 0
model = tf.keras.models.Sequential([
    tf.keras.layers.Input((5,)),
    tf.keras.layers.Dense(8),
    tf.keras.layers.BatchNormalization(),
    tf.keras.layers.Activation('swish'),
    tf.keras.layers.Dense(8),
    tf.keras.layers.BatchNormalization(),
    tf.keras.layers.Activation('swish'),
    tf.keras.layers.Dense(8),
    tf.keras.layers.BatchNormalization(),
    tf.keras.layers.Activation('swish'),
    tf.keras.layers.Dense(1)
])


model.load_weights('bp_final_checkpoint')
model.summary()
#print(model.predict(x_test[:1]))
#print(x_test[:1])
b = 1000
t = 1000
check = 0
start_time = time.time()
    # 시작시간 체크

'''
board = pyfirmata.Arduino('/dev/ttyACM0')
led_builtin = board.get_pin('d:13:o')
touch1 = board.get_pin('d:10:i')
it = pyfirmata.util.Iterator(board)
it.start()
touch1.enable_reporting()
''' # 터치센서 관련 코드

TTS_gtts.speak('객체인식을 통한 음료 안내를 시작합니다.')
check_time = time.time()
while(True):
    with open("connect_data.txt", 'r',encoding='UTF8') as f:
            x = list(f.readlines())
            xy_name = []
            for i in range(0,len(x)):
                x_name = list(x[i].split(','))
                xy_name.extend(x_name)
            #print(xy_name)
    '''
    if touch1.read():
        if check == 0:
            TTS_gtts.speak('객체인식을 통한 음료 안내를 잠시 중지합니다.')
            check = 1
            #time.sleep(30)
        else:
            check = 0
            TTS_gtts.speak('객체인식을 통한 음료 안내를 다시 시작합니다.')
    ''' # 터치센서 관련 코드
    #     time.sleep(3)
        # 10분에 한번 서버에서 데이터를 가져옵니다. 
    if (time.time() - start_time) > 60:
        TTS_gtts.speak('서버로부터 데이터를 갱신 합니다.')
        start_time = time.time()
        Server_Connect.server_connect()
        
    ret,img_color = cap.read()
    
    height, width = img_color.shape[:2]
    #print(img_color.shape[:2])
    img_color = cv.resize(img_color, (width, height), interpolation=cv.INTER_AREA)

    # 원본 영상을 HSV 영상으로 변환합니다.
    img_hsv = cv.cvtColor(img_color, cv.COLOR_BGR2HSV)

    # 범위 값으로 HSV 이미지에서 마스크를 생성합니다.
    img_mask1 = cv.inRange(img_hsv, lower_blue1, upper_blue1)
    img_mask2 = cv.inRange(img_hsv, lower_blue2, upper_blue2)
    img_mask3 = cv.inRange(img_hsv, lower_blue3, upper_blue3)
    img_mask = img_mask1 | img_mask2 | img_mask3

    kernel = np.ones((11,11), np.uint8)
    img_mask = cv.morphologyEx(img_mask, cv.MORPH_OPEN, kernel)
    img_mask = cv.morphologyEx(img_mask, cv.MORPH_CLOSE, kernel)

    # 마스크 이미지로 원본 이미지에서 범위값에 해당되는 영상 부분을 획득합니다.
    img_result = cv.bitwise_and(img_color, img_color, mask=img_mask)


    numOfLabels, img_label, stats, centroids = cv.connectedComponentsWithStats(img_mask)

    key = cv.waitKey(1)
    for idx, centroid in enumerate(centroids):
        if stats[idx][0] == 0 and stats[idx][1] == 0:
            continue
        if np.any(np.isnan(centroid)):
            continue

        x,y,width,height,area = stats[idx]
        
        centerX,centerY = int(centroid[0]), int(centroid[1])
    
        if  (450 > height > 60) and check == 0:
            if (360 > width > 40):
                
                result = [[centerX,centerY,width,height,area]]

                predictions = model.predict(result)
            
                

                with tf.compat.v1.Session() as sess:
                
                    print(" 실 예측값 : ")
                    print(predictions)
                    print(" 근사 예측값 ")
                    a = list(map(float, predictions))
                    #print(round(a[0]))
                    c = round(a[0])
                    if c > 16:
                        c = 16
                        print(c)
                    elif 16.2 > predictions > 15.2:
                        c = 15
                        print(c)
                    elif 15.2 > predictions > 14.2:
                        c = 14
                        print(c)
                    elif 14.2 > predictions > 13:
                        c = 13
                        print(c)
                    else:
                        print(c)

                    if b == c:
                        if c == 1:
                            b_name = xy_name[1][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 2:
                            b_name = xy_name[8][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 3:
                            b_name = xy_name[15][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 4:
                            b_name = xy_name[22][6:-2]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 5:
                            b_name = xy_name[29][8:-1]
                            print(b_name)
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 6:
                            b_name = xy_name[36][8:-1]
                            print(b_name)
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 7:
                            b_name = xy_name[43][8:-1]
                            print(b_name)
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 8:
                            b_name = xy_name[50][6:-2]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 9:
                            b_name = xy_name[43][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 10:
                            b_name = xy_name[50][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 11:
                            b_name = xy_name[13][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 12:
                            b_name = xy_name[14][6:-2]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        
                        elif c == 13:
                            b_name = xy_name[16][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 14:
                            b_name = xy_name[17][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 15:
                            b_name = xy_name[18][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        elif c == 16:
                            b_name = xy_name[19][8:-1]
                            speakstory = ConnectAndData.connect(b_name)
                            t += 1
                            if t > 6:
                                print(speakstory)
                                print(speakstory)
                                TTS_gtts.speak(speakstory)
                                print("표현까지 소요시간 : " + str(time.time()-check_time))
                                check_time = time.time()
                                cv.putText(img_color,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                cv.putText(img_color,'About : ' + str(round(a[0])), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                                t = 0
                            continue
                        
                    else:
                        b = c
                        t = 0
                    #print(round(a))

    cam.write(img_color)
    cv.imshow('img_color', img_color)
    #cv.imshow('img_mask', img_mask)
    cv.imshow('img_result', img_result)

    # ESC 키누르면 종료
    if cv.waitKey(1) & 0xFF == 27:
        cam.release()
        print('영상을 종료합니다. 녹화가 진행되었습니다.')
        break

cv.destroyAllWindows()

