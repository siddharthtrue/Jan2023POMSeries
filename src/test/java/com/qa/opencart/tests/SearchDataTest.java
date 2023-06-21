package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;
import com.qa.opencart.pojo.Product;

public class SearchDataTest extends BaseTest
{
	@BeforeClass
	public void searchSetup()
	{
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(dataProvider="productDataMaster", dataProviderClass = ProductDataProvider.class) // we can also put "productData" as data provider value here
	public void searchProductResultCountTest(Product product)
	{
		resultsPage = accPage.doSearch(product.getSearchKey());
		Assert.assertTrue(resultsPage.getProductResultsCount() > 0);
	}
	
	@Test(dataProvider="productDataMaster", dataProviderClass = ProductDataProvider.class)
	public void searchPageTitleTest(Product product)
	{
		resultsPage = accPage.doSearch(product.getSearchKey());
		String actSearchTitle = resultsPage.getResultsPageTitle(product.getSearchKey());
		System.out.println("Search page title: " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + product.getSearchKey());
	}
	
	@Test(dataProvider="productDataMaster", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(Product product)
	{
		resultsPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName());
		String actualProdHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual product name: " + actualProdHeaderName);
		Assert.assertEquals(actualProdHeaderName, product.getProductName());
	}
	
	@Test(dataProvider="productDataMaster", dataProviderClass = ProductDataProvider.class)
	public void productImagesTest(Product product)
	{
		resultsPage = accPage.doSearch(product.getSearchKey());
		productInfoPage = resultsPage.selectProduct(product.getProductName());
		int actProdImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual product images count: " + actProdImagesCount);
		Assert.assertEquals(actProdImagesCount, product.getProductImages());
	}
}
