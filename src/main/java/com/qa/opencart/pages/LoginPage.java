package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtils;

import io.qameta.allure.Step;

// No assertions in page class. Assertions should only be in src/test/java (tests) classes.
// Page class methods should always return something, otherwise assert would not be possible in test class. 

public class LoginPage
{
	private WebDriver driver;
	private ElementUtils eleUtil;

// 1. Constructor of the page class
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		eleUtil = new ElementUtils(driver); //or eleUtil = new ElementUtils(this.driver);
	}

// 2. Private by locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgottenPswLink = By.linkText("Forgotten Password");
	private By footerLinks = By.xpath("//footer//a");
	private By logo = By.xpath("//img[@title='naveenopencart']");
	private By search = By.id("search");
	private By cartTotalBtn = By.cssSelector("#cart-total");
	private By menuItems = By.cssSelector("ul[class='nav navbar-nav'] > li");
	private By customerTitle = By.xpath("//h2[text()='New Customer']");
	private By returningCustomerTitle = By.xpath("//h2[text()='Returning Customer']");
	private By loginErrorMessage = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	private By registerLink = By.xpath("//div[@class='list-group']/a[text()='Register']");
	
//3. Public page actions / methods
	@Step("getting login page title")
	public String getLoginPageTitle()
	{
//		String title = driver.getTitle();
//		System.out.println("Login page title: " +title);
//		return title;
		return eleUtil.waitForTitleIsAndCapture(AppConstants.LOGIN_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}
	
	@Step("getting login page url")
	public String getLoginPageUrl()
	{
//		String url = driver.getCurrentUrl();
//		System.out.println("Login page url: " +url);
//		return url;
		return eleUtil.waitForURLAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE, AppConstants.MEDIUM_DEFAULT_WAIT);
	}
	
	@Step("checking forgot pwd link exist on the login page")
	public boolean isForgottenPswLinkExist()
	{
		//return driver.findElement(forgottenPswLink).isDisplayed();
		return eleUtil.checkElementIsDisplayed(forgottenPswLink);
	}
	
	@Step("getting footer links")
	public List<String> getFooterLinks()
	{
		//List<WebElement> footerLinksList = driver.findElements(footerLinks);
		List<WebElement> footerLinksList = eleUtil.waitForElementsVisible(footerLinks, AppConstants.SHORT_DEFAULT_WAIT);
		List<String> footerList = new ArrayList<String>();
		for(WebElement e: footerLinksList)
		{
			String text = e.getText();
			System.out.println(text);
			footerList.add(text);
		}
		return footerList;
	}
	
	public boolean isLogoExist()
	{
//		return driver.findElement(logo).isDisplayed();
		return eleUtil.checkElementIsDisplayed(logo);
	}
	
	public boolean isSearchExist()
	{
//		return driver.findElement(search).isDisplayed();
		return eleUtil.checkElementIsDisplayed(search);
	}
	
	public boolean isCartTotalBtnExist()
	{
//		return driver.findElement(cartTotalBtn).isDisplayed();
		return eleUtil.checkElementIsDisplayed(cartTotalBtn);
	}
	
	public List<String> getMenuItems()
	{
//		List<WebElement> menuItemsList = driver.findElements(menuItems);
		List<WebElement> menuItemsList = eleUtil.waitForElementsVisible(menuItems, AppConstants.SHORT_DEFAULT_WAIT);
		List<String> menuItemsListText = new ArrayList<String>();
		for(WebElement e: menuItemsList)
		{
			String text = e.getText();
			System.out.println(text);
			menuItemsListText.add(text);
		}
		return menuItemsListText;
	}
	
	public boolean isNewCustomerTextExist()
	{
//		return driver.findElement(customerTitle).isDisplayed();
		return eleUtil.checkElementIsDisplayed(customerTitle);
	}
	
	public boolean isReturningCustomerTextExist()
	{
//		return driver.findElement(returningCustomerTitle).isDisplayed();
		return eleUtil.checkElementIsDisplayed(returningCustomerTitle);
	}
	
	@Step("login with username {0} and password {1}")
	public AccountsPage doLogin(String userName, String pwd)
	{
//		driver.findElement(emailId).sendKeys(userName);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
		System.out.println("Correct credentials are: " + userName + ":" + pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(emailId, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
// since clicking button navigates to next page so its this method's resp. to return object of next landing page.
// This is called page chaining model.
	}
	
	@Step("login with wrong username {0} and password {1}")
	public boolean doLoginWithWrongCredentials(String userName, String pwd)
	{
		System.out.println("Correct credentials are: " + userName + ":" + pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(emailId, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		String errorMsg = eleUtil.doGetElementText(loginErrorMessage);
		System.out.println(errorMsg);
		if(errorMsg.contains(AppConstants.LOGIN_ERROR_MESSAGE))
		{
			return true;
		}
		return false;
	}
	
	public RegisterPage navigateToRegisterPage()
	{
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
