from hrv.classical import frequency_domain
from hrv.utils import open_rri
import csv

for name in range(1, 136):
    rri = open_rri(str(name) + '_.txt')
    results = frequency_domain(
        rri=rri,
        fs=4.0,
        method='welch',
        interp_method='cubic',
        detrend='linear'
    )
    print("For data sample: " + str(name))
    print(results)

    output_csv_name = str(name) + '_hrvfd.csv'

    with open(output_csv_name, 'wb') as f:  # Just use 'w' mode in 3.x
        w = csv.DictWriter(f, results.keys())
        w.writeheader()
        w.writerow(results)
    
    print(str(name) + ' rri file has been written to csv')