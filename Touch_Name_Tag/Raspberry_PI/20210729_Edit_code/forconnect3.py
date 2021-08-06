with open("forconnect8.txt", "r") as f:
    # data = list(f.read())
    x = list(f.readlines())
    # print("read")
    # print(data)
    print("readlines를 이용하였음")
    print(x)

    t = 0
    b_name = input('검색할 음료의 이름을 서술하라\n')
    print("줄로 나뉜 list 에서 각 요소마다 나눈다")

    xy_name = []
    for i in range(0,len(x)):
        x_name = list(x[i].split(','))
        xy_name.extend(x_name)
        
        #print(x_name)
        k = '"name":"' + b_name +'"'
        if x_name[1] ==  k:
            print(x_name)
            print(xy_name)
            print(" 선택하신 음료는 " + x_name[1][8:-1] + " 입니다. 또한 해당 음료의 가격은 " + x_name[2][8:] + " 원, 해당 음료의 크기는 " + x_name[4][7:] + " mm 입니다.")
            t += 1
        
    if t == 0:
        print("입력하신 음료는 없습니다.")
    # print(data)