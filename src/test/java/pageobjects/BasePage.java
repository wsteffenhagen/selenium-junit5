package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageobjects.utility.BrowserUtil;
import pageobjects.utility.ElementUtil;

public abstract class BasePage {
    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected final ElementUtil element;
    protected final BrowserUtil browser;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        element = new ElementUtil(driver);
        browser = new BrowserUtil(driver);
        browser.waitForJsToLoad();
    }
}
