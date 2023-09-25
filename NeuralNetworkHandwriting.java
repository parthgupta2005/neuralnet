/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetworkhandwriting;

import java.util.Arrays;

/**
 *
 * @author parthgupta
 */
public class NeuralNetworkHandwriting
{

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {

    learning();
  }

  public static void print(Object p)
  {
    System.out.println(p);
  }

  public static void learning()
  {
    String[] inputOutputData =
    {
      "1111011011011111000000000",
      "0010010010010010100000000",
      "0010010010010000100000000",
      "0000010010010010100000000",
      "1110011111001110010000000",
      "0110011111001110010000000",
      "1110011111001100010000000",
      "1110011110011110001000000",
      "1110010110011110001000000",
      "0110011110011110001000000",
      "1110011110010110001000000",
      "1011011110010010000100000",
      "0011011110010010000100000",
      "1001011110010010000100000",
      "1011011110010000000100000",
      "1111001110011110000010000",
      "1101001110011110000010000",
      "1111001110010110000010000",
      "1111001111011110000001000",
      "1101001111011110000001000",
      "1001001111011110000001000",
      "1110010010010010000000100",
      "1110010010010000000000100",
      "1111011111011110000000010",
      "1111011110011110000000001",
      "1111011110010110000000001",
      "1111011110010010000000001"
    };

    String[] validationData =
    {
      "1111011011011111000000000",
      "0000010010010010100000000",
      "0010010010010010100000000",
      "0000010010010000100000000",
      "1110011111001110010000000",
      "1110011111001100010000000",
      "0110011111001100010000000",
      "1110011110011110001000000",
      "0110011110011110001000000",
      "0110010110010110001000000",
      "1011011110010010000100000",
      "0011011110010010000100000",
      "0011011110010000000100000",
      "1111001110011110000010000",
      "1101001110011110000010000",
      "1101001110010110000010000",
      "1111001111011110000001000",
      "1101001111011110000001000",
      "1001001111011110000001000",
      "1110010010010010000000100",
      "1110010010010000000000100",
      "0110010010010010000000100",
      "1111011111011110000000010",
      "1111011110011110000000001",
      "1111011110010110000000001",
      "1111011110010010000000001"
    };

    int index;
    int accuracy = -4;
    double randValue;
    double conditional;
    String[] line;

    inputNode[] inputNodes = new inputNode[15];
    double[] hiddenWeights = new double[15];
    hiddenNode[] hiddenNodes = new hiddenNode[12];
    double[] outputWeights = new double[12];
    outputNode[] outputNodes = new outputNode[10];

    inputNode[] testInputNodes = new inputNode[15];
    hiddenNode[] testHiddenNodes = new hiddenNode[12];
    outputNode[] testOutputNodes = new outputNode[10];

    index = (int) (Math.random() * 26);

    for (int k = 0; k < inputNodes.length; k++)
    {
      inputNodes[k] = new inputNode(Character.getNumericValue(inputOutputData[index].charAt(k)));
    }

    for (int j = 0; j < hiddenNodes.length; j++)
    {
      for (int k = 0; k < hiddenWeights.length; k++)
      {
        conditional = Math.random();
        if (conditional >= 0.5)
        {
          randValue = Math.random();
        }
        else
        {
          randValue = -1 * Math.random();
        }
        hiddenWeights[k] = randValue;
      }
      hiddenNodes[j] = new hiddenNode(Arrays.copyOf(hiddenWeights, hiddenWeights.length));
    }

    for (int j = 0; j < outputNodes.length; j++)
    {
      for (int k = 0; k < outputWeights.length; k++)
      {
        conditional = Math.random();
        if (conditional >= 0.5)
        {
          randValue = Math.random();
        }
        else
        {
          randValue = -1 * Math.random();
        }
        outputWeights[k] = randValue;
      }
      outputNodes[j] = new outputNode(Arrays.copyOf(outputWeights, outputWeights.length), Character.getNumericValue(inputOutputData[index].charAt(j + 15)));
    }

    for (int i = 0; i < 19000; i++)
    {
      index = (int) (Math.random() * 26);

      for (int k = 0; k < inputNodes.length; k++)
      {
        inputNodes[k] = new inputNode(Character.getNumericValue(inputOutputData[index].charAt(k)));
      }
      for(int j = 0; j < outputNodes.length; j++)
      {
        outputNodes[j] = new outputNode(Arrays.copyOf(outputNodes[j].getWeights(), outputNodes[j].getWeights().length), 
            Character.getNumericValue(inputOutputData[index].charAt(j + 15)));
      }
      
      hiddenNodes = calcHidden(inputNodes, hiddenNodes);
      outputNodes = calcOutput(hiddenNodes, outputNodes);

      outputNodes = adjustOutputWeights(hiddenNodes, outputNodes);
      hiddenNodes = adjustHiddenWeights(inputNodes, hiddenNodes, outputNodes);
    }

    for (int j = 0; j < outputNodes.length; j++)
    {
      print(outputNodes[j].getValue());
      print(outputNodes[j].getExpectedValue());
    }

    print("");

    testHiddenNodes = Arrays.copyOf(hiddenNodes, hiddenNodes.length);

    for (int q = 0; q < validationData.length; q++)
    {
      for (int l = 0; l < 15; l++)
      {
        testInputNodes[l] = new inputNode(Character.getNumericValue(validationData[q].charAt(l)));
      }
      for (int t = 0; t < 10; t++)
      {
        testOutputNodes[t] = new outputNode(Arrays.copyOf(outputNodes[t].getWeights(),
            outputNodes[t].getWeights().length), Character.getNumericValue(validationData[q].charAt(t + 15)));
      }

      testHiddenNodes = calcHidden(testInputNodes, testHiddenNodes);
      testOutputNodes = calcOutput(testHiddenNodes, testOutputNodes);

      for (int j = 0; j < outputNodes.length; j++)
      {
        if(testOutputNodes[j].getValue() >= 0.63 && testOutputNodes[j].getExpectedValue() == 1) {
          accuracy = accuracy + 4;
        }
        print(testOutputNodes[j].getValue());
        print(testOutputNodes[j].getExpectedValue());
      }

      print("");
    }
    print("accuracy:" + accuracy);
  }

  public static hiddenNode[] calcHidden(inputNode[] inputNodes, hiddenNode[] hiddenNodes)
  {
    double hiddenNodeValue = 0;
    double gx = 0;

    for (int i = 0; i < hiddenNodes.length; i++)
    {
      for (int j = 0; j < hiddenNodes[0].getWeights().length; j++)
      {
        gx = gx + inputNodes[j].getValue() * hiddenNodes[i].getWeights()[j];
        hiddenNodeValue = 1 / (1 + Math.exp(-1 * gx));
      }
      hiddenNodes[i].setValue(hiddenNodeValue);
      gx = 0;
    }
    return hiddenNodes;
  }

  public static outputNode[] calcOutput(hiddenNode[] hiddenNodes, outputNode[] outputNodes)
  {
    double outputNodeValue = 0;
    double gx = 0;

    for (int i = 0; i < outputNodes.length; i++)
    {
      for (int j = 0; j < outputNodes[0].getWeights().length; j++)
      {
        gx = gx + hiddenNodes[j].getValue() * outputNodes[i].getWeights()[j];
        outputNodeValue = 1 / (1 + Math.exp(-1 * gx));
      }
      outputNodes[i].setValue(outputNodeValue);
      gx = 0;
    }
    return outputNodes;
  }

  public static outputNode[] adjustOutputWeights(hiddenNode[] hiddenNodes, outputNode[] outputNodes)
  {
    double learningRate = 0.25;
    double adjustment;
    double error;
    double hiddenNodeValue;

    for (int i = 0; i < outputNodes.length; i++)
    {
      for (int k = 0; k < outputNodes[0].getWeights().length; k++)
      {
        error = outputNodes[i].getValue() * (1 - outputNodes[i].getValue()) * (outputNodes[i].getExpectedValue() - outputNodes[i].getValue());
        outputNodes[i].setError(error);
        adjustment = learningRate * error * hiddenNodes[k].getValue();
        outputNodes[i].getWeights()[k] = outputNodes[i].getWeights()[k] + adjustment;
      }
    }
    return outputNodes;
  }

  public static hiddenNode[] adjustHiddenWeights(inputNode[] inputNodes, hiddenNode[] hiddenNodes, outputNode[] outputNodes)
  {
    double learningRate = 0.25;
    double alpha = 0;
    double adjustment;
    double error;
    double hiddenNodeValue;

    for (int i = 0; i < hiddenNodes.length; i++)
    {
      for (int k = 0; k < hiddenNodes[0].getWeights().length; k++)
      {
        for (int j = 0; j < outputNodes.length; j++)
        {
          alpha = alpha + outputNodes[j].getError() * outputNodes[j].getWeights()[i];
        }
        error = hiddenNodes[i].getValue() * (1 - hiddenNodes[i].getValue()) * alpha;
        adjustment = learningRate * error * inputNodes[k].getValue();
        hiddenNodes[i].getWeights()[k] = hiddenNodes[i].getWeights()[k] + adjustment;
        alpha = 0;
      }
    }
    return hiddenNodes;
  }
}

class inputNode
{

  int value;

  public inputNode(int value)
  {
    this.value = value;
  }

  public int getValue()
  {
    return this.value;
  }
}

class hiddenNode
{

  double value;
  double[] weights;

  public hiddenNode(double[] weights)
  {
    this.weights = weights;
  }

  public void setValue(double value)
  {
    this.value = value;
  }

  public void setWeights(double[] weights)
  {
    this.weights = weights;
  }

  public double getValue()
  {
    return this.value;
  }

  public double[] getWeights()
  {
    return this.weights;
  }
}

class outputNode
{

  double error;
  double value;
  double[] weights;
  int expectedValue;

  public outputNode(double[] weights, int expectedValue)
  {
    this.weights = weights;
    this.expectedValue = expectedValue;
  }

  public void setError(double error)
  {
    this.error = error;
  }

  public double getError()
  {
    return this.error;
  }

  public void setValue(double value)
  {
    this.value = value;
  }

  public double getValue()
  {
    return this.value;
  }

  public double getExpectedValue()
  {
    return this.expectedValue;
  }

  public double[] getWeights()
  {
    return this.weights;
  }
}
