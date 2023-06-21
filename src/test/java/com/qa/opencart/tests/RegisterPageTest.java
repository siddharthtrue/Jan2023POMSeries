package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

// This code contains dataprovider with and without excel.
// More excel utilities are here - https://github.com/naveenanimation20/ExcelUtil/tree/master/src/main/java/com/navlabs/excel/reader

public class RegisterPageTest extends BaseTest
{	
	@BeforeClass
	public void regSetup()
	{
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmailID()
	{
		return "testautomation" + System.currentTimeMillis() + "@gmail.com";
	}

//----------------------------------------------------------------------------------------------------------
//	@DataProvider(name = "regData")
//	public Object[][] getAccountRegisterData()
//	{
//		return new Object[][]
//		{
//			{"Alpha", "Gupta", "8888555520", "FirstUser", "FirstUser", "yes"},
//			{"Beta", "Verma", "8888555521", "SecUser", "SecUser", "no"},
//			{"Gamma", "Sharma", "8888555522", "ThirdUser", "ThirdUser", "yes"},
//		};
//	}

// Below is data provider with excel. Above one is without excel.
// In excel, telephone no. was printing as 8.8888555520 so the solution is to add ' in telephone in excel sheet
	
	@DataProvider(name = "regExcelData")
	public Object[][] getRegExcelTestData()
	{
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	@Test(dataProvider="regExcelData")
	public void registerAccountTest(String fName, String lName, String phone, String psw, String conPsw, String subscr)
	{
		String actUserRegSuccessMsg = registerPage.doRegisterAccount(fName, lName, getRandomEmailID(), phone, psw, conPsw, subscr);
		Assert.assertEquals(actUserRegSuccessMsg, AppConstants.USER_REG_SUCCESS_MSG);
	}

//----------------------------------------------------------------------------------------------------------

	
}
