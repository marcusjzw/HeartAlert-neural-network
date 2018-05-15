import pandas as pd
import glob, os
path = '/Users/Marcus/git/compsys700/signalprocessing/cudb_nsrdb/cudb/'
all_files = glob.glob(os.path.join(path, "*_fd.csv"))    
df_from_each_file = (pd.read_csv(f) for f in all_files)
concatenated_df   = pd.concat(df_from_each_file, ignore_index=True)
# doesn't create a list, nor does it append to one
concatenated_df.to_csv('allvt_fd.csv', sep=',')
