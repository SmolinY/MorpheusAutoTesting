package MorpheusAutoTesting;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Base class for test classes
 */
public class TestBase {
  protected static String baseUrl;

  protected WebDriver driver;
  protected JavascriptExecutor jse;
  protected WebDriverWait wait;

  public final Logger logger = Logger.getLogger(getClass());

  @BeforeSuite
  public void initTestSuite() throws IOException {
    baseUrl = "http://tst-mrph-srv.csgrp.com";
  }

  @BeforeMethod
  public void initWebDriver() {
    System.setProperty("webdriver.chrome.driver", "c:\\Program Files\\chromedriver\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    jse = (JavascriptExecutor) driver;
    wait = new WebDriverWait(driver, 5);
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() {
    driver.quit();
  }

  protected void log(String msg) {
    logger.info("\r\n\r\n>>>" + msg + "<<<\r\n");
  }
}
