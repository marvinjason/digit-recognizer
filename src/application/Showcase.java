
package application;

import neuralnetwork.DataUtils;
import neuralnetwork.INeuralNetworkCallback;
import neuralnetwork.NeuralNetwork;
import neuralnetwork.Result;

public class Showcase
{
    public static void main(String[] args)
    {
        float[][] input = DataUtils.readInputsFromFile("trainingdata/sampleinput.txt");
        int[] output = DataUtils.readOutputsFromFile("trainingdata/sampleoutput.txt");
        float[] valueToPredict = new float[]{1, 1, 0};

        NeuralNetwork neuralNetwork = new NeuralNetwork(input, output, new INeuralNetworkCallback(){

            @Override
            public void success(Result result)
            {
                System.out.println("Predicted Value: " + result.predictValue(valueToPredict));
                System.out.println("Success Percentage: " + String.format("%.2f", result.getSuccessPercentage()));
            }

            @Override
            public void failure(neuralnetwork.Error error)
            {
                System.out.println("Error: " + error.name());
                System.out.println(error.getDescription());
            }

        });

        neuralNetwork.startLearning();
    }
}
