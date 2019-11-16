package calculus.lengthAITraining;

import calculus.splines.SplineType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DataGenerationScript {
    public static void main(String[] args) {
        generateDataset(340, 0,1000);
        generateDataset(200, 339,500);
        generateDataset(100, 548, 100);
    }

    private static void generateDataset(int numberOfBatches, int startingIndex, int range) {
        for (int i = startingIndex; i < (numberOfBatches+startingIndex); i++) {
            List<TrainingElement> dataSet = new DataSetFactory().generateDataSet(SplineType.CUBIC_HERMITE, range, 1000);
            System.out.println("created all data sets");
            System.out.println(dataSet.size());
            try {
                FileWriter fw = new FileWriter("C:\\Users\\Daniel\\Documents\\LengthAIDataSets\\data_batch" + i + ".txt", false);
                PrintWriter writer = new PrintWriter(fw);
                for (TrainingElement element : dataSet) {
                    writer.println(element.toString());
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("failed at writing data");
            }
            System.out.println("wrote data");
            System.out.println("completed batch " + i);
        }
    }
}
