package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleHomePage extends BasePage {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleHomePage.class);

    @FindBy(css = "input[title='Search']")
    private WebElement searchInput;

    @FindBy(css = ".UUbT9 input[value='Google Search']")
    private WebElement googleSearchButton;

    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }

    public GoogleHomePage visit() {
        LOG.info("Navigating to Google home page.");
        browser.navigate("/");
        return new GoogleHomePage(driver);
    }

    public GoogleSearchResultsPage googleSearch(String searchTerm) {
        LOG.info("Searching for term '{}' in Google search.", searchTerm);
        element.sendKeys(searchInput, searchTerm);
        element.click(googleSearchButton);
        return new GoogleSearchResultsPage(driver);
    }
}
