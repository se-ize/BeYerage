import requests
import time
import pandas as pd


class server():
    def server_connect(self):
        try:
            r =requests.get('http://ec2-3-36-89-34.ap-northeast-2.compute.amazonaws.com:3333/beveragesWithLocInfo')

            # print(r.status_code)
            # print(r.text)
            pan = pd.DataFrame.from_dict(r.json())
            # print(pan)
            
            string = r.text
            characters = "[]"
            pan = pd.DataFrame.from_dict(r.json())
            pan = pan.sort_values(by=['row','column'],ascending = True)
            string = pan.to_json(orient ='records',force_ascii=False)

            string = ''.join( x for x in string if x not in characters)

            # print(string)

            if string.find("},{") != -1:
                string1 = string.replace("{","")
                string1 = string1.replace("},","\n")
                string1 = string1.replace("}","")
            else:
                print("동일한 부분이 존재하지 않습니다.")

            

            with open("/home/pi/Desktop/Raspberry/beverage_story.txt", "w") as f:
                print(string1)
                f.write(string1)  

        except Exception as ex:
            print("서버 연결에 문제가 발생하였습니다.")
            print(ex)

# server().server_connect()
