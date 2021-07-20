import requests

r =requests.post('http://15.165.63.211:3333/beverageLocationInfoByJson/raspberry-pi')
# r1 =requests.post('http://15.165.63.211:3333/beverageInfoByJson/raspberry-pi')

print(r.status_code)
print(r.text)
json_array = r.text
# print(r1.status_code)
# print(r1.text)

store_list = []

count = 1
for item in json_array:
    store_details = {"id":None, "c1":None, "c2":None, "c3":None ,"c4":None}
    store_details['id'] = item['id']
    store_details['1열'] = item['c1']
    store_details['2열'] = item['c2']
    store_details['3열'] = item['c3']
    store_details['4열'] = item['c4']
    store_list.append(store_details)
    count += 1

print(store_list)