import time
import connect7
start = time.time()

while True:
    if time.time()-start > 10:
        print("time :", time.time() - start)
        connect7.server().server_connect()
        start = time.time()