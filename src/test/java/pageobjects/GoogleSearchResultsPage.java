package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleSearchResultsPage extends BasePage {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleSearchResultsPage.class);

    @FindBy(css = "p.card-section")
    private WebElement didYouMeanSection;

    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public String getDidYouMeanText() {
        LOG.info("Getting text for 'Did you mean' section.");
        return element.getText(didYouMeanSection);
    }
}
