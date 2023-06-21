package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class LoginPageNegativeTest extends BaseTest
{
/*	@DataProvider
	public Object[][] incorrectLoginTestData()
	{
		return new Object[][]
		{
			{"@#$$auto123", "Selenium@12345"},
//			{" ", "Selenium@12345"},
//			{"", "Selenium@12345"},
			{"janautomation@gmail.com", "123456"},
//			{"janautomation@gmail.com", " "},
//			{"janautomation@gmail.com", ""},
			{"@gmail.com", "abc"},
//			{" ", " "},
//			{"", ""}
		};
	}
	
	@Test(dataProvider="incorrectLoginTestData")
	public void loginWithWrongCredentialsTest(String userName, String password)
	{
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(userName, password));
	}
*/
// Below is data provider with excel. Above one is without excel. (one more ex. in registerpagetest.java)

	@DataProvider(name = "loginExcelData")
	public Object[][] getloginExcelTestData()
	{
		Object regData[][] = ExcelUtil.getTestData(AppConstants.LOGIN_SHEET_NAME);
		return regData;
	}
	
	@Test(dataProvider="loginExcelData")
	public void loginAccountTest(String userName, String password)
	{
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(userName, password));
//		String actUserRegSuccessMsg = registerPage.doRegisterAccount(fName, lName, getRandomEmailID(), phone, psw, conPsw, subscr);
//		Assert.assertEquals(actUserRegSuccessMsg, AppConstants.USER_REG_SUCCESS_MSG);
	}
}
