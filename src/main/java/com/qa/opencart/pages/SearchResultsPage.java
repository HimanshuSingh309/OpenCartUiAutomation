package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.frameworkconstants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
		
	}
	
	@Step("Select the product")
	public ProductDetailsPage selectProduct(String ProductName) {
		eleUtil.waitForVisibilityOfElement(By.linkText(ProductName), AppConstants.MEDIUM_DEFAULT_WAIT).click();
		return new ProductDetailsPage(driver);
	}

}
