package com.redhat.coolstore;
	
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;
	import org.junit.Test;
		

import org.openqa.selenium.MutableCapabilities;
public class NewIT {
 
    WebDriver driver;
 
    @Test
    public void beforeTest() {
        String webDriverKey = "webdriver.chrome.driver";
        String webDriverValue = System.getProperty("user.dir") +
                "/target/tmp_webdrivers/chromedriver-linux-64bit";
        System.setProperty(webDriverKey, webDriverValue);
        driver = new ChromeDriver();
        driver.get("http://www.google.com");
    }
 
//todo: test
 
  /* @AfterTest
    public void afterTest() {
//         driver.quit();
    }	*/
}