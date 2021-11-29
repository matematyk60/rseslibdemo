import rseslib.example.ComputeReducts;
import rseslib.processing.classification.*;
import rseslib.processing.classification.parameterised.knn.KnnClassifier;
import rseslib.structure.data.DoubleData;
import rseslib.structure.table.ArrayListDoubleDataTable;
import rseslib.structure.table.DoubleDataTable;
import rseslib.system.Report;
import rseslib.system.output.StandardOutput;
import rseslib.system.progress.StdOutProgress;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Demo {

    public static void main(String[] args) throws Exception {
        Report.addInfoOutput(new StandardOutput());
        KnnPlayground knnPlayground = new KnnPlayground();

        knnPlayground.singleClassifierTest();
        knnPlayground.crossValidationClassifierTest();
    }

}
