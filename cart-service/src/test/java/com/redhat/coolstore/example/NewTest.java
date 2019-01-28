package com.redhat.coolstore;
import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;		
import org.testng.annotations.Test;	
import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;
public class NewTest {
 
    WebDriver driver;
 
    @BeforeTest
    public void beforeTest() {
        String webDriverKey = "webdriver.chrome.driver";
        String webDriverValue = System.getProperty("user.dir") +
                "/target/tmp_webdrivers/chromedriver-mac-32bit";
        System.setProperty(webDriverKey, webDriverValue);
        driver = new ChromeDriver();
        driver.get("http://www.google.com");
    }
 
//todo: test
 
    @AfterTest
    public void afterTest() {
        driver.quit();
    }	
}
