import requests
import time
start = time.time()

import pandas as pd
import numpy as np

def server_connect():
    try:
        r =requests.get('http://ec2-3-36-89-34.ap-northeast-2.compute.amazonaws.com:3333/beveragesWithLocInfo')

        # print(r.status_code)
        string = r.text
        characters = "[]"

        pan = pd.DataFrame.from_dict(r.json())
        #print(pan)
        #pan = pan.set_index(["row","column"])
        #pan = pan.set_index('id')
        pan = pan.sort_values(by=['row', 'column'] ,ascending=True)
        #pan.to_csv('connect_data_pandas.txt')
        #print(pan)
        string = pan.to_json(orient = 'records',force_ascii=False)
        #print(string)

        string = ''.join( x for x in string if x not in characters)

        if string.find("},{") != -1:
            string1 = string.replace("{","")
            string1 = string1.replace("},","\n")
            string1 = string1.replace("}","")
        else:
            print("동일한 부분이 존재하지 않습니다.")

        with open("connect_data.txt", "w",encoding="UTF-8") as f:
            f.write(string1)
            #print(string1)

        #print("time :", time.time() - start)
        # 현재시각 - 시작시간 = 실행 시간

    except Exception as ex:
        print("서버 연결에 문제가 발생하였습니다.")
        print(ex)

server_connect()