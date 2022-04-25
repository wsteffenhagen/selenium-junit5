package tests;

import config.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.utility.BrowserUtil;
import pageobjects.utility.ElementUtil;

public class BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);
    protected final SoftAssertions softly = new SoftAssertions();
    protected ElementUtil element;
    protected BrowserUtil browser;
    protected WebDriver driver;
    private WebDriverManager wdm;

    /**
     * Before test class.
     */
    @BeforeAll
    static void beforeClass() {
        // empty for now
    }

    /**
     * Before each test.
     *
     * @param testInfo the test info
     */
    @BeforeEach
    synchronized void beforeTest(TestInfo testInfo) {
        String testName = testInfo.getDisplayName().replace("()", "");
        LOG.info("Test starting: {}", testName);
        wdm = Configuration.getWebDriverManager();
        driver = wdm.create();
        element = new ElementUtil(driver);
        browser = new BrowserUtil(driver);
    }

    /**
     * After each test.
     */
    @AfterEach
    synchronized void afterTest() {
        wdm.quit();
    }
}
