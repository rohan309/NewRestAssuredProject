import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/api_automation/Features",
        glue = "com.api_automation.stepdefination",
        tags = "@GetAllCustomer"
       /* plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "junit:target/cucumber-reports/Cucumber.xml"
        }*/
)
public class TestRunner {
}
