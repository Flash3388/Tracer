import torch.utils.data
import torch.nn as nn
import gc

from src.CubicDataset import CubicDataset
from src.FeedForwardNeuralNetModel import FeedForwardNeuralNetModel

DIRECTORY = 'C:\\Users\\Daniel\\Documents\\LengthAIDataset'


def train(model, directory, train_dataset_size, num_of_epochs, batch_size):
    learning_rate = 0.01
    criterion = nn.MSELoss()

    for epoch in range(num_of_epochs):
        learning_rate = adjust_learning_rate(learning_rate, epoch)
        optimizer = torch.optim.SGD(model.parameters(), lr=learning_rate)

        for iteration in range(int(train_dataset_size / 9)):
            print("epoch ", epoch, "iteration ", iteration)

            dataset = CubicDataset(directory, iteration * 9, (iteration + 1) * 9)
            loader = torch.utils.data.DataLoader(dataset,
                                                 batch_size=batch_size,
                                                 shuffle=True)

            train_loader(model, optimizer, loader, criterion)
            clear_dataset(dataset)
    test(model, DIRECTORY, 99)

    return model


def adjust_learning_rate(current_learning_rate, epoch):
    if not (epoch + 1) % 5:
        return current_learning_rate / 10
    else:
        return current_learning_rate


def train_loader(model, optimizer, loader, criterion):
    for j, (inputs, expected) in enumerate(loader):
        optimizer.zero_grad()
        outputs = model(inputs).t()[0]
        loss = criterion(outputs, expected)
        loss.backward()
        optimizer.step()


def clear_dataset(dataset):
    dataset.data = None
    dataset.targets = None
    gc.collect()


def test(model, directory, test_index):
    test_dataset = CubicDataset(directory, test_index, test_index + 1)
    test_loader = torch.utils.data.DataLoader(test_dataset,
                                              batch_size=10,
                                              shuffle=True)
    errors = 0
    for i, (inputs, expected) in enumerate(test_loader):
        errors += (expected - model(inputs).t()[0]).abs().sum().item() / 10

    return errors / (len(test_dataset) / 10)


def standard_model():
    input_dim = 9
    output_dim = 1
    hidden_dim = 30

    return FeedForwardNeuralNetModel(input_dim, hidden_dim, output_dim)


def main():
    model = train(standard_model(), DIRECTORY, 10, 1, 1000)
    # torch.save(model, '/media/daniel/736C-2464/cubic10.model')


if __name__ == '__main__':
    main()
