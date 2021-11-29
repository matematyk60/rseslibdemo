import rseslib.system.Report;
import rseslib.system.output.StandardOutput;

public class Demo {

    public static void main(String[] args) throws Exception {
        Report.addInfoOutput(new StandardOutput());
        KnnPlayground knnPlayground = new KnnPlayground();

        knnPlayground.singleClassifierTest();
        knnPlayground.crossValidationClassifierTest();
    }

}
