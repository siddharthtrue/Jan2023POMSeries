package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest
{
	@BeforeClass
	public void accPageSetup()
	{
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void productInfoTest()
	{
		resultsPage = accPage.doSearch("MacBook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfo();
		System.out.println(productInfoMap);
		
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productInfoMap.get("extaxprice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertAll();
	}
	
	@Test
	public void addToCartTest()
	{
		resultsPage = accPage.doSearch("MacBook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		String actualSuccessMsg = productInfoPage.addToCart(1);
		String finalStr[] = actualSuccessMsg.split("×");
		String actualMsg = finalStr[0].trim();
		Assert.assertEquals(actualMsg, "Success: You have added " + "MacBook Pro" + " to your shopping cart!");
	}
}
