import serial
import time
import TTS_gtts_2 as TTS_gtts


arduino = serial.Serial('COM10',9600, timeout=1)
print("아두이노와 통신 시작")

'''
arduino = serial.Serial(
    port = '/dev/ttyACM0',
    baudrate = 115200,
    bytesize = serial.EIGHTBITS,
    parity = serial.PARITY_NONE,
    stopbits = serial.STOPBITS_ONE,
    timeout = 5,
    xonxoff = False,
    rtscts = False,
    dsrdtr = False,
    writeTimeout = 2
)
'''

a = b'1\r\n'
result = 'no'
start_msg = '객체인식을 통한 음료 안내를 시작하겠습니다.'
exit_msg = '객체인식을 통한 음료 안내를 종료하겠습니다.'

TTS_gtts.speak(start_msg)

while True:
    try:
        data = arduino.readline()
        if data:
            if data == a:
                if result == 'no':
                    result = 'contact'
                    print('contact')
                    TTS_gtts.speak(start_msg)
                    continue
                else:
                    result = 'contact'
                    print('contact')
            else:
                if result == 'contact':
                    result = 'no'
                    print('no')
                    TTS_gtts.speak(exit_msg)
                    continue
                else:
                    result = 'no'
                    print('no')
    except Exception as e:
        print(e)
        arduino.close()