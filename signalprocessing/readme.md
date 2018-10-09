# Readme - signalprocessing

## How to run: nsr2db

The folder named nsr2db has data from the normal sinus rhythm database. The ecg signal was converted to a text rri value via the script `ecg_to_rri_script.py`, _which requires the wfdb command line package to be installed_. The objective of this data was to obtain RR intervals related to normal sinus rhythm. However, only a subset of these .txt files were used - the first three. This is because these readings are huge and we do not need that much data (ventricular tachycardia readings are much shorter, being about 4-8KB in size. These files are in the range of 400KB!)

This data was mixed with 'mr' data from the Spontaneous Ventricular Tachyarrhythmia Database (SVTDB) to form all non-VT data used in the machine learning dataset. This is explained in the next section...

## How to run: onset_prediction_vtvf

This folder contains dataset files downloading from SVTDB, you can see it in the `data/` subdirectory. In this subdirectory, you should spot files tagged as 'mr', 'vt' and 'vf'.

- 'vt' naturally means ventricular tachycardia, these files were converted and added to the VT data used in the machine learning dataset
- 'vf' means ventricular fibrillation, these files were omitted for reasons explained in the Report.
- 'mr' means Most Recent. It contains the 1024 R-R intervals BEFORE ICD interrogation. To understand this, keep in mind that all files in this database are actually captured via an ICD (Implantable-Cardioverter-Defribillator) used by heart disease sufferers. When a pacing event is required due to VT or VF, the latest 1024 RR intervals in the device's memory buffer are saved. 'MR' thus refers to a non-VT event, where the patient is diseased but is not going undergoing an episode. Data here was mixed with normal sinus rhythm database data to create the non-diseased dataset.

The files were converted from their obscure file format (e.g. .vt) to .txt via a wfdb command.

The `mr/` folder shows the data extracted from mr samples, and the `RRdata1/vt/` folder shows the data extracted vt samples. In both directories, two scripts (`concat_all_csv.py` and `fd_calc.py`) are used to convert all RRI data to frequency domain HRV in .csv format, and merge all individual .csv together into one .csv file. 

The resulting file produced is named `all_data_onsets.csv`, which takes the output and adds an extra column with the class variable (outcome, either 0 for non-VT or 1 for VT). This then copied to the `machinelearning/` folder for further analysis.