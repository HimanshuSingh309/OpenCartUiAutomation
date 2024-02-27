package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.frameworkconstants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//By Locators
		private By logout = By.linkText("Logout");
		private By search = By.cssSelector("div #search input");
		private By seacrchIcon = By.cssSelector("div #search button");
		private By account_headers = By.xpath("//div[@id='content']/h2");
	
	
		// Page Const
		public AccountsPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementUtil(this.driver);
			
		}
				
		// Page actions/ methods
		public String getAccountPageTitle() {		
			String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNT_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
			System.out.println("Account Page Title: " + title);
			return title;
		}
		
		public String getAccountPageURL() {
			
			String url = eleUtil.waitForURLContains(AppConstants.ACCOUNT_PAGE_URL_FRACTIONS, AppConstants.SHORT_DEFAULT_WAIT);		 
			System.out.println("Account Page URL: " + url);
			return url;
		}
		
		public boolean isLogoutLinkVisible() {
			return eleUtil.waitForVisibilityOfElement(logout, AppConstants.MEDIUM_DEFAULT_WAIT).isDisplayed();
			
		}
		
		public void logout() {
			if(isLogoutLinkVisible()) {
				eleUtil.doClick(logout);
			}
		}
		
		public boolean isSearchFieldvisible() {
			return eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).isDisplayed();
		}
		
		public List<String> getAccountHeaders() {
			List<WebElement> headersList= eleUtil.waitForVisibilityOfElements(account_headers, AppConstants.SHORT_DEFAULT_WAIT);
			List<String> headersValueList = new ArrayList<String>();
			for(WebElement e : headersList) {
				String text = e.getText();
				 headersValueList.add(text);
				
			}
			return  headersValueList;
			
		}
		
		public SearchResultsPage doSearch(String SearchKey) {
			eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).clear();
			eleUtil.waitForVisibilityOfElement(search, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(SearchKey);
			eleUtil.doClick(seacrchIcon);
			return new SearchResultsPage(driver);
			
		}
	

}
