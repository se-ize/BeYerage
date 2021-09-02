from datetime import datetime
import time
import os
import TTS_gtts_2 as TTS_gtts

a = datetime.today()            # 현재 날짜 가져오기
print(a)
b = datetime.today().year        # 현재 연도 가져오기
print(b)
c = datetime.today().month      # 현재 월 가져오기
print(c)

datetime.today().day        # 현재 일 가져오기
videoname = 'MakingDataSet_' + str(datetime.today().month) + str(datetime.today().day) + '.avi'
print(videoname)


import pyfirmata

try:
    board = pyfirmata.Arduino("/dev/ttyACM0")
except(TypeError) as e:
    print(e)
#board = pyfirmata.Arduino('COM4')
led_builtin = board.get_pin('d:13:o')
touch1 = board.get_pin('d:10:i')
it = pyfirmata.util.Iterator(board)
it.start()
touch1.enable_reporting()

check = 0
try:
    
    while(True):
        if touch1.read():
            if check == 0:
                check = 1
                print('loading')
                TTS_gtts.speak('객체인식을 통한 음료 안내를 잠시 중지합니다.')
                os.system('read -s -n 1 -p "Press any key to continue..."')
                print('wait')
                # 아무 키나 눌러주세요 -> 다만, 터치센서의 키도 가능한것인지 확인 필요.
                # 하지만, 작성자가 생각하기에 if문을 전체를 다시 확인하는 과정이라 가능할것으로 사료.
            else:
                check = 0
                TTS_gtts.speak('객체인식을 통한 음료 안내를 다시 시작합니다.')
                print('restart')

        '''
        if touch1.read():
            #TTS_gtts.speak('객체인식을 통한 음료 안내를 잠시 중지합니다.')
            print("touch on")
        else:
            print('touch off')
        '''
except(ValueError, TypeError) as e:
    print('에러정보 : ', e)

'''
while True:
    print( "1번 시작 또는 2번 정지 ")
    answer = int(input("선택해 : \n"))

    if answer == 2:
        
        print("중지한다.")
        os.system("pause")
    else:
        print("재 시작한다.")
'''