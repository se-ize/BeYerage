with open("forconnect1.txt", "w") as f:
    f.write("Life is too short, you need BeYerage\n")

with open("forconnect1.txt", "a") as f:
    f.write("id:1, name:펩시, price:1000, type:CAN, size:250\n")
    f.write("id:5, name:사이다, price:1000, type:CAN, size:250\n")
    f.write("id:6, name:웰치스, price:1000, type:CAN, size:250\n")
    f.write("id:10, name:콜라, price:1000, type:CAN, size:250\n")
    f.write("id:27, name:환타, price:1000, type:CAN, size:250\n")

with open("forconnect1.txt", "r") as f:
    data = f.read()
    print(data)
    list_data_o = data.split("\n")
    #list_data = data.split(", ")    
    # str을 공란()으로 쪼갬 
    print(list_data_o)
    #print(list_data)
    print(len(list_data_o))
list_data_result = []
for i in range(0,len(list_data_o)):
    list_data_o_edit = list_data_o[i].split(", ")
    print(list_data_o_edit) 
    list_data_result.append(list_data_o_edit)

print("결과물")
print(list_data_result)
# print(list_data_result.index(['name:환타']))
    
# f = open("forconnect1.txt", 'r')
# data = f.read()
# print(data)
# f.close()