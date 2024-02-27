package com.qa.opencart.frameworkconstants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_URL_FRACTIONS = "account/login";
	public static final String ACCOUNT_PAGE_TITLE = "My Account";
	public static final String ACCOUNT_PAGE_URL_FRACTIONS = "route=account/account";
	
	public static final int SHORT_DEFAULT_WAIT = 5;
	public static final int MEDIUM_DEFAULT_WAIT = 10;
	public static final int LONG_DEFAULT_WAIT = 20;
	public static final int POLLING_DEFAULT_WAIT = 2;
	public static final int ACCOUNTS_HEADER_LIST_COUNT = 4;
	
	public static final List<String> ACCOUNTS_PAGE_HEADERS_LIST = Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");
	public static final String USER_REGISTER_SUCCESS_MSG = "Your Account Has Been Created!";
	public static final String REGISTER_DATA_SHEET_NAME = "Register";
	public static final String INCORRECT_LOGIN_DATA_SHEET_NAME = "incorrectlogindata";
	public static final String INVALID_LOGIN_ERROR_MESSAGE = "Warning: No match for E-Mail Address and/or Password.";
	public static final String INVALID_LOGIN_WARNING_MESSAGE = "Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.";

}
