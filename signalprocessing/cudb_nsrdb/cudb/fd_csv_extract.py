import pandas as pd 
for i in range(1, 36):
    try: 
        df = pd.read_csv('cu' + str(i).zfill(2) + '.csv', usecols=[11, 14, 15, 18])
        # print(df)
        df.to_csv('cu' + str(i).zfill(2)+'_fd.csv', sep=',')
    except:
        continue