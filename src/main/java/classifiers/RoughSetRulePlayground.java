package classifiers;

import rseslib.processing.classification.Classifier;
import rseslib.processing.classification.parameterised.knn.KnnClassifier;
import rseslib.processing.classification.rules.roughset.RoughSetRuleClassifier;
import rseslib.structure.attribute.formats.HeaderFormatException;
import rseslib.structure.data.formats.DataFormatException;
import rseslib.structure.table.DoubleDataTable;
import rseslib.system.PropertyConfigurationException;
import rseslib.system.progress.StdOutProgress;

import java.io.IOException;
import java.util.Properties;

public class RoughSetRulePlayground extends ClassifierPlayground {

    private final Properties roughSetClassifierProperties;

    public RoughSetRulePlayground() throws HeaderFormatException, DataFormatException, IOException, InterruptedException {
        //initialize Rough Set rule classifier properties

        this.roughSetClassifierProperties = new Properties();

        this.roughSetClassifierProperties.setProperty("Discretization", "EntropyMinimizationStatic");
        this.roughSetClassifierProperties.setProperty("Reducts", "AllLocal");
        this.roughSetClassifierProperties.setProperty("IndiscernibilityForMissing", "DiscernFromValue");
        this.roughSetClassifierProperties.setProperty("DiscernibilityMethod", "All");
        this.roughSetClassifierProperties.setProperty("GeneralizedDecisionTransitiveClosure", "TRUE");
        this.roughSetClassifierProperties.setProperty("AlphaForPartialReducts", "0.5");
        this.roughSetClassifierProperties.setProperty("MissingValueDescriptorsInRules", "FALSE");
    }

    @Override
    public Classifier createClassifier(DoubleDataTable trainingData) throws PropertyConfigurationException, InterruptedException {
        return new RoughSetRuleClassifier(roughSetClassifierProperties, trainingData, new StdOutProgress());
    }

    @Override
    public ClassifierPlayground.ClassifierCreationData classifierCreationData() {
        return new ClassifierPlayground.ClassifierCreationData("Rough set rule classifier", RoughSetRuleClassifier.class, roughSetClassifierProperties);
    }


}
