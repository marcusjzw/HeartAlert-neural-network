# COMPSYS700: Introduction
This is project #97 of the 2018 University of Auckland ECE Part IV projects. The intent of this research is
to provide an automated emergency callout system for heart disease patients in life threatening events. 

_NOTE: For more details on each folder, please view the README in their respective folders._

This repository contains:
1. `/signalprocessing`, contains Python scripts that preprocess Physionet datasets. This involves conversion of .vt and .qrs files into .txt R-R interval files, as well as use of the Python hrv library to convert samples to frequency domain HRV. Scripts for parsing data into .csv format are also included.  Two datasets used: normal sinus rhythm database, Spontaneous Ventricular Tachyarrhythmia Database. Other files were the result of experimentation with the Pan-Tompkins algorithm and Matlab code to convert ECG signals to HRV; this is not used in the final implementation
2. `/machinelearning`, This is a virtualenv environment, so make sure the virtualenv commands (seen under 'Important notes') have been enabled. Contains the files to run the HRV neural network trained in this project, using Keras. K-fold cross validation was done via scikit-learn, and different data analysis/processing tools like numpy/pandas have been used for data output.
3. `/PolarHeartRateApplication`, code regarding the Android app developed for automated emergency callouts. Run this code using Android Studio. 
4. `/bluetooth-le-spoofer`, contains the mbedOS files used to program the nRF52-DK. Use of the mbedOS platform is required in order to test this.

# Important notes
1. To get virtualenv + other dependencies (it's too big to put all on git) then use this command:
`virtualenv --no-site-packages --distribute .env && source .env/bin/activate && pip install -r requirements.txt`

To start virtualenv, use this:

```
$ cd targetDirectory
$ source ./bin/activate 
```

2. The wfdb software package is required to run any python scripts that extract information from Physionet datasets. This is a standalone command line package, MATLAB package or Python package.

3. To easily download the datasets provided in this repo for yourself, use
`rsync`:
 
 `rsync -Cavz physionet.org::nameOfDb /directory`, where nameOfDb substitutes the desired database and /directory is the absolute path of where you want to save it.
