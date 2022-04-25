package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;

public class Configuration {
    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    /**
     * Gets web driver manager.
     *
     * @param testName the test name
     * @return the web driver manager
     */
    public synchronized static WebDriverManager getWebDriverManager() {
        // Sets Selenium WebDriver logger level
        setSeleniumLoggerLevel(Level.SEVERE);

        Config config = getConfig();

        return new WebDriverManagerBuilder(config.getString("browser")).buildLocal();
    }

    /**
     * Sets selenium logger level.
     *
     * @param level the level
     */
    private static void setSeleniumLoggerLevel(Level level) {
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(level);
    }

    /**
     * Gets config.
     *
     * @return the config
     */
    private static Config getConfig() {
        Config config = ConfigFactory.load();

        if (config.hasPath("conf")) {
            String conf = config.getString("conf");
            LOG.info("Setting config to '{}'", conf);
            config = config.getConfig(conf);
        } else {
            config = config.getConfig("config.current");
        }

        return config;
    }

    /**
     * Gets base url.
     *
     * @return the base url
     */
    public static String getBaseUrl() {
        Config config = ConfigFactory.load();
        return config.getString("config.baseurl");
    }
}
