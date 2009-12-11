package kudewe.reports.test.common;
import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations={"/kudewe/reports/config/context/applicationContext.xml", "/kudewe/reports/test/config/context/testContext.xml"})
@Ignore
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {
}
