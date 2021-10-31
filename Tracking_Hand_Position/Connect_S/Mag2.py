import pyfirmata
import time
import TTS_gtts_2 as TTS_gtts

board = pyfirmata.Arduino('/COM6')
led_builtin = board.get_pin('d:13:o')
touch = board.get_pin('d:10:i')
magnetic = board.get_pin('d:8:i')
it = pyfirmata.util.Iterator(board)
it.start()
touch.enable_reporting()
magnetic.enable_reporting()



start_msg = '객체인식을 통한 음료 안내를 시작합니다.'
restart_msg = '객체인식을 통한 음료 안내를 다시 시작합니다.'
exit_msg = '객체인식을 통한 음료 안내를 종료합니다.'
touch_msg = '버튼을 눌러주세요'
touch_start = 0 

while True:

    if touch.read():
        print(start_msg)
        TTS_gtts.speak(start_msg)
        #touch_start = time.time()
        # 핀에 출력값으로 1을 주면 led 불이 켜집니다. 
        led_builtin.write(1)
        time.sleep(1)
        continue

    if magnetic.read():
        print(magnetic.read())
        continue
        
    else:
        # 핀에 출력값으로 0을 주면 led 불이 꺼집니다.     
        led_builtin.write(0)
        #print('mag off')
