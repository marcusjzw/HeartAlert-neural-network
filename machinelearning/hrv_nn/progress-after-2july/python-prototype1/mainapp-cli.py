# Prototype, comments made where the final solution will have this computation 
# Program workflow:
# 1) Ask for RRI input (in form of .txt file)
# 2) Calculate power spectral density from given RRI
# 3) Send metrics of LF, HF, LF/HF and VLF through trained neural network
# 4) Present classification result
import os
from hrv.classical import frequency_domain
from hrv.utils import open_rri
from keras.models import Sequential
from keras.models import load_model
from io import StringIO
import numpy

def compute_HRV_metrics(rritxt):
    print("CALCULATING...")
    hrvfd = frequency_domain(rri=rritxt, fs=4.0, method='welch',
                             interp_method='cubic', detrend='linear')
    print("HF: %.3f" % hrvfd.get('hf'))
    print("LF: %.3f" % hrvfd.get('lf'))
    print("LF/HF: %.3f" % hrvfd.get('lf_hf'))
    print("VLF: %.3f" % hrvfd.get('vlf'))
    return hrvfd

print("~~~Detecting Onset of Ventricular Tachycardia v0.1~~~")
rri_name = raw_input("Please give the file name of a .txt file containing RRI interval values: ")
actual_output = raw_input("What is the actual output (0 = normal sinus rhythm, 1 = ventricular tachycardia): ")
# In final solution, RRI data is calculated on a HR monitoring device, and streamed to smartphone
# RRI = 60 * (1000/BPM)
try: 
    rri = open_rri(os.getcwd() + '/data/' + rri_name)
    print("Found file " + rri_name)
except:
    print("Could not find file!")

# compute RRI to PSD
hrv_metrics = compute_HRV_metrics(rri)
np2 = numpy.array([[hrv_metrics.get('lf'), hrv_metrics.get('lf_hf'), hrv_metrics.get('vlf'), hrv_metrics.get('hf')]])
print(np2)
print("Sending HRV metrics to neural network...")
model = load_model('/Users/Marcus/git/compsys700-new/machinelearning/hrv_nn/progress-after-2july/saved_models/vt_classification_model_complete75PERCENT.h5')
prediction = model.predict_on_batch(np2)
if (round(prediction) == 1):
    print("The onset of ventricular tachycardia has been detected! ALERT")
else:
    print("Normal sinus rhythm detected")

if (round(prediction) == float(actual_output)):
    print("The machine learning model was: CORRECT")
else:
    print("The machine learning model was: INCORRECT")
