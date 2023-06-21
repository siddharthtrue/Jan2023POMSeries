package com.qa.opencart.tests;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

// No selenium code like driver.findelement.... here. It should be in src/main/java (page) classes

@Epic("Epic 100: Login Page Design")
@Story("US 101: design login page for open cart app with title, url, forgot pwd links, user is able to login")
public class LoginPageTest extends BaseTest
{	
	@Severity(SeverityLevel.MINOR)
	@Description("checking login page title test...")
	@Feature("title test")
	@Test
	public void loginPageTitleTest()
	{
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
//	@Severity(SeverityLevel.NORMAL)
//	@Description("checking login page url test...")
//	@Feature("url test")
//	@Test
//	public void loginPageUrlTest()
//	{
//		String actualUrl = loginPage.getLoginPageUrl();
//		Assert.assertTrue(actualUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
//	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("checking forgot pwd link test...")
	@Feature("forgot pwd test")
	@Test
	public void forgotPswExistLinkTest()
	{
		Assert.assertTrue(loginPage.isForgottenPswLinkExist());
	}
	
	@Test
	public void logoExistTest()
	{
		Assert.assertTrue(loginPage.isLogoExist());
	}
	
	@Test
	public void searchExistTest()
	{
		Assert.assertTrue(loginPage.isSearchExist());
	}
	
	@Test
	public void cartTotalBtnExistTest()
	{
		Assert.assertTrue(loginPage.isCartTotalBtnExist());
	}
	
	@Test
	public void allFooterLinksExistTest()
	{
		int actualsize = loginPage.getFooterLinks().size();
		Assert.assertEquals(actualsize, 16);
	}
	
	@Test
	public void allMenuItemsExistTest()
	{
// Below 2 lines can be used if we want to compare only count of list elements.
// As a good practice there should only be single assert statement per method.
//		int actualsize = loginPage.getMenuItems().size();
//		Assert.assertEquals(actualsize, 8);
		List<String> actualMenuItems = loginPage.getMenuItems();
		Collections.sort(actualMenuItems);
//		Collections.sort(expectedMenuItems);
//		Assert.assertEquals(actualMenuItems, expectedMenuItems);
		Collections.sort(AppConstants.EXP_MENU_ITEMS);
		Assert.assertEquals(actualMenuItems, AppConstants.EXP_MENU_ITEMS);
	}
	
	@Test
	public void newCustomerTitleExistTest()
	{
		Assert.assertTrue(loginPage.isNewCustomerTextExist());
	}
	
	@Test
	public void returningCustomerTitleExistTest()
	{
		Assert.assertTrue(loginPage.isReturningCustomerTextExist());
	}
	
	@Severity(SeverityLevel.BLOCKER)
	@Description("checking user is able to login with correct username/password test...")
	@Feature("login test")
	@Test
	public void webLoginTest()
	{
//		accPage = loginPage.doLogin("janautomation@gmail.com", "Selenium@12345"); This line is replaced with below
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
//		Assert.assertTrue(accPage.getAccPageTitle().equals("My Account"));
	}
}
