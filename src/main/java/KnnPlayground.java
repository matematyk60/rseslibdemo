import rseslib.processing.classification.*;
import rseslib.processing.classification.parameterised.knn.KnnClassifier;
import rseslib.structure.attribute.formats.HeaderFormatException;
import rseslib.structure.data.DoubleData;
import rseslib.structure.data.formats.DataFormatException;
import rseslib.structure.table.ArrayListDoubleDataTable;
import rseslib.structure.table.DoubleDataTable;
import rseslib.system.PropertyConfigurationException;
import rseslib.system.Report;
import rseslib.system.progress.StdOutProgress;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class KnnPlayground {
    private final DoubleDataTable table;

    private final Properties knnClassifierProperties;

    public KnnPlayground() throws HeaderFormatException, DataFormatException, IOException, InterruptedException {

        //load data
        table = new ArrayListDoubleDataTable(getResourceFile("data/heart.dat"), new StdOutProgress());

        //initialize KNN classifier properties

        knnClassifierProperties = new Properties();

        knnClassifierProperties.setProperty("learnOptimalK", "TRUE");
        knnClassifierProperties.setProperty("metric", "CityAndSimpleValueDifference");
        knnClassifierProperties.setProperty("weightingMethod", "DistanceBased");
        knnClassifierProperties.setProperty("indexing", "FALSE");
        knnClassifierProperties.setProperty("maxK", "100");
        knnClassifierProperties.setProperty("filterNeighboursUsingRules", "TRUE");
        knnClassifierProperties.setProperty("voting", "InverseSquareDistance");
    }

    public void singleClassifierTest() throws PropertyConfigurationException, InterruptedException {
        // prepare test and training data
        ArrayList<DoubleData> originalData = table.getDataObjects();
        ArrayList<DoubleData> trainData = new ArrayList<>();
        ArrayList<DoubleData> testData = new ArrayList<>();

        // test data: first 10 rows, training data: all next rows
        for (int i = 0; i < originalData.size(); i++) {
            DoubleData obj = originalData.get(i);
            if (i < 10) {
                testData.add(obj);
            } else {
                trainData.add(obj);
            }
        }

        ArrayListDoubleDataTable trainDataTable = new ArrayListDoubleDataTable(trainData);
        ArrayListDoubleDataTable testDataTable = new ArrayListDoubleDataTable(testData);

        // create and train classifier
        KnnClassifier knnClassifier = new KnnClassifier(knnClassifierProperties, trainDataTable, new StdOutProgress());

        // test classifier
        SingleClassifierTest classifierTest = new SingleClassifierTest();
        TestResult result = classifierTest.classify(knnClassifier, testDataTable, new StdOutProgress());
        result.getStatisticsAndResults().list(System.out);
    }

    public void crossValidationClassifierTest() throws PropertyConfigurationException, InterruptedException {
        //create classifier set - cross validation API only works with ClassifierSet
        ClassifierSet classifierSet = new ClassifierSet();
        classifierSet.addClassifier("KNN Classifier", KnnClassifier.class, knnClassifierProperties);

        //initialize cross validation parameters
        Properties crossValidationParams = new Properties();
        crossValidationParams.setProperty("noOfFolds", "10");
        CrossValidationTest crossValidationTest = new CrossValidationTest(crossValidationParams, classifierSet);

        //run cross validation of classifier
        Map<String, MultipleTestResult> results = crossValidationTest.test(table, new StdOutProgress());

        //display cross validation results
        Report.displayMapWithMultiLines("KNN Classifier", results);
    }

    private static File getResourceFile(String filename) {
        try {
            return new File(KnnPlayground.class.getClassLoader().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
