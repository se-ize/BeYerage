with open("forconnect1.txt", "w") as f:
    f.write("Life is too short, you need BeYerage\n")

with open("forconnect1.txt", "a") as f:
    f.write("id:1, name:펩시, price:1000, type:CAN, size:250\n")
    f.write("id:5, name:사이다, price:1000, type:CAN, size:250\n")
    f.write("id:6, name:웰치스, price:1000, type:CAN, size:250\n")
    f.write("id:10, name:콜라, price:1000, type:CAN, size:250\n")
    f.write("id:27, name:환타, price:1000, type:CAN, size:250\n")

with open("forconnect1.txt", "r") as f:
    # data = list(f.read())
    x = list(f.readlines())
    # print("read")
    # print(data)
    print("readlines를 이용하였음")
    print(x)

    t = 0
    b_name = input('검색할 음료의 이름을 서술하라\n')
    print("줄로 나뉜 list 에서 각 용소마다 나눔")
    for i in range(0,len(x)):
        x_name = list(x[i].split(', '))
        #print(x_name)
        k = 'name:' + b_name
        if x_name[1] ==  k:
            print(x_name)
            print(" 선택하신 음료는 " + x_name[1][5:] + " 입니다. 또한 해당 음료의 가격은 " + x_name[2][6:] + " 원, 해당 음료의 크기는 " + x_name[4][5:] + "mm 입니다.")
            t += 1
        
    if t == 0:
        print("입력하신 음료는 없습니다.")
    # print(data)
