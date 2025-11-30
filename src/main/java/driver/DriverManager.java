package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverInstance = new ThreadLocal<>();
    private static final ThreadLocal<String> browserName = new ThreadLocal<>();

    private DriverManager() { }

    public static void setBrowser(String browser) {
        browserName.set(browser);
    }

    public static WebDriver getDriver() {
        if (driverInstance.get() == null) {
            if (browserName.get() == null) {
                throw new IllegalStateException("Browser not set");
            }
            driverInstance.set(createDriver(browserName.get()));
        }
        return driverInstance.get();
    }

    private static WebDriver createDriver(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            default -> throw new IllegalArgumentException("Unsupported Browser: " + browserName);
        };
    }

    public static void quitDriver() {
        if (driverInstance.get() != null) {
            driverInstance.get().quit();
            driverInstance.remove();
            browserName.remove();
        }
    }
}
