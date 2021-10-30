from gtts import gTTS
# now you can import vlc
#import vlc
import os
import time
import playsound
import time, sys
from pygame import mixer
from datetime import datetime
# pygame.init()


def speak(text):
    tts = gTTS(text=text,lang='ko')

    filename ='Voice'+ str(datetime.today().month) + str(datetime.today().day) +'.mp3'

    tts.save(filename)
    playsound.playsound('C:/Users/Woojin/Desktop/ProBono/Tracking_Hand_Position/Connect_S/' + filename)
    os.remove(filename)
    #playsound 는 리룩스에서는 작동하지않아요.

    #p = vlc.MediaPlayer(filename)
    #p.play()
    #time.sleep(5)
    # mixer.init()
    # mixer.music.load(filename) # you may use .mp3 but support is limited
    # mixer.music.play()
    # mixer.stop()
    #sound = mixer.Sound(filename)
    #sound.play()
    time.sleep(6)
    
    # print('잘 다음으로 넘어가나요?')
    # os.system('/home/pi/Desktop/arduino/' + filename)

def speakend(filename):
    os.remove(filename)


#speak(" 객체인식을 통한 음료 안내를 종료합니다. ")
#speak("해당 음료는 스프라이트 CAN, 위치는 1행 1열, 크기는 240 ml, 가격은 1200원 입니다.")