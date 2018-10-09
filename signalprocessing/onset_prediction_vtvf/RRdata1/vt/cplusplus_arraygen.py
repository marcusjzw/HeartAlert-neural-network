filename = '77_.txt'
codesnippet = 'static uint8_t rriValues = {'
for line in file('/Users/Marcus/git/compsys700-new/signalprocessing/onset_prediction_vtvf/RRdata1/vt/'+filename): # CHANGE THIS FILE IF WANT TO RUN
    codesnippet += line.rstrip() + ','

codesnippet = codesnippet[:-1] # remove ending comma
codesnippet += '}'
print(codesnippet)

