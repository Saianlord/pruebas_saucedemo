import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./test-output/ExtentReport.html");
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static void closeReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
