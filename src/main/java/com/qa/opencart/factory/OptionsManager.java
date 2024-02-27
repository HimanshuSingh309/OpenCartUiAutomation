package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	
	

	public OptionsManager(Properties prop) {

		this.prop = prop;

	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) co.addArguments("--headless");
		
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) co.addArguments("--incognito");
		
		return co;

	}
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) co.addArguments("--headless");
		
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) co.addArguments("--incognito");
		
		return fo;

	}
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) co.addArguments("--headless");
		
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) co.addArguments("--inprivate");
		
		return eo;

	}

}
