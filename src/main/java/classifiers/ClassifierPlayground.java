package classifiers;

import classifiers.KnnPlayground;
import rseslib.processing.classification.*;
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

public abstract class ClassifierPlayground {

    protected final DoubleDataTable table;

    public ClassifierPlayground() throws HeaderFormatException, DataFormatException, IOException, InterruptedException {
        //load data
        this.table = new ArrayListDoubleDataTable(getResourceFile("data/heart.dat"), new StdOutProgress());
    }

    abstract public Classifier createClassifier(DoubleDataTable trainingData) throws PropertyConfigurationException, InterruptedException;

    abstract public ClassifierCreationData classifierCreationData();

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
        Classifier classifier = createClassifier(trainDataTable);

        // test classifier
        SingleClassifierTest classifierTest = new SingleClassifierTest();
        TestResult result = classifierTest.classify(classifier, testDataTable, new StdOutProgress());
        Report.displaynl(result.getStatisticsAndResults());
    }

    public void crossValidationClassifierTest() throws PropertyConfigurationException, InterruptedException {
        ClassifierCreationData classifierCreationData = classifierCreationData();

        //create classifier set - cross validation API only works with ClassifierSet
        ClassifierSet classifierSet = new ClassifierSet();
        classifierSet.addClassifier(classifierCreationData.getClassifierName(), classifierCreationData.getClassifierType(), classifierCreationData.getClassifierProperties());

        //initialize cross validation parameters
        Properties crossValidationParams = new Properties();
        crossValidationParams.setProperty("noOfFolds", "10");
        CrossValidationTest crossValidationTest = new CrossValidationTest(crossValidationParams, classifierSet);

        //run cross validation of classifier
        Map<String, MultipleTestResult> results = crossValidationTest.test(table, new StdOutProgress());

        //display cross validation results
        Report.displayMapWithMultiLines(classifierCreationData.getClassifierName(), results);
    }


    static class ClassifierCreationData {
        private final String classifierName;
        private final Class<?> classifierType;
        private final Properties classifierProperties;

        public ClassifierCreationData(String classifierName, Class<?> classifierType, Properties classifierProperties) {
            this.classifierName = classifierName;
            this.classifierType = classifierType;
            this.classifierProperties = classifierProperties;
        }

        public String getClassifierName() {
            return classifierName;
        }

        public Class<?> getClassifierType() {
            return classifierType;
        }

        public Properties getClassifierProperties() {
            return classifierProperties;
        }
    }

    private static File getResourceFile(String filename) {
        try {
            return new File(KnnPlayground.class.getClassLoader().getResource(filename).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
