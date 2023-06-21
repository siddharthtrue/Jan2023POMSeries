package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;

// Search is common functionality so it does not have its page class. hence only test class.

// There were multiple data providers below, created a master data provider with all data. Tests will consume
// data as they want. Master and all individual data providers are stored in dataproviders package.
// To achieve this we need to use pojo technique. (Plain Old Java Object)

public class SearchTest extends BaseTest
{
	@BeforeClass
	public void searchSetup()
	{
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(dataProvider="productNameData", dataProviderClass = ProductDataProvider.class)
	public void searchProductResultCountTest(String searchKey)
	{
		resultsPage = accPage.doSearch(searchKey);
		Assert.assertTrue(resultsPage.getProductResultsCount() > 0);
	}
	
	@Test(dataProvider="productNameData", dataProviderClass = ProductDataProvider.class)
	public void searchPageTitleTest(String searchKey)
	{
		resultsPage = accPage.doSearch(searchKey);
		String actSearchTitle = resultsPage.getResultsPageTitle(searchKey);
		System.out.println("Search page title: " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + searchKey);
	}
	
	@Test(dataProvider="productTestData", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(String searchKey, String productName)
	{
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		String actualProdHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual product name: " + actualProdHeaderName);
		Assert.assertEquals(actualProdHeaderName, productName);
	}
	
	@Test(dataProvider="productImagesTestData", dataProviderClass = ProductDataProvider.class)
	public void productImagesTest(String searchKey, String productName, int expImagesCount)
	{
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		int actProdImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual product images count: " + actProdImagesCount);
		Assert.assertEquals(actProdImagesCount, expImagesCount);
	}
}
