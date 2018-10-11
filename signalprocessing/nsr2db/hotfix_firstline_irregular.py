for i in range(1, 55):
    filename = str(i).zfill(3) + 'rri.txt'
    with open(filename, 'r') as fin:
        data = fin.read().splitlines(True)
    with open(filename, 'w') as fout:
        for i in range(1, len(data)):
            fout.write(data[i])
    print(filename + "complete")