import csv

f = open('write.csv','w', newline='')
wr = csv.writer(f)
wr.writerow([1,'우진', '부산'])
wr.writerow([2,'유진', '서울'])
wr.writerow([3,'채민', '서울'])
 
f.close()