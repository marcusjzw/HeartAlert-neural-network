# When you use ann2rr from the physionet WFDB library,
# 1. the first value is garbage value, always. strip it out
# 2. the subsequent values are not in ms, you need to *10 for it to be in ms.
# This script does 2. currently (make it automate and do 1 as well)
s = '0'
with open('nsr003.rri2.txt', 'w') as out_file:
    with open('nsr003.rri.txt', 'r') as in_file:
        for line in in_file:
            out_file.write(line.rstrip('\n') + s + '\n')