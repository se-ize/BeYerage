import time
def waitUntil(condition, output): #defines function
    wU = True
    while wU == True:
        if condition: #checks the condition
            output
            wU = False
        time.sleep(60) #waits 60s for preformance

waitUntil(Cookies >= 0, eatCookies()) #runs function (output MUST be another function)