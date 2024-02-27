package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.FrameworkExceptions;

public class DriverFactoryPage {

	WebDriver driver;
	protected Properties prop;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static String highlight = null;

	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		//String browserName = System.getProperty("browser");
		highlight = prop.getProperty("highlight");
		
		
		System.out.println("Browser name is: " + browserName);
		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {
		case "chrome": {

			driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(driver);
			break;
		}

		case "firefox": {

			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(driver);
			break;
		}
		case "edge": {

			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(driver);
			break;
		}
		case "safari": {

			driver = new SafariDriver();
			tlDriver.set(driver);
			break;
		}
		default:
			System.out.println("Please pass the correct browser. " + browserName);
			throw new FrameworkExceptions("Now Browser found");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
		}
	
	public Properties initProp() {
		
		FileInputStream ip = null;
		prop = new Properties();
		
		String envName = System.getProperty("env");
		System.out.println("Env name is: " + envName);
		
		try {
		if (envName == null) {
			ip = new FileInputStream("./src/tests/resources/config/config.properties");
			
		} else {
			switch (envName.toLowerCase().trim()) {
			
			case "qa": 
				ip = new FileInputStream("./src/tests/resources/config/config.qa.properties");
				break;
			case "dev": 
				ip = new FileInputStream("./src/tests/resources/config/config.dev.properties");
				break;
			case "stage": 
				ip = new FileInputStream("./src/tests/resources/config/config.stage.properties");
				break;
			case "prod": 
				ip = new FileInputStream("./src/tests/resources/config/config.properties");
				break;
			case "uat": 
				ip = new FileInputStream("./src/tests/resources/config/config.uat.properties");
				break;
			
			default:
				System.out.println("Please pass the correct env name : " + envName);
				throw new FrameworkExceptions("Wrong env name: " + envName );
			}
			
		   }
		}
		catch(FileNotFoundException e){
			
		}
			try {
				prop.load(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		         
		return prop;
	}
	public static String getScreenshot(String methodName) {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
