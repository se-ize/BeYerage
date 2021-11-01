def connect(B_name):
    with open("connect_data.txt", "rt",encoding='UTF8') as f:
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
                t += 1
                return(" 선택하신 음료는 " + x_name[1][8:-1] +" "+ x_name[3][8:-1] + " 입니다. 해당 음료의 위치는 " + x_name[5][-1:]+ " 행 " + x_name[6][-2:] + " 열 이며 가격은 " + x_name[2][8:] + " 원, 크기는 " + x_name[4][7:] + "ml 입니다.")              
        if t == 0:
            return("오류가 발생하였습니다. 다시 선택해주세요.")
            # print(data)

#print(connect('콜라'))