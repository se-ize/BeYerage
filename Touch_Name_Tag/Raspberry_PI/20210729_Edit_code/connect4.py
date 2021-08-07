import requests

r = [0,1]
r0 = requests.post('http://15.165.63.211:3333/beverageLocationInfoByJson/raspberry-pi')
r1 = requests.post('http://15.165.63.211:3333/beverageInfoByJson/raspberry-pi')

for i in r:
    if r+i.status_code == 200:    
        print(r.status_code)
        print(r+i + '이 정상적으로 연결되었습니다')
        print(r+i.text)