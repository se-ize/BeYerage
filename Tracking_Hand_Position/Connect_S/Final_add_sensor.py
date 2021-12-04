import tensorflow as tf
import cv2 as cv
import numpy as np
import csv
import HandTrackingModule as htm
import math
from datetime import datetime
import time
import ConnectAndData
import TTS_gtts_2 as TTS_gtts
import Server_Connect
import pyfirmata

videoname = 'RealCheck_' + str(datetime.today().month) + str(datetime.today().day) + '.avi'

start_msg = '객체인식을 통한 음료 안내를 시작합니다.'
restart_msg = '객체인식을 통한 음료 안내를 시작합니다.'
exit_msg = '객체인식을 통한 음료 안내를 종료합니다. 안내버튼을 다시 눌러주세요.'
touch_msg = '객체인식 시작을 위한 터치센서를 눌러주세요'
touch_start = 0 

################################
wCam, hCam = 640, 480
################################

cam = cv.VideoWriter(videoname,cv.VideoWriter_fourcc('D', 'I', 'V', 'X'),25,(640,480))
cap = cv.VideoCapture(0)
cap.set(3, wCam)
cap.set(4, hCam)
pTime = 0
# 카메라를 연결합니다.

b_number = 0

detector = htm.handDetector(detectionCon=0.7, maxHands=1)
area = 0


b_number = 0
model = tf.keras.models.Sequential([
    tf.keras.layers.Input((9,)),
    tf.keras.layers.Dense(10),
    tf.keras.layers.BatchNormalization(),
    tf.keras.layers.Activation('swish'),
    tf.keras.layers.Dense(10),
    tf.keras.layers.BatchNormalization(),
    tf.keras.layers.Activation('swish'),
    tf.keras.layers.Dense(10),
    tf.keras.layers.BatchNormalization(),
    tf.keras.layers.Activation('swish'),
    tf.keras.layers.Dense(1)
])

ML_Name = 'final_checkpoint_'+ str(datetime.today().month) + str(datetime.today().day)

#final_checkpoint_918
model.load_weights('final_checkpoint_115')

model.summary()
#print(model.predict(x_test[:1]))
#print(x_test[:1])

check = 0
area = 0
start_time = time.time()
start_time2 = time.time()

# 시작시간 체크

board = pyfirmata.Arduino('COM6')
#board = pyfirmata.Arduino('/dev/ttyACM0')

led_builtin = board.get_pin('d:13:o')
touch = board.get_pin('d:10:i')
magnetic = board.get_pin('d:8:i')

it = pyfirmata.util.Iterator(board)
it.start()

touch.enable_reporting()
magnetic.enable_reporting()

TTS_gtts.speak(start_msg)
detector = htm.handDetector(detectionCon=0.7, maxHands=1)

b = 1000
t = 1000

check_time = time.time()

class final:
    def eval(img,predictions,c,b,t,check_time):
        
        with open("connect_data.txt", 'r',encoding='UTF8') as f:
            x = list(f.readlines())
            xy_name = []
            for i in range(0,len(x)):
                x_name = list(x[i].split(','))
                xy_name.extend(x_name)
            #print(xy_name)
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
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 2:
                b_name = xy_name[8][8:-1]
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 3:
                b_name = xy_name[15][8:-1]
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 4:
                b_name = xy_name[22][8:-1]
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 5:
                b_name = xy_name[29][8:-1]
                print(b_name)
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 6:
                b_name = xy_name[36][8:-1]
                print(b_name)
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 7:
                b_name = xy_name[43][8:-1]
                print(b_name)
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 8:
                b_name = xy_name[50][8:-1]
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
            elif c == 9:
                b_name = xy_name[57][8:-1]
                speakstory = ConnectAndData.connect(b_name)
                t += 1
                if t > 6:
                    print(speakstory)
                    TTS_gtts.speak(speakstory)
                    print("표현까지 소요시간 : " + str(time.time()-check_time))
                    check_time = time.time()
                    cv.putText(img,'Real : ' + str(predictions), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    cv.putText(img,'About : ' + str(round(predictions)), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                    t = 0
                    return img, b, t, check_time
                return img, b, t, check_time
        else:
            b = c
            t = 0
            return img, b, t, check_time

    

    while(True):
        if touch == 1:
            start_camera = time.time()

        with open("connect_data.txt", 'r',encoding='UTF8') as f:
                x = list(f.readlines())
                xy_name = []
                for i in range(0,len(x)):
                    x_name = list(x[i].split(','))
                    xy_name.extend(x_name)
        
        if (time.time() - start_time) > 600:
            TTS_gtts.speak('서버로부터 데이터를 갱신 합니다.')
            start_time = time.time()
            Server_Connect.server_connect()

    
        success, img = cap.read()
        img = detector.findHands(img)
        lmList, bbox = detector.findPosition(img, draw=True)

        

        if len(lmList) != 0 and detector.fingersUp() == [0, 0, 1, 1, 1]:
            #print(detector.fingersUp())
            area = (bbox[2] - bbox[0]) * (bbox[3] - bbox[1])//100
            # print(area)
            if 70 < area < 1500:
                real_area = (bbox[2] - bbox[0]) * (bbox[3] - bbox[1])
                length, img, lineInfo = detector.findDistance(0, 8, img)
                length2, img, lineInfo = detector.findDistance(5, 17, img)
            
                result = [[real_area,length,length2,lineInfo[0],lineInfo[1],lineInfo[2],lineInfo[3],lineInfo[4],lineInfo[5]]]

                predictions = model.predict(result)
        
                with tf.compat.v1.Session() as sess:
                
                    #print(" 실 예측값 : ")
                    #print(predictions)
                    #print(" 근사 예측값 ")
                    a = list(map(float, predictions))
                    c = round(a[0])
                    if c > 9:
                        c = 9
                    print(c)

                    # eval 함수 이용
                    #img,b,t,check_time = eval(img,a[0],c,b,t,check_time)


                    if touch_start != 0:
                        if time.time() - touch_start > 30:
                            print(exit_msg)
                            TTS_gtts.speak(exit_msg)
                            touch_start = 0
                        else:
                            print(magnetic.read())
                            img,b,t,check_time = eval(img,a[0],c,b,t,check_time)
                    else:
                        # 핀에 출력값으로 0을 주면 led 불이 꺼집니다.
                        led_builtin.write(0)

        if touch.read():
            print(restart_msg)
            TTS_gtts.speak(restart_msg)
            touch_start = time.time()
            # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
            led_builtin.write(1)
            time.sleep(1)
        cam.write(img)
        cv.imshow('img', img)


        # ESC 키누르면 종료
        if cv.waitKey(1) & 0xFF == 27:
            cam.release()
            print('영상을 종료합니다. 녹화가 진행되었습니다.')
            break

    cv.destroyAllWindows()

