import cv2 as cv
import numpy as np
import csv
from datetime import datetime


def nothing(x):
    pass

color = [ 177, 177, 217]

one_pixel = np.uint8([[color]])
hsv = cv.cvtColor(one_pixel, cv.COLOR_BGR2HSV)
hsv = hsv[0][0]
threshold = 60
lower_blue1 = np.array([hsv[0], threshold, threshold])
upper_blue1 = np.array([180, 255, 255])
lower_blue2 = np.array([0, threshold, threshold])
upper_blue2 = np.array([hsv[0]+10-180, 255, 255])
lower_blue3 = np.array([hsv[0]-10, threshold, threshold])
upper_blue3 = np.array([hsv[0], 255, 255])

cv.namedWindow('img_color')
cv.namedWindow('img_result')

videoname = 'MakingDataSet_' + str(datetime.today().month) + str(datetime.today().day) + '.avi'
cam = cv.VideoWriter(videoname,cv.VideoWriter_fourcc('D', 'I', 'V', 'X'),25,(640,480))
cap = cv.VideoCapture(1)


TrainSetName = 'Train_Dataset_'+ str(datetime.today().month) + str(datetime.today().day)+ '.csv'
TestSetName = 'Test_Dataset_'+ str(datetime.today().month) + str(datetime.today().day)+ '.csv'

f = open(TrainSetName,'w', newline='')
b_number = 0

while(True):
    wr = csv.writer(f)
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
        # centerX,centerY = int(centroid[0]), int(centroid[1])
        # print(centerX, centerY)
        # print(area)

        centerX,centerY = int(centroid[0]), int(centroid[1])
    
        # # 손의 최소 크기를 설정후 데이터 셋을 구성해야 합니다.
        # if key == ord(' '):
        #     b_number = input("해당되는 번호를 누르세요\n")
        #     key = key = cv.waitKey(1)
        # wr.writerow([centerX,centerY,width,height,area,b_number])


        if  450 > height > 60:
            if 360 > width > 40:
                if key == 32:
                    b_number = input("해당되는 번호를 누르세요\n")
                    key = cv.waitKey(1)
                wr.writerow([centerX,centerY,width,height,area,b_number])
                #print(centerX, centerY)
                # print(height)
                cv.putText(img_color,'X : ' + str(centerX), (10, 20), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                cv.putText(img_color,'Y : ' + str(centerY), (10, 50), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                cv.putText(img_color,'Area : ' + str(area), (10, 80), cv.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0), 1, lineType=cv.LINE_AA)
                cv.circle(img_color, (centerX, centerY), 10, (0,0,255), 10)
                cv.rectangle(img_color, (x,y), (x+width,y+height), (0,0,255))
    
    cam.write(img_color)
    cv.imshow('img_color', img_color)
    #cv.imshow('img_mask', img_mask)
    cv.imshow('img_result', img_result)

    # ESC 키누르면 종료
    if cv.waitKey(1) & 0xFF == 27:
        cam.release()
        f.close()
        print('영상을 종료합니다. 녹화가 진행되었습니다.')
        break

cv.destroyAllWindows()