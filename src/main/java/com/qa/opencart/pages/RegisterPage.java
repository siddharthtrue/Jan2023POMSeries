package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtils;

public class RegisterPage
{
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	public RegisterPage(WebDriver driver)
	{
		this.driver = driver;
		eleUtil = new ElementUtils(this.driver);
	}
	
	private By firstname = By.id("input-firstname");
	private By lastname = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[1]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[2]/input");
	
	private By agreeChkBox = By.xpath("//input[@name='agree']");
	private By BtnContinue = By.xpath("//input[@type='submit']");
	
	private By userRegistrationSuccessMsg = By.cssSelector("div#content h1");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public String doRegisterAccount(String firstName, String lastName, String emailAddr, String phone, String psw, String confirmPsw, String subscribe)
	{
		eleUtil.waitForElementVisible(firstname, AppConstants.SHORT_DEFAULT_WAIT);
		eleUtil.doSendKeys(firstname, firstName);
		eleUtil.doSendKeys(lastname, lastName);
		eleUtil.doSendKeys(email, emailAddr);
		eleUtil.doSendKeys(telephone, phone);
		eleUtil.doSendKeys(password, psw);
		eleUtil.doSendKeys(confirmPassword, confirmPsw);
		
		doSubscribe(subscribe);
		
		eleUtil.doClick(agreeChkBox);
		eleUtil.doClick(BtnContinue);
		
		String userRegSuccessMsg = eleUtil.waitForElementVisible(userRegistrationSuccessMsg, AppConstants.MEDIUM_DEFAULT_WAIT).getText();
		System.out.println(userRegSuccessMsg);
		
		eleUtil.doClick(logoutLink, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doClick(registerLink);
		
		return userRegSuccessMsg;
	}
	
	private void doSubscribe(String subscribe)
	{
		if(subscribe.equalsIgnoreCase("yes"))
		{
			eleUtil.doClick(subscribeYes);
		}
		else
		{
			eleUtil.doClick(subscribeNo);
		}
	}
}
