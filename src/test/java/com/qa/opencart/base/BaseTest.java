package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactoryPage;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductDetailsPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	
	protected WebDriver driver;
	protected Properties prop;
	DriverFactoryPage df;
	protected LoginPage loginpage;
	protected AccountsPage accountspage;
	protected SearchResultsPage searchResultsPage;
	protected ProductDetailsPage productDetailsPage;
	protected SoftAssert softAssert;
	protected RegisterPage registerPage;;
	
	@Parameters({"browser","browserversion","testname"})
	@BeforeTest
	public void setup(String browserName, String browserVersion, String testName) {
		df = new DriverFactoryPage();
		prop = df.initProp();
		
		if(browserName!= null) {
			prop.setProperty("browser", browserName);
			prop.setProperty("browserversion", browserVersion);
			prop.setProperty("testname", testName);
			
			
		}
		
		driver = df.initDriver(prop);
		loginpage = new LoginPage(driver);
		softAssert = new SoftAssert();
		
		
	}
	
	@AfterTest
	public void teardown() {
		
		driver.quit();
	}
	

}
