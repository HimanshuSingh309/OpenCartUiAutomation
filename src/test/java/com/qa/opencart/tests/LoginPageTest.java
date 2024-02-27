package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.frameworkconstants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

@Story("OpenCart-- Login Page")
public class LoginPageTest extends BaseTest {
	
	@Description("Verify the login page title is appearing correctly. ")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String ActTitle = loginpage.getLoginPageTitle();
		Assert.assertEquals(ActTitle, AppConstants.LOGIN_PAGE_TITLE);
		
	}
	
	@Description("Verify the login URL is appearing correctly. ")
	@Test(priority = 2)
	public void loginPageURLTest() {
		String ActURL = loginpage.getLoginPageURL();
		Assert.assertTrue(ActURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTIONS));
		
	}
	
	@Description("Verify the forgot password link is appearing on login page. ")
	@Test(priority = 3)
	public void loginPageForgotPwdLinkVisible() {
		Assert.assertTrue(loginpage.isForgotPwdLinkVisible());		
		
	}
	
	@Description("Verify the application logo is appearing on login page. ")
	@Test(priority = 4)
	public void loginPageLogoVisible() {
		Assert.assertTrue(loginpage.isLogoVisible());		
		
	}
	@Description("Verify the user is able to login successfully. ")
	@Test(priority = 5)
	public void loginPageUserTest() {
		accountspage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accountspage.isLogoutLinkVisible());
		
	}
	
	

}
