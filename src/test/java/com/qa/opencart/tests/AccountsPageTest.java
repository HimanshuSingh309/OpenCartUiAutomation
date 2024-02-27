package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.frameworkconstants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Story;


@Story("OpenCart---Account Page")
public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accSetup() {
		accountspage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Description("Verify the Account page title is appearing correctly. ")
	@Test
	public void accountPageTitleTest() {
		String ActTitle = accountspage.getAccountPageTitle();
		Assert.assertEquals(ActTitle, AppConstants.ACCOUNT_PAGE_TITLE);
		
	}
	
	@Description("Verify the account page URL is appearing correctly. ")
	@Test
	public void accountPageURLTest() {
		String ActURL = accountspage.getAccountPageURL();
		Assert.assertTrue(ActURL.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTIONS));
		
	}
	
	@Description("Verify the logout link is appearing. ")
	@Test
	public void logoutLinkVisibleTest() {
		Assert.assertTrue(accountspage.isLogoutLinkVisible());
		System.out.println("Logout link visible.");
		
	}
	
	@Description("Verify the search field is visible at header of the page. ")
	@Test
	public void isSearchFieldVisibleTest() {		
		Assert.assertTrue(accountspage.isSearchFieldvisible());
		System.out.println("Search field visible.");
		
	}
	
	@Description("Verify the user is able to logout successfully. ")
	@Test
	public void logoutTest() {
		accountspage.logout();
		System.out.println("User logged out.");
		
	}
	
	@Description("Verify the count of the headers on accounts page. ")
	@Test
	public void accountHeadersCountTest() {
		List<String> accPageHeaderList = accountspage.getAccountHeaders();
		System.out.println(accPageHeaderList);
		Assert.assertEquals(accPageHeaderList.size(), AppConstants.ACCOUNTS_HEADER_LIST_COUNT);

	}
	
	@Description("Verify the correct header names are showing. ")
	@Test
	public void accountHeadersTest() {
		List<String> accPageHeaderList = accountspage.getAccountHeaders();
		System.out.println(accPageHeaderList);
		Assert.assertEquals(accPageHeaderList, AppConstants.ACCOUNTS_PAGE_HEADERS_LIST);		

	}
	
	@Description("Verify user is able to search the product successfully. ")
	@Test
	public void searchTest() {
		searchResultsPage = accountspage.doSearch("Macbook");
		productDetailsPage = searchResultsPage.selectProduct("MacBook Pro");
		String ActualProductName = productDetailsPage.getProductName();
		Assert.assertEquals(ActualProductName, "MacBook Pro");
		
	}
}
