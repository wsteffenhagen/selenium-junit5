package pageobjects.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public final class ElementUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ElementUtil.class);
    private WebDriver driver;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
    }

    private FluentWait<WebDriver> defaultWait() {
        return customWait(20);
    }

    private FluentWait<WebDriver> customWait(int secondsToWait) {
        return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(secondsToWait))
                .pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
    }

    /**
     * Waits for the specified element to be visible on the page.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            The visible WebElement
     */
    public WebElement waitForVisible(WebElement element) {
        try {
            return defaultWait().until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out waiting for visibility of element: " + element);
        }
    }

    /**
     * Waits for the element matching the specified By selector to be visible
     * on the page.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @return
     *            The visible WebElement
     */
    public WebElement waitForVisible(By by) {
        try {
            return defaultWait().until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out waiting for visibility of element: " + by);
        }
    }

    /**
     * Waits for the specified element to be clickable on the page.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            The clickable WebElement
     */
    public WebElement waitForClickable(WebElement element) {
        try {
            return defaultWait().until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out waiting for element to be clickable: " + element);
        }
    }

    /**
     * Waits for the element matching the specified By selector to be
     * clickable on the page.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @return
     *            The clickable WebElement
     *
     */
    public WebElement waitForClickable(By by) {
        try {
            return defaultWait().until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out waiting for element to be clickable: " + by);
        }
    }

    /**
     * Waits for the specified element to be clickable and then clicks it.
     *
     * @param element
     *            WebElement object representing an element on the page
     */
    public void click(WebElement element) {
        waitForVisible(element);
        try {
            waitForClickable(element).click();
        } catch (ElementClickInterceptedException e) {
            LOG.info("Element click intercepted, trying JS click.");
            clickWithJS(element);
        }
    }

    /**
     * Waits for the element matching the specified By selector to be clickable
     * and then clicks it.
     *
     * @param by
     *            By object representing a locator to an element on the page
     */
    public void click(By by) {
        waitForVisible(by);
        try {
            waitForClickable(by).click();
        } catch (ElementClickInterceptedException e) {
            LOG.warn("Element click intercepted, trying JS click.");
            clickWithJS(by);
        }
    }

    /**
     * Waits for the specified element to be clickable and then clicks it
     * with JavaScript.
     *
     * @param element
     *            WebElement object representing an element on the page
     */
    public void clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", waitForClickable(element));
    }

    /**
     * Waits for the element matching the specified By selector to be clickable
     * and then clicks it with JavaScript.
     *
     * @param by
     *            By object representing a locator to an element on the page
     */
    public void clickWithJS(By by) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", waitForClickable(by));
    }

    /**
     * Waits for the specified element to be visible and enters the given
     * char sequence in it.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @param keys
     *            CharSequence to send to the element
     */
    public void sendKeys(WebElement element, CharSequence... keys) {
        waitForVisible(element).sendKeys(keys);
    }

    /**
     * Waits for the element matching the specified By selector to be visible
     * and enters the given char sequence in it.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @param keys
     *            CharSequence to send to the element
     */
    public void sendKeys(By by, CharSequence... keys) {
        waitForVisible(by).sendKeys(keys);
    }

    /**
     * Waits for the specified element to be visible, clears its current value,
     * and enters the given char sequence as the new value.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @param keys
     *            CharSequence to send to the element
     */
    public void clearAndSendKeys(WebElement element, CharSequence... keys) {
        waitForVisible(element);
        element.clear();
        sendKeys(element, keys);
    }

    /**
     * Waits for the element matching the specified By selector to be visible,
     * clears its current value, and enters the given char sequence as the
     * new value.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @param keys
     *            CharSequence to send to the element
     */
    public void clearAndSendKeys(By by, CharSequence... keys) {
        clearAndSendKeys(waitForVisible(by), keys);
    }

    /**
     * Fluently determines if an element exists on the page using the given
     * WebElement object.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @param secondsToWait
     *            How long to wait for element presence in seconds
     * @return
     *            True if the desired element is present on the page, otherwise
     *            false.
     */
    public boolean isPresent(WebElement element, int secondsToWait) {
        try {
            customWait(secondsToWait).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Fluently determines if an element exists on the page using the given
     * WebElement object and a default timeout of 2 seconds.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            True if the desired element is present on the page, otherwise
     *            false.
     */
    public boolean isPresent(WebElement element) {
        return isPresent(element, 2);
    }

    /**
     * Fluently determines if an element exists on the page using the given
     * By selector.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @param secondsToWait
     *            How long to wait for element presence in seconds
     * @return
     *            True if the desired element is present on the page, otherwise
     *            false.
     */
    public boolean isPresent(By by, int secondsToWait) {
        try {
            customWait(secondsToWait).until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Fluently determines if an element exists on the page using the given
     * By selector and a default timeout of 2 seconds.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @return
     *            True if the desired element is present on the page, otherwise
     *            false.
     */
    public boolean isPresent(By by) {
        return isPresent(by, 2);
    }

    /**
     * Fluently determines if an element is visible on the page using the given
     * WebElement object.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @param secondsToWait
     *            How long to wait for element visibility in seconds
     * @return
     *            True if the desired element is visible on the page, otherwise
     *            false.
     */
    public boolean isVisible(WebElement element, int secondsToWait) {
        try {
            customWait(secondsToWait).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Fluently determines if an element is visible on the page using the given
     * WebElement object and a default timeout of 2 seconds.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            True if the desired element is visible on the page, otherwise
     *            false.
     */
    public boolean isVisible(WebElement element) {
        return isVisible(element, 2);
    }

    /**
     * Fluently determines if an element is visible on the page using the given
     * By selector.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @param secondsToWait
     *            How long to wait for element visibility in seconds
     * @return
     *            True if the desired element is visible on the page, otherwise
     *            false.
     */
    public boolean isVisible(By by, int secondsToWait) {
        try {
            customWait(secondsToWait).until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Fluently determines if an element is visible on the page using the given
     * By selector and a default timeout of 2 seconds.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @return
     *            True if the desired element is visible on the page, otherwise
     *            false.
     */
    public boolean isVisible(By by) {
        return isVisible(by, 2);
    }

    /**
     * Scrolls specified element into view.
     *
     * @param element
     *            WebElement object representing an element on the page
     */
    public void scrollIntoView(WebElement element) {
        if (isPresent(element)) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            } catch (TimeoutException e) {
                LOG.error("Could not scroll element into view: {}", e);
            }
        } else {
            LOG.error("Unable to locate element: {}", element);
        }
    }

    /**
     * Scrolls element matching the specified By selector into view.
     *
     * @param by
     *            By object representing a locator to an element on the page
     */
    public void scrollIntoView(By by) {
        scrollIntoView(waitForVisible(by));
    }

    /**
     * Waits for the specified element to be visible and then returns
     * its text.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            The element's text as a string
     */
    public String getText(WebElement element) {
        return waitForVisible(element).getText();
    }

    /**
     * Waits for the element matching the specified By selector to be visible
     * and then returns its text.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @return
     *            The element's text as a string
     */
    public String getText(By by) {
        return waitForVisible(by).getText();
    }

    /**
     * Waits for the specified element to be visible and then returns
     * its value attribute.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            The element's value attribute as a string
     */
    public String getValue(WebElement element) {
        return waitForVisible(element).getAttribute("value");
    }

    /**
     * Waits for the element matching the specified By selector to be visible
     * and then returns its value attribute.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @return
     *            The element's value attribute as a string
     */
    public String getValue(By by) {
        return waitForVisible(by).getAttribute("value");
    }

    /**
     * Waits for the specified element to be visible and then returns
     * the specified attribute.
     * @param element
     *            WebElement object representing an element on the page
     * @param attribute
     *            The attribute of the element to return
     * @return
     *            The element's specified attribute as a string
     */
    public String getAttribute(WebElement element, String attribute) {
        if (isPresent(element)) {
            return element.getAttribute(attribute);
        } else {
            LOG.error("Unable to locate element: {}", element);
            return "";
        }
    }

    /**
     * Waits for the specified element to be visible and then returns
     * its href attribute.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            The element's href attribute as a string
     */
    public String getHref(WebElement element) {
        if (isPresent(element)) {
            return element.getAttribute("href");
        } else {
            LOG.error("Unable to locate element: {}", element);
            return "";
        }
    }

    /**
     * Waits for the specified element to be visible and then returns
     * its content attribute.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            The element's content attribute as a string
     */
    public String getContentAttribute(WebElement element) {
        if (isPresent(element)) {
            return element.getAttribute("content");
        } else {
            LOG.error("Unable to locate element: {}", element);
            return "";
        }
    }

    /**
     * Waits for the specified element to be visible and then returns
     * its innerText attribute.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @return
     *            The element's innerText attribute as a string
     */
    public String getInnerText(WebElement element) {
        if (isPresent(element)) {
            return element.getAttribute("innerText");
        } else {
            LOG.error("Unable to locate element: {}", element);
            return "";
        }
    }

    /**
     * Waits for the specified element to contain the given text.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @param text
     *            Text to wait for in element
     * @return
     *            True if the desired text is present in the element
     */
    public void waitForTextToBePresent(WebElement element, String text) {
        try {
            defaultWait().until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out waiting for text to be present in element: " + element);
        }
    }

    /**
     * Waits for the element matching the specified By selector to contain
     * the given text.
     *
     * @param by
     *            By object representing a locator to an element on the page
     * @param text
     *            Text to wait for in element
     * @return
     *            True if the desired text is present in the element
     */
    public void waitForTextToBePresent(By by, String text) {
        try {
            defaultWait().until(ExpectedConditions.textToBePresentInElement(driver.findElement(by), text));
        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out waiting for text to be present in element: " + by);
        }
    }

    /**
     * Fluently determines if specified text is present in an element on the
     * page represented by the given WebElement object.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @param text
     *            Text to check for in the element
     * @param secondsToWait
     *            How long to wait for text to be present in seconds
     * @return
     *            True if element contains the given text, otherwise false.
     */
    public boolean isTextPresent(WebElement element, String text, int secondsToWait) {
        try {
            customWait(secondsToWait).until(ExpectedConditions.textToBePresentInElement(element, text));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Fluently determines if text is present in an element on the page represented
     * by the given WebElement object using a default timeout of 2 seconds.
     *
     * @param element
     *            WebElement object representing an element on the page
     * @param text
     *            Text to check for in the element
     * @return
     *            True if element contains the given text, otherwise false.
     */
    public boolean isTextPresent(WebElement element, String text) {
        return isTextPresent(element, text, 2);
    }

    /**
     * Waits for the specified select menu to be visible and then selects option
     * based on given text.
     *
     * @param selectElement
     *            WebElement object representing a select menu on the page
     * @param text
     *            Text for select menu option to select
     */
    public void selectOptionByVisibleText(WebElement selectElement, String text) {
        Select select = new Select(waitForVisible(selectElement));
        select.selectByVisibleText(text);
    }

    /**
     * Waits for the specified frame element to be visible and then switches
     * driver context to it.
     *
     * @param frame
     *            WebElement object representing a frame element on the page
     */
    public void waitForFrameAndSwitchToIt(WebElement frame) {
        try {
            defaultWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out switching to frame: " + frame);
        }
    }

    /**
     * Moves cursor to the element.
     *
     * @param element
     *            WebElement object representing an element on the page
     */
    public void moveToElement(WebElement element){
        if (isPresent(element)) {
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(element);
                actions.perform();
            }
            catch (TimeoutException e) {
                LOG.error("Could not move to element: ", e);
            }
        }
        else {
            LOG.error("Unable to locate element: {}", element);
        }
    }
}
