package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.frameworkconstants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// By locators
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwd = By.linkText("Forgotten Password1");
	private By logo = By.cssSelector("img[title='naveenopencart']");
	private By registerLink = By.linkText("Register");
	private By invalidLoginMsg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	
	// Page Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
		
	}
	
	// Page actions/ methods
	
	public String getLoginPageTitle() {		
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login Page Title: " + title);
		return title;
	}
	
	public String getLoginPageURL() {
		
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTIONS, AppConstants.SHORT_DEFAULT_WAIT);		 
		System.out.println("Login Page URL: " + url);
		return url;
	}
	
	public boolean isForgotPwdLinkVisible() {
		return eleUtil.waitForVisibilityOfElement(forgotPwd, 5).isDisplayed();
		
	}
	
	public boolean isLogoVisible() {
		return eleUtil.waitForVisibilityOfElement(logo, 5).isDisplayed();
	}
	
	@Step("Username is {0} and password is {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println(username + ":  " + pwd);
		eleUtil.waitForVisibilityOfElement(userName, 10).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);		
		return new AccountsPage(driver);
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForVisibilityOfElement(registerLink, 10).click();
		
		return new RegisterPage(driver);
		
	}
	
	@Step("Do login with negative test data.")
	public String doLoginwithNegativeCredentials(String username, String pwd) {
		eleUtil.waitForVisibilityOfElement(userName, 10).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);		
		String errorMsg = eleUtil.doElementGetText(invalidLoginMsg);
		
		if(errorMsg.equalsIgnoreCase(AppConstants.INVALID_LOGIN_WARNING_MESSAGE)) {
		eleUtil.waitForVisibilityOfElement(userName, 10).clear();
		eleUtil.waitForVisibilityOfElement(password, 10).clear();
		System.out.println("Invalid login Warning  msg: " + errorMsg);
		return errorMsg;
		}
		else
		{
			eleUtil.waitForVisibilityOfElement(userName, 10).clear();
			eleUtil.waitForVisibilityOfElement(password, 10).clear();
			System.out.println("Invalid login Error  msg: " + errorMsg);
			return errorMsg;
		}
		
		
	}
	
	
	
	
	

}
