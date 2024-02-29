package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

@Story("OpenCart---Product Details Page")
public class ProductDetailsPageTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetup() {
		accountspage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@DataProvider
	public Object[][] getSearchData() {
		
		return new Object [][] {
			{"MacBook","MacBook Pro", 4},
			{"MacBook","MacBook Air", 4},
			{"imac","iMac", 3},
			{"Samsung", "Samsung SyncMaster 941BW", 1}
			
		};
	}
	@Description("Verify the product images count. ")
	@Test(dataProvider = "getSearchData")
	public void productImageCountTest(String searchKey, String productName, int imageCount) {
		searchResultsPage = accountspage.doSearch(searchKey);
		productDetailsPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productDetailsPage.getProductImageCount(), imageCount);
		
	}
	
	@Description("Verify that user is able to see the product information. ")
	@Test
	public void productInfoTest() {
		searchResultsPage = accountspage.doSearch("MacBook Pro");
		productDetailsPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDetailsMap = productDetailsPage.getProductDetails();
		
		softAssert.assertEquals(productDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productDetailsMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productDetailsMap.get("Price"), "$2,000.00");
		softAssert.assertEquals(productDetailsMap.get("ExtaxPrice"), "$2,000.00");
		
		softAssert.assertAll();
		
	}
	
	@Description("Verify that user is able to see the success message while adding the product into Cart. ")
	@Test
	public void addToCartSuccessMsgTest() {
		searchResultsPage = accountspage.doSearch("MacBook");
		productDetailsPage = searchResultsPage.selectProduct("MacBook Air");
		String actAddToCartSuccessMsg = productDetailsPage.getAddToCartSuccessMessage();
		Assert.assertTrue(actAddToCartSuccessMsg.contains("Success: You have added "));
		
	}

}
