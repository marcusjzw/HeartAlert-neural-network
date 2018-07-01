from keras.models import Sequential
from keras.models import load_model
import numpy

numpy.random.seed(7)

# load dataset
dataset = numpy.loadtxt("testing_data.csv", delimiter = ",")
inputs = dataset[:,0:4] # take the 4 input params (element 1 to 4)
outputs = dataset[:,4] # one output, the outcome  
model = load_model('vt_classification_model_complete.h5')
predictions = model.predict_on_batch(inputs)
rounded_predictions = [int(round(inputs[0])) for inputs in predictions]
print(rounded_predictions)
print(outputs)
