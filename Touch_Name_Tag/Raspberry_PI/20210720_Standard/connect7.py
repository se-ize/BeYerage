import requests
import time
start = time.time()

class server():
    def server_connect(self):
        try:
            r =requests.post('http://15.165.63.211:3333/beverageLocationInfoByJson/raspberry-pi')
            r1 =requests.post('http://15.165.63.211:3333/beverageInfoByJson/raspberry-pi')

            # print(r.status_code)
            # print(r.text)
            mstring = r1.text
            string = r.text
            characters = "[]"

            string = ''.join( x for x in string if x not in characters)
            mstring =''.join( x for x in mstring if x not in characters)

            if string.find("},{") != -1:
                
                string1 = string.replace("{","")
                string1 = string1.replace("},","\n")
                string1 = string1.replace("}","")
                mstring1 = mstring.replace("{","")
                mstring1 = mstring1.replace("},","\n")
                mstring1 = mstring1.replace("}","")
            else:
                print("동일한 부분이 존재하지 않습니다.")
            with open("forconnect10.txt", "w") as f:
                print(string1)
                f.write(string1)
            with open("forconnect11.txt", "w") as f:
                f.write(mstring1)
                print("음료 안내")
                print(mstring1)

            print("time :", time.time() - start)  
            # 현재시각 - 시작시간 = 실행 시간
        except Exception as ex:
            print("서버 연결에 문제가 발생하였습니다.")
            print(ex)