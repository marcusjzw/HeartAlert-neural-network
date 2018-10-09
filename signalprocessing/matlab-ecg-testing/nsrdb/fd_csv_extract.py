import pandas as pd 
for i in range(16265, 19831):
    try: 
        df = pd.read_csv(str(i) + '.csv', usecols=[11, 14, 15, 18])
# print(df)
        df.to_csv(str(i)+'_fd.csv', sep=',')
    except:
        continue