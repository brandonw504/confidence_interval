/*
This program generates a dataset of values from 0 to 1 and creates a nonparametric confidence interval.
Nonparametric tests are used when the median provides a better measure of center than the mean.
 */
import java.util.Arrays;
import java.util.Scanner;

public class ConfidenceInterval {
    public static void main(String[] args) {
        // input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the size of the dataset you want to generate: ");
        int datasetSize = input.nextInt();
        System.out.print("Enter a number to determine the skew. Between 0 and 1 will be left skewed, 1 will be uniform, and greater than 1 will be right skewed. Enter: ");
        double skew = input.nextDouble();
        System.out.print("Enter a sample size smaller than the dataset size: ");
        int sampleSize = input.nextInt();
        System.out.print("Enter a confidence level in percent (ex: 97.5): ");
        double confidenceLevel = input.nextDouble();
        input.close();

        // generating the dataset of random doubles from 0 to 1
        var dataset = new double[datasetSize];
        for (int i = 0; i < datasetSize; i++) {
            dataset[i] = Math.pow(Math.random(), skew);
        }

        // taking a sample
        var sample = new double[sampleSize];
        double sum = 0.0;
        double mean = 0.0;
        for (int i = 0; i < sampleSize; i++) {
            for (int j = 0; j < 100; j++) {
                sum += dataset[(int)(Math.random() * datasetSize)];
                mean = sum / 100;
            }
            sample[i] = mean;
            sum = 0.0;
            mean = 0.0;
        }
        Arrays.sort(sample);

        // finding the middle x% of data points in the sample
        double significanceLevel = 100 - confidenceLevel;
        int lowerIndex = (int)Math.floor((sample.length * (significanceLevel / 200)) + 0.5);
        int upperIndex = (int)Math.floor((sample.length * (confidenceLevel + (significanceLevel / 2)) / 100) + 0.5);
        double lower = sample[lowerIndex];
        double upper = sample[upperIndex];

        // output
        System.out.println();
        System.out.println("Result:");
        System.out.format("I am %.0f%% confident that the true population mean lies in the interval (%.3f, %.3f).\n", confidenceLevel, lower, upper);
    }
}
