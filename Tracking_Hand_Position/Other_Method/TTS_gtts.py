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
    playsound.playsound('C:/Users/Woojin/Desktop/ProBono/Tracking_Hand_Position/Other_Method/' + filename)
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
# speak(" 마음이 답답할 땐 청량하게! , 해당 제품은 사이다 입니다. ")

def speakend(filename):
    os.remove(filename)


#speak(" 올해는 도시마다 다시 즐거움이 켜질거에요! 해당 제품은 코카콜라입니다. ")
