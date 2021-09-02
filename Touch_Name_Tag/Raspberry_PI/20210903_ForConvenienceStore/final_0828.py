
import time
import pyfirmata
import testtts3
import connect10
import connect7
import testtts
import server_connect_0828

# 아두이노에 연결합니다. 

# board2 = pyfirmata2.Arduino('/dev/serial/by-path/platform-fd500000.pcie-pci-0000:01:00.0-usb-0:1.1:1.0')
board1 = pyfirmata.ArduinoMega('/dev/serial/by-path/platform-fd500000.pcie-pci-0000:01:00.0-usb-0:1.1:1.0')

# 디지털(digital) 핀(pin) 13번을 출력(output) 모드로 가져옵니다. 
# 아두이노 메가

led_builtin = board1.get_pin('d:13:o')
touch1 = board1.get_pin('d:24:i')
touch2 = board1.get_pin('d:25:i')
touch3 = board1.get_pin('d:26:i')
touch4 = board1.get_pin('d:27:i')
touch5 = board1.get_pin('d:28:i')
touch6 = board1.get_pin('d:29:i')
touch7 = board1.get_pin('d:30:i')
touch8 = board1.get_pin('d:31:i')


'''
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
'''
it = pyfirmata.util.Iterator(board1)
it.start()

touch1.enable_reporting()
touch2.enable_reporting()
touch3.enable_reporting()
touch4.enable_reporting()
touch5.enable_reporting()
touch6.enable_reporting()
touch7.enable_reporting()
touch8.enable_reporting()

# 각 센서의 상황을 입력받기 위해 라이브러리에서 필요로 하는 코드


class BeYerage:
    def __init__(self):
        with open("/home/pi/Desktop/Raspberry/beverage_story.txt", "r") as f:
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
        testtts3.speak(' 지금 부터 터치센서 음성 안내를 시작합니다')
        while True:
            if time.time()- start > 60:
                # 시현용 시간이기에 600 정도가 적당함.
                # print("time :", time.time() - start)
                
                testtts.StartSpeak('서버로 부터 데이터를 갱신하였습니다.')
                server_connect_0828.server().server_connect()
                start = time.time()
                with open("/home/pi/Desktop/Raspberry/beverage_story.txt", "r") as f:
                    x = list(f.readlines())
                    xy_name = []
                    for i in range(0,len(x)):
                        x_name = list(x[i].split(','))
                        xy_name.extend(x_name)
                        self.xy_name = xy_name
                    print(xy_name)
                
            if touch1.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[1][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            if touch2.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[8][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다.   g
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue
            if touch3.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[15][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue
            if touch4.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[22][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue
            if touch5.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[29][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue
            if touch6.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[36][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue
            if touch7.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[43][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue
            if touch8.read(): #1번
                print('눌렸음')
                b_name = self.xy_name[50][8:-1]
                # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
                led_builtin.write(1)
                # time.sleep(1)
                print('해당 제품은 '+ b_name +'입니다.')
                speakstory = connect10.connect(b_name)
                print(speakstory)
                # 한글을 영어로 표현하는 걸 어떻게 자동으로 설정하지??? => 우선은 공통 대체 언어이용
                testtts3.speak(speakstory)
                time.sleep(1)
                continue

            else:
                # 핀에 출력값으로 0을 주면 led 불이 꺼집니다.     
                led_builtin.write(0)
                # print('한 번 눌러봐')

            time.sleep(0.2)

            '''

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
            '''
while True:
    # testtts.StartSpeak('터치 센서 감지를 시작합니다.')
    print('시작합니다')
    print(BeYerage().start())