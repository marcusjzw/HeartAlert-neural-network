import os
import sys

try:
    filename =  sys.argv[1]
except:
    print('File name not found!')
numberOfValues = 0;
codesnippet = 'static uint16_t rriValues[100] = {'
for line in file(os.path.dirname(os.path.abspath(__file__)) + '/'+filename): # change the \\ to / if running on Linux instead of Windows
    if (numberOfValues < 100):
        codesnippet += line.rstrip() + ','
        numberOfValues += 1
    else:
        break

codesnippet = codesnippet[:-1] # remove ending comma
codesnippet += '};'
print(codesnippet)
print("Number of Values in this file: " + str(numberOfValues))

