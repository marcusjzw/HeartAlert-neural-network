# WAIT

Before you run anything here, initiate virtualenv!

To start virtualenv, use this:

```
$ cd machinelearning (skip  this step if you're already in this folder)
$ source ./bin/activate 
```

# File structure: hrv_nn

The `progress-prior-2july` folder is archived, and should not be used to run anything officially. Files of interest include `all_data.csv`, which contains wrangled data which is not used anymore. These are from the normal sinus rhythm database (which is in .ecg format, converted via Pan-Tompkins algorithm using Matlab) and MIT-BIH Malignant Ventricular Arrhythmia database. These files were essentially ditched for having major flaws:
1. MIT-BIH datasets have long periods of normal sinus rhythm (between 6-8 minutes) before having an arrhythmia episode. This was not considered prior to data wrangling, and it would be a lot of work with Lightwave (https://www.physionet.org/lightwave/) to splice out relevant data.
2. ECG to RRI conversion is wonky, at best. Since there are datasets directly in RRI format anyway, this was favoured.
3. The results are quite poor, considering the dataset was very uneven. This means the baseline accuracy is not 50% (in the case of guessing between 1 and 0).

The `progress-after-2july` _should_ be used to run things officially, as the final classifier run on smartphone is developed from models in this folder. The `all_data_onsets.csv` file contains the wrangled data which is officially used. This involves data from Normal Sinus Rhythm 2 Database (R-R intervals), and Spontaneous Ventricular Tachyarrhythmia Database. The `heatmaps` folder shows .txt files recording results of each neuron configuration, used to populate the neuron heatmap. The script used to populate these .txt files is seen in `kfoldneuralnetwork.py`.

`neuralnetwork-75percenttrain.py` uses 75% of the dataset (from `training_data_75percent.csv`) to train a neural network, spitting out a Keras .h5 model file. There are plenty of print outs - the accuracy is printed out, then the names of all the nodes in the graph. `neuralnetwork-25percenttest.py` then uses the 25% testing dataset to check recall accuracy. This helps ensure that the model can accurately predict from unseen data.

The last Python file, `neuralnetwork-fulltrain.py`, utilises scikit-learn to calculate the confusion matrix results. These are printed out to console.

Lastly, the `saved_models` file contains .h5 models that have been saved via the scripts above. A `keras_to_tensorflow.py` script is present here which freezes Keras variables and converts them to TensorFlow constants. The result is a protobuf file (.pb) that is compatible with the Android interface for TensorFlow models (TensorFlowInferenceInterface). `inputoutputnodenames.txt` contains the name of the input node and output node, which is helpful for the development of the Java code on the Android platform.

# How to run: hrv_nn

1. Ensure that virtualenv has been initiated!
2. Navigate to the folder `/progress-after-2july`
3. Call python xxxx.py, where xxxx is the name of the python script.
4. Results are shown on console.
