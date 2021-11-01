import cv2 as cv
import numpy as np
import csv
import HandTrackingModule as htm
import math
from datetime import datetime
import time


################################
wCam, hCam = 640, 480
################################

videoname = 'MakingDataSet_' + str(datetime.today().month) + str(datetime.today().day) + '.avi'
cam = cv.VideoWriter(videoname,cv.VideoWriter_fourcc('D', 'I', 'V', 'X'),25,(640,480))
cap = cv.VideoCapture(0)
cap.set(3, wCam)
cap.set(4, hCam)
pTime = 0

TrainSetName = 'Train_Dataset_'+ str(datetime.today().month) + str(datetime.today().day)+ '.csv'
TestSetName = 'Test_Dataset_'+ str(datetime.today().month) + str(datetime.today().day)+ '.csv'

f = open(TestSetName,'w', newline='')
b_number = 0

detector = htm.handDetector(detectionCon=0.7, maxHands=1)

area = 0

while True:
    wr = csv.writer(f)
    success, img = cap.read()
    key = cv.waitKey(1)

    # Find Hand
    img = detector.findHands(img)
    lmList, bbox = detector.findPosition(img, draw=True)
    if len(lmList) != 0:

        # Filter based on size
        area = (bbox[2] - bbox[0]) * (bbox[3] - bbox[1])//100
        #print(area)
        if 70 < area < 900:
            real_area = (bbox[2] - bbox[0]) * (bbox[3] - bbox[1])
            # Find Distance between index and Thumb
            length, img, lineInfo = detector.findDistance(0, 8, img)
            # print(length)

            # Check fingers up
            fingers = detector.fingersUp()
            #print(fingers)

            if not fingers[1]:
                cv.circle(img, (lineInfo[4], lineInfo[5]), 15, (0, 255, 0), cv.FILLED)
                if key == 32:
                        b_number = input("해당되는 번호를 누르세요\n")
                        key = cv.waitKey(1)
                wr.writerow([real_area,length,lineInfo[0],lineInfo[1],lineInfo[2],lineInfo[3],lineInfo[4],lineInfo[5],b_number])
            
# Drawings

    # Frame rate
    cam.write(img)
    cTime = time.time()
    fps = 1 / (cTime - pTime)
    pTime = cTime
    #cv.putText(img, f'FPS: {int(fps)}', (40, 50), cv.FONT_HERSHEY_COMPLEX,1, (255, 0, 0), 3)
    cv.imshow("Img", img)

    if cv.waitKey(1) & 0xFF == 27:
        cam.release()
        f.close()
        print('영상을 종료합니다. 녹화가 진행되었습니다.')
        break

cv.destroyAllWindows()