
import time
import pyfirmata
import pyfirmata2
import test0330
import testtts3
import connect10
import connect7
import testtts

# 아두이노에 연결합니다. 

board2 = pyfirmata2.Arduino('/dev/serial/by-path/platform-fd500000.pcie-pci-0000:01:00.0-usb-0:1.1:1.0')
board1 = pyfirmata.ArduinoMega('/dev/serial/by-path/platform-fd500000.pcie-pci-0000:01:00.0-usb-0:1.2:1.0')

# 디지털(digital) 핀(pin) 13번을 출력(output) 모드로 가져옵니다. 
# 아두이노 메가

led_builtin = board1.get_pin('d:13:o')
touch4 = board1.get_pin('d:22:i')
touch3 = board1.get_pin('d:23:i')
touch2 = board1.get_pin('d:24:i')
touch1 = board1.get_pin('d:25:i')
touch5 = board1.get_pin('d:6:i')
touch6 = board1.get_pin('d:7:i')
touch7 = board1.get_pin('d:4:i')
touch8 = board1.get_pin('d:5:i')
touch9 = board1.get_pin('d:50:i')
touch10 = board1.get_pin('d:51:i')
touch11 = board1.get_pin('d:52:i')
touch12 = board1.get_pin('d:53:i')

# 아두이노 우노

led_builtin2 = board2.get_pin('d:13:o')
touch13 = board2.get_pin('d:8:i')
touch14 = board2.get_pin('d:9:i')
touch15 = board2.get_pin('d:10:i')
touch16 = board2.get_pin('d:11:i')

it = pyfirmata.util.Iterator(board1)
it.start()
it2 = pyfirmata2.util.Iterator(board2)
it2.start()

touch1.enable_reporting()
touch2.enable_reporting()
touch3.enable_reporting()
touch4.enable_reporting()
touch5.enable_reporting()
touch6.enable_reporting()
touch7.enable_reporting()
touch8.enable_reporting()
touch9.enable_reporting()
touch10.enable_reporting()
touch11.enable_reporting()
touch12.enable_reporting()
touch13.enable_reporting()
touch14.enable_reporting()
touch15.enable_reporting()
touch16.enable_reporting()

# 각 센서의 상황을 입력받기 위해 라이브러리에서 필요로 하는 코드


class BeYerage:
    def __init__(self):
        with open("/home/pi/Desktop/Raspberry/forconnect7.txt", "r") as f:
            x = list(f.readlines())
            xy_name = []
            for i in range(0,len(x)):
                x_name = list(x[i].split(','))
                xy_name.extend(x_name)
                self.xy_name = xy_name
            print(xy_name)
    
    def start(self):
        
        start = time.time()
        b_number = 0
        while True:
            if time.time()- start > 60:
                # 시현용 시간이기에 600 정도가 적당함.
                # print("time :", time.time() - start)
                
                testtts.StartSpeak('서버로 부터 데이터를 갱신하였습니다.')
                connect7.server().server_connect()
                start = time.time()
                
            if touch1.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[1][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','1-1')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch2.read(): #2번
                b_name = self.xy_name[2][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','1-2')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch3.read(): #3번
                b_name = self.xy_name[3][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','1-3')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch4.read(): #4번
                b_name = self.xy_name[4][6:-2]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','1-4')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch5.read(): #5번
                b_name = self.xy_name[6][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','2-1')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                print("음료를 선택하세요")
                time.sleep(1)
                
                # continue

            elif touch6.read(): #6번
                b_name = self.xy_name[7][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','2-2')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch7.read(): #7번
                b_name = self.xy_name[8][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','2-3')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch8.read(): #8번
                b_name = self.xy_name[9][6:-2]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','2-4')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch9.read(): #9번
                b_name = self.xy_name[11][6:-1]
                print(b_name)
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','3-1')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch10.read(): #10번
                b_name = self.xy_name[12][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','3-2')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch11.read(): #11번
                b_name = self.xy_name[13][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','3-3')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch12.read(): #12번
                b_name = self.xy_name[14][6:-2]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','3-4')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch13.read(): #13번
                b_name = self.xy_name[16][6:-1]
                print(b_name)
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin2.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','4-1')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch14.read(): #14번
                b_name = self.xy_name[17][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin2.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','4-2')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch15.read(): #15번
                b_name = self.xy_name[18][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin2.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','4-3')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            elif touch16.read(): #16번
                b_name = self.xy_name[19][6:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin2.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                test0330.Bname('The location is','4-4')
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            else:
                # 핀에 출력값으로 0을 주면 led 불이 꺼집니다.     
                led_builtin.write(0)
                led_builtin2.write(0)
                # print('한 번 눌러봐')
                test0330.Brest()
            time.sleep(0.2)
while True:
    testtts.StartSpeak('터치 센서 감지를 시작합니다.')
    print(BeYerage().start())