package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class AccountsPage
{
	private WebDriver driver;
	private ElementUtils eleUtil;

	public AccountsPage(WebDriver driver)
	{
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}

	private By logout = By.linkText("Logout");
	private By myAccount = By.linkText("My Account");
	private By accHeaders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	
	//3. page actions:
	public String getAccPageTitle()
	{
//		String title = driver.getTitle();
//		System.out.println("Acc page title : " + title);
//		return title;
		return eleUtil.waitForTitleIsAndCapture(AppConstants.ACCOUNTS_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}
	
	public boolean isLogoutLinkExist()
	{
		//return driver.findElement(logout).isDisplayed();
		return eleUtil.checkElementIsDisplayed(logout);
	}
	
	public boolean isMyAccountLinkExist()
	{
		// return driver.findElement(myAccount).isDisplayed();
		return eleUtil.checkElementIsDisplayed(myAccount);
	}
	
	public List<String> getAccountPageHeadersList()
	{
//		List<WebElement> headersList = driver.findElements(accHeaders);
		List<WebElement> headersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.SHORT_DEFAULT_WAIT);
		List<String> headersValList = new ArrayList<String>();
		for(WebElement e : headersList)
		{
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}
	
	public ResultsPage doSearch(String searchTerm)
	{
//		driver.findElement(search).sendKeys(searchTerm);
//		driver.findElement(searchIcon).click();
		eleUtil.waitForElementVisible(search, 10);
		eleUtil.doSendKeys(search, searchTerm);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	}
}
