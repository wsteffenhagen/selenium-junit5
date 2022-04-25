package config;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverManagerBuilder {
    private final String browserName;

    /**
     * Instantiates a new Web Driver Manager builder.
     *
     * @param browserName the browser name
     */
    public WebDriverManagerBuilder(String browserName) {
        this.browserName = browserName;
    }

    /**
     * Build local web driver manager.
     *
     * @return the web driver manager
     */
    public synchronized WebDriverManager buildLocal() {
        switch (browserName) {
            case "Chrome":
            default:
                System.setProperty("webdriver.chrome.silentOutput", "true");
                return WebDriverManager.chromedriver();
            case "Edge":
                return WebDriverManager.edgedriver();
            case "Firefox":
                return WebDriverManager.firefoxdriver();
            case "Safari":
                return WebDriverManager.safaridriver();
        }
    }
}
