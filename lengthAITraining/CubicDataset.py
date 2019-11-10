from torch.utils.data import Dataset
import torch.tensor


def deserialize_file(path):
    data = list()
    targets = list()

    with open(path) as file:
        lines = file.readlines()

    for line in lines:
        line = line.replace(' ', '')
        line = line.replace('\n', '')
        line = line.split('::')

        data.append(deserialize_input_entry(line[0].split(',')))
        targets.append(torch.tensor(float(line[1])).float())

    return data, targets


def deserialize_input_entry(input_list):
    input_entry = list()

    for i in input_list:
        input_entry.append(float(i))
    return torch.tensor(input_entry).float()


class CubicDataset(Dataset):
    def __init__(self, directory, start, end):
        self.data, self.targets = list(), list()
        for i in range(start, end):
            print(i)
            data_point = deserialize_file(directory + "data_batch" + str(i) + ".txt")
            self.data.extend(data_point[0])
            self.targets.extend(data_point[1])

    def __len__(self):
        return len(self.data)

    def __getitem__(self, index):
        return self.data[index], self.targets[index]
