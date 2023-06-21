package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class ProductInfoPage
{
	private WebDriver driver;
	private ElementUtils eleUtil;

	private Map<String, String> productInfoMap;
	
	public ProductInfoPage(WebDriver driver)
	{
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By successMsgAddToCart = By.cssSelector(".alert.alert-success.alert-dismissible");
	public String getProductHeaderName()
	{
		return eleUtil.doGetElementText(productHeader);
	}
	
	public int getProductImagesCount()
	{
		return eleUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_WAIT).size();
	}
	
	private void getProductMetaData()
	{
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement e : metaList)
		{
// Here, hashmap is used to validate keys and values means each keys contains correct values of not.
// We can also use array list also but we cannot validate keys and their values.
// Brand: Apple ----> where Brand is key and Apple is value
// Product Code: Product 18
// Reward Points: 800
// Availability: In Stock
			String metaText = e.getText();
			String metaInfo[] = metaText.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}
	
	private void getProductPriceData()
	{
// $2,000.00
// Ex Tax: $2,000.00
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String priceValue = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText();
		String exTaxPriceValue = exTaxPrice.split(":")[1].trim();
		
		productInfoMap.put("productprice", priceValue); // Here custom key is used means any string. key should be in small letters
		productInfoMap.put("extaxprice", exTaxPriceValue);
	}
	
// Above both methods not returning anything so used below method which combinedly return map data. 
	public Map<String, String> getProductInfo()
	{
		//productInfoMap = new HashMap<String, String>();
		productInfoMap = new LinkedHashMap<String, String>();
		//productInfoMap = new TreeMap<String, String>(); //sorted order-as per capital, small and numerical values
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productname", getProductHeaderName());
		return productInfoMap;
	}
	
	public String addToCart(int quantityAmt)
	{
		eleUtil.waitForElementVisible(quantity, AppConstants.SHORT_DEFAULT_WAIT);
		eleUtil.doSendKeys(quantity, String.valueOf(quantityAmt));
		eleUtil.doClick(addToCartBtn);
		return eleUtil.doGetElementText(successMsgAddToCart);
		//return eleUtil.waitForElementPresent(successMsgAddToCart, 5).getText();
	}
}
