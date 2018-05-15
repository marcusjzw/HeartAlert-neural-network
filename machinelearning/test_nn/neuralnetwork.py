from keras.models import Sequential
from keras.layers import Dense
import numpy

# random seed for reproducibility
numpy.random.seed(7)

# load dataset
dataset = numpy.loadtxt("pima-indians-diabetes.csv", delimiter = ",")

# split into input and output variables from splicing csv data
inputs = dataset[:,0:8] # take the eight input parameters into the input layer
outputs = dataset[:,8] # one output, the outcome (?)

# create model, add dense layers one by one specifying activation function
model = Sequential() # defining layers left to right (input, hidden, output)
# 'dense' = every neuron is connected to every other neuron in the next layer
model.add(Dense(12, input_dim=8, activation='relu')) # 8 input dimensions, 12 neurons
# relu = squishing weights in between the 0 to 1 domain and passing to next layer. other methods: sigmoid, tanh
model.add(Dense(15, activation='relu')) # 15 neurons
model.add(Dense(8, activation='relu'))
model.add(Dense(10, activation='relu'))
model.add(Dense(1, activation='sigmoid')) # output layer, sigmoid for optimal shape to get 'definite' answer

# each 'forward and bakcward pass' is known as an epoch
# forward pass = run through the layers, come up with prediction
# backward pass = adjust according to cost function discrepancy (gradient descent)
model.compile(loss="binary_crossentropy", optimizer="adam", metrics=['accuracy'])
# binary crossentropy for a cost function which is binary, for multiple output states us
# categorical crossentropy
# adam = method for gradient descent, adam is an effective approximation

# call function to fit to the data (Training the network)
model.fit(inputs, outputs, epochs = 10, batch_size=10) # what is batch_size?

# evaluation of model
scores = model.evaluate(inputs, outputs)
print("\n %s: %.2f%%" % (model.metrics_names[1], scores[1]*100))