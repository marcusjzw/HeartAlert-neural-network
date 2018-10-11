# Hardware used
1. Polar H7 Heart Rate Monitor
2. nRF52 Bluetooth Low Energy Development Kit
3. Google Nexus 6 (Android phone, running 7.0 Nougat)
4. UNIX-based laptop for data wrangling and Python machine learning model devleopment
5. Desktop running Windows 10 for Android app development

# Developed Research Components

## Data Wrangling
- Python scripts for data manipulation (fd_calc.py, concat_all_csv.py, ecg_to_rri_script.py)

## Machine Learning
- Python scripts pertaining to developed neural network models (neuralnetwork-75percenttrain.py)
- Python scripts pertaining to testing neural network models using train-test split (neuralnetwork-25percenttest.py)
- Python scripts related to k-fold cross validation testing and neuron heatmap testing (kfoldneuralnetwork.py)

## Smartphone App Development
- Entirety of an Android app written in Java, capable of Bluetooth Low Energy integration with a Polar H7 hardware device, nRF52-DK embedded device, and machine learning model classifier integration.

## Embedded Device Development
- Embedded software written in C++, run on the mbedOS platform. Capable of security/pairing over Bluetooth Low Energy, data spoofing, and communication over any Heart Rate Service compatible app, including the app developed in this research project.
- Python scripts pertaining to the conversion of ventricular tachycardia datasets to C++ array code for data transmission.
```