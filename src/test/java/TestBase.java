import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    public static void beforeAllTests() {
        ReportManager.getInstance();
    }

    @AfterAll
    public static void afterAllTests() {
        ReportManager.closeReport();
    }
}
