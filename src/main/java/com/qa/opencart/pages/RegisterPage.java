package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.frameworkconstants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']");
	private By agreeChecbox = By.xpath("//input[@name='agree']");
	private By continueBtn = By.xpath("//input[@type='submit']");
	private By successMsg = By.xpath("//div[@id='content']/h1");
	private By logout = By.linkText("Logout");
	private By register = By.linkText("Register");
	
	
	
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	public boolean userRegistration(String firstName, String lastName, String email, String telephone, String password, String  subscribe) {
		eleUtil.waitForVisibilityOfElement(this.firstName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(firstName);
		
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(confirmPassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doSendKeys(subscribeYes, subscribe);
		}
		else
			eleUtil.doSendKeys(subscribeNo, subscribe);
		
		eleUtil.doClick(agreeChecbox);
		eleUtil.doClick(continueBtn);
		
		String ActsuccessMessage =  eleUtil.waitForVisibilityOfElement(this.successMsg, AppConstants.MEDIUM_DEFAULT_WAIT).getText();
		System.out.println("User Registration Success message: " + ActsuccessMessage);
		
		if (ActsuccessMessage.equals(AppConstants.USER_REGISTER_SUCCESS_MSG)) {
			eleUtil.doClick(logout);
			eleUtil.doClick(register);
			
			return true;
		}
		else
			return false;
		
		
			
		
	}
	
	

}
