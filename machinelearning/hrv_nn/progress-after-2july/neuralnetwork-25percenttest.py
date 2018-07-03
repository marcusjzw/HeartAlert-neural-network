from keras.models import Sequential
from keras.models import load_model
import numpy

numpy.random.seed(7)

# load dataset
dataset = numpy.loadtxt("testing_data_25percent.csv", delimiter = ",")
inputs = dataset[:,1:5] # take the 4 input params (element 1 to 4)
outputs = dataset[:,5] # one output, the outcome  

model = load_model('vt_classification_model_complete75PERCENT.h5')
predictions = model.predict_on_batch(inputs)
rounded_predictions = [int(round(inputs[0])) for inputs in predictions]

print('Predicted outcomes (0 for normal sinus rhythm, 1 for ventricular tachycardia onset: ')
print(rounded_predictions)
print('Actual outcomes: ')
print(outputs)
no_correct = 0
for i in range (0, len(outputs)):
    print(outputs[i])
    print(rounded_predictions[i])
    if (outputs[i] == rounded_predictions[i]):
        no_correct += 1

percentage_correct = 100 * (float(no_correct) / float(len(outputs)))
print("Total recall correct: %.2f%%" % (percentage_correct))


