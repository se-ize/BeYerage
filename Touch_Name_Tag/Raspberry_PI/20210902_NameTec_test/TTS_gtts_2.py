from gtts import gTTS
import pygame

def speak(text):
    text = '에' + text
    tts = gTTS(text=text,lang='ko')
    filename ='voice0526.mp3'
    tts.save(filename)

    #### vlc ########
    # p = vlc.MediaPlayer(filename)
    # p.play()

    #### pyglet ######
    # song = pyglet.media.load('voice0429.mp3')
    # song.play()
    # pyglet.app.run()
    # time.sleep(7)

    ##### pygame 3#####

    music_file = filename   # mp3 or mid file


    freq = 24000    # sampling rate, 44100(CD), 16000(Naver TTS), 24000(google TTS)
    bitsize = -16   # signed 16 bit. support 8,-8,16,-16
    channels = 1    # 1 is mono, 2 is stereo
    buffer = 2048   # number of samples (experiment to get right sound)

    # default : pygame.mixer.init(frequency=22050, size=-16, channels=2, buffer=4096)
    pygame.mixer.init(freq, bitsize, channels, buffer)
    pygame.mixer.music.load(music_file)
    pygame.mixer.music.play()

    clock = pygame.time.Clock()
    while pygame.mixer.music.get_busy():
        clock.tick(30)
    pygame.mixer.quit()    
    
#speak("선택하신 음료는 레쓰비 입니다. 또한 해당 음료의 가격은 900 원, 해당 음료의 크기는 250 ml 입니다.")