from keras.models import Sequential
from keras.layers import Dense
from sklearn.model_selection import StratifiedKFold
import numpy
for hidden_layer_neurons in range(10, 25):
    # random seed for reproducibility
    seed = 7
    numpy.random.seed(seed)
    # BEST SO FAR: 12, 9
    # load dataset
    dataset = numpy.loadtxt("all_data_onsets.csv", delimiter = ",")

    # split into input and output variables from splicing csv data
    inputs = dataset[:,1:5] # take the 4 input params (element 1 to 4)
    outputs = dataset[:,5] # one output, the outcome
    # define 4-fold cross validation test harness
    kfold = StratifiedKFold(n_splits=4, shuffle=True, random_state=seed)
    cvscores = []
    for train, test in kfold.split(inputs, outputs):
            
        # create model, add dense layers one by one specifying activation function
        model = Sequential() # defining layers left to right (input, hidden, output)
        # 'dense' = every neuron is connected to every other neuron in the next layer
        model.add(Dense(hidden_layer_neurons, input_dim=4, activation='relu')) # 4 input dimensions
        model.add(Dense(hidden_layer_neurons-3, activation='relu'))
        # relu = squishing weights in between the 0 to 1 domain and passing to next layer. other methods: sigmoid, tanh
        model.add(Dense(1, activation='sigmoid')) # output layer, sigmoid for optimal shape to get 'definite' answer

        # each 'forward and bakcward pass' is known as an epoch
        # forward pass = run through the layers, come up with prediction
        # backward pass = adjust according to cost function discrepancy (gradient descent)
        model.compile(loss="binary_crossentropy", optimizer="adam", metrics=['accuracy'])
        # binary crossentropy for a cost function which is binary, for multiple output states us
        # categorical crossentropy
        # adam = method for gradient descent, adam is an effective approximation

        # call function to fit to the data (Training the network)
        model.fit(inputs[train], outputs[train], epochs = 100, batch_size=25)
        print("Neurons: %s", hidden_layer_neurons)
        # evaluation of model
        scores = model.evaluate(inputs[test], outputs[test])
        print("\n %s: %.2f%%" % (model.metrics_names[1], scores[1]*100))
        cvscores.append(scores[1]*100)

    print("%.2f%% (+/- %.2f%%)" % (numpy.mean(cvscores), numpy.std(cvscores)))
    with open("heatmapoutput_kfoldandhiddenlayer.txt", "a+") as text_file:
        text_file.write("\nHidden neurons: %s \nAccuracy:  %.2f%% \nSD: %.2f%%" % (hidden_layer_neurons,
        numpy.mean(cvscores), numpy.std(cvscores)))

