import requests
import time
start = time.time()
 
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
    # print('\n음료 위치 정보\n'+string1+'\n')
    # print('음료 상세 정보\n'+mstring1+'\n')
else:
    print("동일한 부분이 존재하지 않습니다.")

# print(r1.status_code)
# print(r1.text)
with open("forconnect7.txt", "w") as f:
    print("음료 위치 ")
    print(string1)
    f.write(string1)
    print("")
with open("forconnect8.txt", "w") as f:
    # f.write(mstring1)
    f.write(mstring1)   
    print("음료 상세 정보 ") 
    print(mstring1)
    


print("time :", time.time() - start)  # 현재시각 - 시작시간 = 실행 시간