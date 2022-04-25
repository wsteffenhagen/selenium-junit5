package pageobjects.utility;

import config.Configuration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public final class BrowserUtil {
    private static final Logger LOG = LoggerFactory.getLogger(BrowserUtil.class);
    private final String baseUrl = Configuration.getBaseUrl();
    private final WebDriver driver;

    /**
     * Instantiates a new Browser util.
     *
     * @param driver the driver
     */
    public BrowserUtil(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigate to page.
     *
     * @param path the path
     */
    public void navigate(String path) {
        LOG.info("Loading URL: {}{}", baseUrl, path);
        driver.get(baseUrl + path);
        waitForJsToLoad();
    }

    /**
     * Refresh the page.
     */
    public void refresh() {
        LOG.info("Refreshing the page.");
        driver.navigate().refresh();
        waitForJsToLoad();
    }

    /**
     * Gets current URL from driver.
     *
     * @return the current url as a string
     */
    public String getCurrentUrl() {
        LOG.info("Getting current URL.");
        return driver.getCurrentUrl();
    }

    /**
     * Waits for designated number of milliseconds.
     *
     * @param millisecondsToWait the milliseconds to wait
     */
    public void wait(int millisecondsToWait) {
        try {
            LOG.info("Waiting for {} milliseconds.", millisecondsToWait);
            Thread.sleep(millisecondsToWait);
        } catch (InterruptedException e) {
            LOG.error("InterruptedException: ", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Waits for JavaScript to load.
     */
    public void waitForJsToLoad() {
        try {
            ExpectedCondition<Boolean> jsLoad = driver -> {
                assert driver != null;
                return (Boolean) ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState === 'complete'");
            };
            getFluentWait(10).until(jsLoad);
        } catch (Exception e) {
            LOG.error("JavaScript may have failed to load for {}", getCurrentUrl());
        }
    }

    /**
     * Gets fluent wait object.
     *
     * @param secondsToWait the seconds to wait
     * @return the fluent wait
     */
    private FluentWait<WebDriver> getFluentWait(int secondsToWait) {
        return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(secondsToWait))
                .pollingEvery(Duration.ofMillis(500));
    }
}
