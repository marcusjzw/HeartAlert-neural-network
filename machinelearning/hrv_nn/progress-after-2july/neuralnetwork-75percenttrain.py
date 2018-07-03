from keras.models import Sequential
from keras.models import load_model
from keras.models import model_from_json
from keras.optimizers import SGD
from keras.layers import Dense
import numpy
import json

numpy.random.seed(7)

# load dataset (75% one)
dataset = numpy.loadtxt("training_data_75percent.csv", delimiter = ",")

# split into input and output variables from splicing csv data
inputs = dataset[:,1:5] # take the 4 input params (element 1 to 4)
outputs = dataset[:,5] # one output, the outcome  
# create model, add dense layers one by one specifying activation function
model = Sequential() # defining layers left to right (input, hidden, output)
# 'dense' = every neuron is connected to every other neuron in the next layer
model.add(Dense(12, input_dim=4, activation='relu')) # 4 input dimensions
model.add(Dense(9, activation='relu'))
# relu = squishing weights in between the 0 to 1 domain and passing to next layer. other methods: sigmoid, tanh
model.add(Dense(1, activation='sigmoid')) # output layer, sigmoid for optimal shape to get 'definite' answer

# each 'forward and bakcward pass' is known as an epoch
# forward pass = run through the layers, come up with prediction
# backward pass = adjust according to cost function discrepancy (gradient descent)
# model.compile(loss="binary_crossentropy", optimizer="adam", metrics=['accuracy'])
model.compile(loss = "binary_crossentropy", optimizer="adam", metrics=['accuracy'])
# binary crossentropy for a cost function which is binary, for multiple output states us
# categorical crossentropy
# adam = method for gradient descent, adam is an effective approximation

# call function to fit to the data (Training the network)
model.fit(inputs, outputs, epochs = 1000, batch_size=5)
# evaluation of model
scores = model.evaluate(inputs, outputs)
print("\n %s: %.2f%%" % (model.metrics_names[1], scores[1]*100))

# save model (complete)
model.save('vt_classification_model_complete75PERCENT.h5')
# save model architecture only
json_string = model.to_json()
with open('vt_classification_model_architecture75PERCENT.txt', 'w+') as outfile:
    json.dump(json_string, outfile)

# save model weights only
model.save_weights('vt_classification_model_weights75PERCENT.h5')

# try predictions
predictions = model.predict(inputs)
# round predictions (due to sigmoid as output layer)
rounded_predictions = [round(inputs[0]) for inputs in predictions]
print(rounded_predictions)


