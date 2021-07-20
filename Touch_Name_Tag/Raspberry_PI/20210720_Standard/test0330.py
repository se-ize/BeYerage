from signal import signal, SIGTERM, SIGHUP, pause
from rpi_lcd import LCD
import time

lcd = LCD()
# start = time.time()
# end = time + timeout
Bname, Bname2 = 'a','b'


def safe_exit(signum, frame, Bname):
    exit(1)

    signal(SIGTERM, safe_exit)
    signal(SIGHUP, safe_exit)
'''
try:
    lcd.text('BeYerage Team, ', 1)
    lcd.text('Beverage :', 2)
    pause()

except KeyboardInterrupt:
    pass
finally:
    lcd.clear()
'''
def Bname(Bname, Bname2):
    lcd.text('BeYerage Team, ', 1)
    lcd.text('Beverage Information', 2)
    lcd.text(Bname, 3)
    lcd.text(Bname2, 4)
    # if time.sleep(3):

def Brest():
    lcd.clear()