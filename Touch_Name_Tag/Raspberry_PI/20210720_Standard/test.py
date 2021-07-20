import time
import pyfirmata
import test0330
import testtts3

# 아두이노에 연결합니다. 
board = pyfirmata.Arduino('/dev/ttyACM0')

# 디지털(digital) 핀(pin) 13번을 출력(output) 모드로 가져옵니다. 
led_builtin = board.get_pin('d:13:o')
touch8 = board.get_pin('d:8:i')
touch9 = board.get_pin('d:9:i')
touch10 = board.get_pin('d:10:i')
it = pyfirmata.util.Iterator(board)
it.start()
touch8.enable_reporting()
touch9.enable_reporting()
touch10.enable_reporting()
# 각 센서의 상황을 입력받기 위해 라이브러리에서 필요로 하는 코드

while True:
    if touch8.read():
        # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
        led_builtin.write(1)
        # time.sleep(1)
        print('해당 제품은 코카콜라 입니다.')
        test0330.Bname('Coke','1-1')
        testtts3.speak("함께하는 즐거움! 해당 제품은 코카콜라 입니다")
        time.sleep(3)
        continue

    elif touch9.read():
        # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
        led_builtin.write(1)
        # time.sleep(1)
        print('해당 제품은 코카콜라 제로입니다.')
        test0330.Bname('Coke Zero','2-2')
        testtts3.speak("제로로 제로하는! , 해당 제품은 코카콜라 제로 입니다. ")
        time.sleep(3)
        continue

    elif touch10.read():
        # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
        led_builtin.write(1)
        # time.sleep(1)
        print('해당 제품은 사이다입니다.')
        test0330.Bname('Cider','3-3')
        testtts3.speak("마음이 답답할 땐 청량하게! , 해당 제품은 사이다 입니다. ")
        time.sleep(3)
        continue
    else:
        # 핀에 출력값으로 0을 주면 led 불이 꺼집니다.     
        led_builtin.write(0)
        print('한 번 눌러봐')
        test0330.Brest()
    time.sleep(0.2)
