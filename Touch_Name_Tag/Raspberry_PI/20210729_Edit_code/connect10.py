def connect(B_name):
    with open("/home/pi/Desktop/Raspberry/forconnect8.txt", "r") as f:
        # data = list(f.read())
        x = list(f.readlines())
        # print("read")
        # print(data)
        #print("readlines를 이용하였음")
        #print(x)

        t = 0
        b_name = B_name
        #print("줄로 나뉜 list 에서 각 요소마다 나눈다")
        for i in range(0,len(x)):
            x_name = list(x[i].split(','))
            # print(x[i])
            # print(x_name)
            k = '"name":"' + b_name +'"'
            if x_name[1] ==  k:
                # print(x_name)
                # print(" 선택하신 음료는 " + x_name[1][8:-1] + " 입니다. 또한 해당 음료의 가격은 " + x_name[4][7:] + " 원, 해당 음료의 크기는 " + x_name[4][7:] + "mm 입니다.")
                t += 1
                return(" 선택하신 음료는 " + x_name[1][8:-1] + " 입니다. 또한 해당 음료의 가격은 " + x_name[2][6:] + " 원, 해당 음료의 크기는 " + x_name[4][7:] + "ml 입니다.")              
        if t == 0:
            # print("오류가 발생하였습니다. 다시 선택해주세요.")
            return("오류가 발생하였습니다. 다시 선택해주세요.")
            # print(data)