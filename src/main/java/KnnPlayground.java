import rseslib.processing.classification.Classifier;
import rseslib.processing.classification.parameterised.knn.KnnClassifier;
import rseslib.structure.attribute.formats.HeaderFormatException;
import rseslib.structure.data.formats.DataFormatException;
import rseslib.structure.table.DoubleDataTable;
import rseslib.system.PropertyConfigurationException;
import rseslib.system.progress.StdOutProgress;

import java.io.IOException;
import java.util.Properties;

public class KnnPlayground extends ClassifierPlayground {

    private final Properties knnClassifierProperties;

    public KnnPlayground() throws HeaderFormatException, DataFormatException, IOException, InterruptedException {
        //initialize KNN classifier properties

        this.knnClassifierProperties = new Properties();

        this.knnClassifierProperties.setProperty("learnOptimalK", "TRUE");
        this.knnClassifierProperties.setProperty("metric", "CityAndSimpleValueDifference");
        this.knnClassifierProperties.setProperty("weightingMethod", "DistanceBased");
        this.knnClassifierProperties.setProperty("indexing", "FALSE");
        this.knnClassifierProperties.setProperty("maxK", "100");
        this.knnClassifierProperties.setProperty("filterNeighboursUsingRules", "TRUE");
        this.knnClassifierProperties.setProperty("voting", "InverseSquareDistance");
    }

    @Override
    public Classifier createClassifier(DoubleDataTable trainingData) throws PropertyConfigurationException, InterruptedException {
        return new KnnClassifier(this.knnClassifierProperties, trainingData, new StdOutProgress());
    }

    @Override
    public ClassifierCreationData classifierCreationData() {
        return new ClassifierCreationData("KNN Classifier", KnnClassifier.class, this.knnClassifierProperties);
    }


}
