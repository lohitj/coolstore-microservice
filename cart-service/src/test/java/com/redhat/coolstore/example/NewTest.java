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
	    private WebDriver driver;		
		@Test				
		public void testEasy() {	
			driver.get("http://demo.guru99.com/test/guru99home/");  
			String title = driver.getTitle();				 
			Assert.assertTrue(title.contains("Demo Guru99 Page")); 		
		}	
		@BeforeTest
		public void beforeTest() {	
			String driverPath = "lib/origin/driver/chromedriver";
        		System.setProperty("webdriver.chrome.driver", driverPath);
        		ChromeOptions options = new ChromeOptions();
        		options.addArguments("--headless");
        		ChromeDriver driver = new ChromeDriver(options);
		}		
		@AfterTest
		public void afterTest() {
			driver.close();
        		driver.quit();			
		}		
}
