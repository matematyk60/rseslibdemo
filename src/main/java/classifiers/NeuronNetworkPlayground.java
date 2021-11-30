package classifiers;

import rseslib.processing.classification.Classifier;
import rseslib.processing.classification.neural.NeuronNetwork;
import rseslib.structure.attribute.formats.HeaderFormatException;
import rseslib.structure.data.formats.DataFormatException;
import rseslib.structure.table.DoubleDataTable;
import rseslib.system.PropertyConfigurationException;
import rseslib.system.progress.StdOutProgress;

import java.io.IOException;
import java.util.Properties;

public class NeuronNetworkPlayground extends ClassifierPlayground {

    private final Properties neuronNetworkClassifierProperties;

    public NeuronNetworkPlayground() throws HeaderFormatException, DataFormatException, IOException, InterruptedException {
        //initialize neuron network properties

        this.neuronNetworkClassifierProperties = new Properties();

        this.neuronNetworkClassifierProperties.setProperty("timeLimit", "60");
        this.neuronNetworkClassifierProperties.setProperty("automaticNetworkStructure", "TRUE");
        this.neuronNetworkClassifierProperties.setProperty("initialAlpha", "1");
        this.neuronNetworkClassifierProperties.setProperty("targetAccuracy", "80");
        this.neuronNetworkClassifierProperties.setProperty("showTraining", "FALSE");
    }

    @Override
    public Classifier createClassifier(DoubleDataTable trainingData) throws PropertyConfigurationException, InterruptedException {
        return new NeuronNetwork(neuronNetworkClassifierProperties, trainingData, new StdOutProgress());
    }

    @Override
    public ClassifierPlayground.ClassifierCreationData classifierCreationData() {
        return new ClassifierPlayground.ClassifierCreationData("Neuron network classifier", NeuronNetwork.class, neuronNetworkClassifierProperties);
    }


}
