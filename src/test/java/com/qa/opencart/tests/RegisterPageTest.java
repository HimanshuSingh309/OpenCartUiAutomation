package com.qa.opencart.tests;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.frameworkconstants.AppConstants;
import com.qa.opencart.utils.ExcelUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

@Story("OpenCart---Registration Page")
public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void registrationSetup() {
		registerPage = loginpage.navigateToRegisterPage();
		
	}
	
	public String getRandomEmail() {
		return "HimanshuAutomation" + System.currentTimeMillis() + "@opencart.com";
		// return "HimanshuAutomation" + UUID.randomUUID() + "@opencart.com";
	}
	
	
	@DataProvider
	public Object[][] getUserRegistrationData() {
		
		return new Object[][] {
			{"Himanshu","Thakur","9650282653","hssahj@123","Yes"},
			{"Saket","Sharma","9654195030","hssahj@123","No"},
			{"Amrit","Sinha","7417822773","hssahj@123","Yes"}
		};
	}
	
	@DataProvider
	public Object[][] getUserRegistrationExcelData() {
		Object RegData[][] = ExcelUtils.getTestData(AppConstants.REGISTER_DATA_SHEET_NAME);
		return RegData;
		
	}
	
	@Description("Verify user is able to create the accounts successfully. ")
	@Test(dataProvider = "getUserRegistrationData")
	public void userRegistrationTest(String fName, String lName, String MobileNo, String pwd, String subscribeCheckBox) {
		boolean IsRegDone = registerPage.userRegistration(fName, lName,getRandomEmail(),MobileNo,pwd,subscribeCheckBox);
		Assert.assertTrue(IsRegDone, "Registraion is Done Successfully.");
	}

}
