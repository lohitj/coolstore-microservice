package com.redhat.coolstore;
	
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;
import org.junit.Test;
		

import org.openqa.selenium.MutableCapabilities;
public class NewIT {
	@Test
	public void f() {
		WebDriver webDriver = new RemoteWebDriver(DesiredCapabilities.firefox());
		webDriver.get("http://www.google.com");
		webDriver.getTitle();
		String expectedTitle = "Google";
		String actualTitle = null;
		actualTitle = webDriver.getTitle();
		Assert.assertTrue(actualTitle.contains(expectedTitle));
		webDriver.quit();
		System.out.println("Test completed--- Selenium");
	}
}
