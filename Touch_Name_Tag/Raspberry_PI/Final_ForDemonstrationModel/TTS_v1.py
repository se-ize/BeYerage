import speech_recognition as sr
from gtts import gTTS
import os
import time
import playsound
import pygame

def StartSpeak(text):
    tts = gTTS(text=text,lang='ko')
    filename ='/home/pi/Desktop/Raspberry/voice2.mp3'
    tts.save(filename)
    # playsound.playsound('/home/pi/Desktop/arduino/' + filename)
    # playsound 는 리룩스에서는 작동하지않아요.
    
    pygame.mixer.init()
    pygame.mixer.music.load(filename)
    pygame.mixer.music.play()
    while pygame.mixer.music.get_busy() == True:
        continue

    # os.system('/home/pi/Desktop/arduino/' + filename)

# StartSpeak("테스트를 진행합니다 ")

