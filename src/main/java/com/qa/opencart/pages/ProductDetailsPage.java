package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.frameworkconstants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductDetailsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private By ProductHeader = By.cssSelector("div #content h1");
	private By ProductImages = By.cssSelector("ul.thumbnails img");
	private By ProductMetaData = By.xpath("//div[@id='content']//ul[@class='list-unstyled'][1]/li");
	private By ProductPricingData = By.xpath("//div[@id='content']//ul[@class='list-unstyled'][2]/li");
	private By AddToCartBtn = By.id("button-cart");
	private By AddToCartSuccessMsg = By.xpath("//div[@class='alert alert-success alert-dismissible']");

	//It maintains the random order
	private Map<String, String> ProductMap = new HashMap<String, String>();
	
	// It maintains the insertion order
	//private Map<String, String> ProductMap = new LinkedHashMap<String, String>();
	
	// It maintains the Alphabetic sorting based upon KEY
	//private Map<String, String> ProductMap = new TreeMap<String, String>();

	public ProductDetailsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);

	}

	public String getProductName() {
		String ActProductNameVal = eleUtil.doElementGetText(ProductHeader);
		System.out.println("Product Header: " + ActProductNameVal);
		return ActProductNameVal;

	}

	public int getProductImageCount() {
		int imagesCount = eleUtil.waitForVisibilityOfElements(ProductImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
		System.out.println("Product " + getProductName() + " images count: " + imagesCount);
		return imagesCount;
	}

	private void getProductMetadata() {

		List<WebElement> metaDataList = eleUtil.waitForVisibilityOfElements(ProductMetaData,
				AppConstants.SHORT_DEFAULT_WAIT);

		for (WebElement e : metaDataList) {
			String metaData = e.getText();
			String MetaKey = metaData.split(":")[0].trim();
			String MetaValue = metaData.split(":")[1].trim();
			ProductMap.put(MetaKey, MetaValue);

		}
	}

	private void getProductPricedata() {

		List<WebElement> metaPriceList = eleUtil.waitForVisibilityOfElements(ProductPricingData,
				AppConstants.SHORT_DEFAULT_WAIT);
		String ProductPrice = metaPriceList.get(0).getText();
		String ProductExTaxPrice = metaPriceList.get(1).getText().split(":")[1].trim();

		ProductMap.put("Price", ProductPrice);
		ProductMap.put("ExtaxPrice", ProductExTaxPrice);

	}
	
	public Map<String, String> getProductDetails() {
		
		ProductMap.put("Product Name: ", getProductName());
		getProductMetadata();
		getProductPricedata();
		System.out.println(ProductMap);
		
		return ProductMap;
	}
	
	public String getAddToCartSuccessMessage() {
		eleUtil.doClick(AddToCartBtn);
		String ActualsuccessMsg = eleUtil.waitForVisibilityOfElement(AddToCartSuccessMsg, AppConstants.MEDIUM_DEFAULT_WAIT).getText();
		System.out.println("Success msg on click event on Add To Cart Btn : " + ActualsuccessMsg );
		return ActualsuccessMsg;
	}

}
