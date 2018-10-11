import pandas as pd
import glob, os
path = os.path.dirname(os.path.abspath(__file__)) + '/' # change the / to \\ if running on Windows instead of UNIX
all_files = glob.glob(os.path.join(path, "*_hrvfd.csv"))    
df_from_each_file = (pd.read_csv(f) for f in all_files)
concatenated_df   = pd.concat(df_from_each_file, ignore_index=True)
# doesn't create a list, nor does it append to one
concatenated_df.to_csv('all_data.csv', sep=',')
