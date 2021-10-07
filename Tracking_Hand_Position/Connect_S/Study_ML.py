import tensorflow as tf
import numpy as np
import random
from datetime import datetime

bp = np.loadtxt('Train_Dataset_918.csv', delimiter=',', dtype=np.float32)
bp2 = np.loadtxt('Test_Dataset_918.csv', delimiter=',', dtype=np.float32)
ML_Name = 'final_checkpoint_'+ str(datetime.today().month) + str(datetime.today().day)

#(x_train, y_train),(x_test, y_test) = bp.load_data()
#x_train, x_test = x_train / 255.0, x_test / 255.0
x_train = bp[:, 0:-1]
y_train = bp[:, [-1]]
x_test = bp2[:, 0:-1]
y_test = bp2[:, [-1]]
print(x_train[:8],y_train[:8])
print(x_test[:8],y_test[:8])

X = tf.keras.layers.Input(shape=[8])
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