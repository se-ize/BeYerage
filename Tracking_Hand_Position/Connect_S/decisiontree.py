import tensorflow as tf
import numpy as np
from datetime import datetime
import pandas as pd
from sklearn.tree import DecisionTreeClassifier
import pickle

#Train_Dataset_111_seo.csv
#Test_Dataset_111_seo.csv
bp = pd.read_csv('train.csv')
bp2 = pd.read_csv('test.csv')

bp2=bp2.iloc[np.random.permutation(bp2.index)].reset_index(drop=True)
bp=bp.iloc[np.random.permutation(bp.index)].reset_index(drop=True)

#(x_train, y_train),(x_test, y_test) = bp.load_data()
#x_train, x_test = x_train / 255.0, x_test / 255.0
x_train = bp.iloc[:, 0:-1]
y_train = bp.iloc[:, [-1]]
x_test = bp2.iloc[:, 0:-1]
y_test = bp2.iloc[:, [-1]]
print(x_train[:8],y_train[:8])
print(x_test[:8],y_test[:8])

model = DecisionTreeClassifier(random_state=42)
model.fit(x_train,y_train)

print("예측가정:")
print(model.predict(x_test[:8]))
print("예측결과:")
print(y_test[:8])


pickle.dump(model, open('tree', 'wb'))