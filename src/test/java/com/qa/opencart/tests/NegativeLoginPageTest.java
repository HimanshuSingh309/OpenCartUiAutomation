package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.frameworkconstants.AppConstants;
import com.qa.opencart.utils.ExcelUtils;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
@Story("OpenCart---Login Page Negative Test")
public class NegativeLoginPageTest extends BaseTest {
	
	@DataProvider
	public Object[][] getInvalidLoginTestData() {
		return new Object[][] {
			{"himansu@@gmail.com","43672tu"},
			{"himanshusingh309@gmail.com","t2374t23"},
			{"himanshusingh309@@gmail.com","Hardwork@123"},
			{"ahsjdhas","6273rte"},
			{"@$#%^","@$#%$^"}
		};
	}
	
	@DataProvider
	public Object[][] getInvalidLoginExcelTestdata() {
		Object [][] invLoginData = ExcelUtils.getTestData(AppConstants.INCORRECT_LOGIN_DATA_SHEET_NAME);
		return invLoginData;
	}
	
	@Description("Verify the error validation messages with set of invalid credentials. ")
	@Test(dataProvider = "getInvalidLoginExcelTestdata")
	public void loginWithWrongCredentialsTest(String username, String password) {
		String ActErrorMsg = loginpage.doLoginwithNegativeCredentials( username,  password);
		if(ActErrorMsg.equals(AppConstants.INVALID_LOGIN_WARNING_MESSAGE)) {
		Assert.assertEquals(ActErrorMsg, AppConstants.INVALID_LOGIN_WARNING_MESSAGE);
		}
		else
			Assert.assertEquals(ActErrorMsg, AppConstants.INVALID_LOGIN_ERROR_MESSAGE);
			
	}

}
