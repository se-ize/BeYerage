import tensorflow as tf
import numpy as np
import random
import pandas as pd
from datetime import datetime

#Train_Dataset_111_seo.csv
#Test_Dataset_111_seo.csv
#bp = np.loadtxt('train.csv', delimiter=',', dtype=np.float32)
#bp2 = np.loadtxt('test.csv', delimiter=',', dtype=np.float32)

bp = pd.read_csv('Test_Dataset_115.csv')
bp2 = pd.read_csv('test_115.csv')
ML_Name = 'final_checkpoint_'+ str(datetime.today().month) + str(datetime.today().day)

bp2=bp2.iloc[np.random.permutation(bp2.index)].reset_index(drop=True)
bp=bp.iloc[np.random.permutation(bp.index)].reset_index(drop=True)

#(x_train, y_train),(x_test, y_test) = bp.load_data()
#x_train, x_test = x_train / 255.0, x_test / 255.0
x_train = bp.iloc[:, 0:-1]
y_train = bp.iloc[:, [-1]]
x_test = bp2.iloc[:, 0:-1]
y_test = bp2.iloc[:, [-1]]
#print(x_train[:8],y_train[:8])
#print(x_test[:8],y_test[:8])

X = tf.keras.layers.Input(shape=[9])
H = tf.keras.layers.Dense(10)(X)
H = tf.keras.layers.BatchNormalization()(H)
H = tf.keras.layers.Activation('swish')(H)
H = tf.keras.layers.Dense(10)(H)
H = tf.keras.layers.BatchNormalization()(H)
H = tf.keras.layers.Activation('swish')(H)
H = tf.keras.layers.Dense(10)(H)
H = tf.keras.layers.BatchNormalization()(H)
H = tf.keras.layers.Activation('swish')(H)
Y = tf.keras.layers.Dense(1)(H)

model = tf.keras.models.Model(X, Y)
model.compile(loss='mse')


model.summary()

model.fit(x_train, y_train, epochs=200,verbose=0)
model.fit(x_train, y_train, epochs=100)


print("예측가정:")
print(model.predict(x_test[:8]))
print("예측결과:")
print(y_test[:8])


print(model.get_weights())

model.save_weights(ML_Name)