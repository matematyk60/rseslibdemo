import classifiers.NeuronNetworkPlayground;
import rseslib.system.Report;
import rseslib.system.output.StandardOutput;

public class Demo {

    public static void main(String[] args) throws Exception {
        Report.addInfoOutput(new StandardOutput());

        //KNN Classifier test
        System.out.println("KNN CLASSIFIER");
        System.out.println("--------------");
        classifiers.KnnPlayground knnPlayground = new classifiers.KnnPlayground();

        knnPlayground.singleClassifierTest();
        knnPlayground.crossValidationClassifierTest();

        System.out.println("--------------");

        //Rough Sets classifier test
        System.out.println("ROUGH SET RULE CLASSIFIER");
        System.out.println("--------------");
        classifiers.RoughSetRulePlayground playground = new classifiers.RoughSetRulePlayground();

        playground.singleClassifierTest();
        playground.crossValidationClassifierTest();

        System.out.println("--------------");

        //Neuron Network classifier test
        System.out.println("NEURON NETWORK CLASSIFIER");
        System.out.println("--------------");
        NeuronNetworkPlayground neuronNetworkPlayground = new NeuronNetworkPlayground();

        neuronNetworkPlayground.singleClassifierTest();
        neuronNetworkPlayground.crossValidationClassifierTest();

        System.out.println("--------------");
    }

}
