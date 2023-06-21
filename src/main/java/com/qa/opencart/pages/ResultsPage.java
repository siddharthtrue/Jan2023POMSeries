package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class ResultsPage
{
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	public ResultsPage(WebDriver driver)
	{
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}
	
	private By resultsProduct = By.cssSelector("div.product-layout.product-grid");
	
	public String getResultsPageTitle(String searchKey)
	{
		return eleUtil.waitForTitleIsAndCapture(searchKey, AppConstants.SHORT_DEFAULT_WAIT);
	}
	
	public int getProductResultsCount()
	{
		int resultCount = eleUtil.waitForElementsVisible(resultsProduct, AppConstants.SHORT_DEFAULT_WAIT).size();
		System.out.println("Product search result count: " + resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String productName)
	{
		By prodNameLocator = By.linkText(productName);
		eleUtil.doClick(prodNameLocator);
		return new ProductInfoPage(driver);
	}
}
