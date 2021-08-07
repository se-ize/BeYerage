import tensorflow as tf
import numpy as np
import random

bp2 = np.loadtxt('first_dataset_1.csv', delimiter=',', dtype=np.float32)
bp = np.loadtxt('first_dataset_2.csv', delimiter=',', dtype=np.float32)


#(x_train, y_train),(x_test, y_test) = bp.load_data()
#x_train, x_test = x_train / 255.0, x_test / 255.0
x_train = bp[:, 0:-1]
y_train = bp[:, [-1]]
x_test = bp2[:, 0:-1]
y_test = bp2[:, [-1]]
# for i in range(0,2000):
#     x_test1 = random.choice(x_train)
#     y_test1 = random.choice(y_train)
#     print(x_test , y_test)

#     x_test.append(x_test1)
#     x_test.append(y_test1)

model = tf.keras.models.Sequential([
#   tf.keras.layers.Flatten(),
  tf.keras.layers.Dense(512, activation=tf.nn.relu),
  tf.keras.layers.Dropout(0.6),
  tf.keras.layers.Dense(256, activation=tf.nn.relu),
  tf.keras.layers.Dropout(0.5),
  tf.keras.layers.Dense(512, activation=tf.nn.relu),
  tf.keras.layers.Dropout(0.6),
  tf.keras.layers.Dense(128, activation=tf.nn.relu),
  tf.keras.layers.Dropout(0.6),
  tf.keras.layers.Dense(64, activation=tf.nn.relu),
  tf.keras.layers.Dense(4, activation=tf.nn.softmax) 
])
 
model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])

model.fit(x_train, y_train, epochs=10)
model.evaluate(x_test, y_test)

model.save_weights('bp2_checkpoint')