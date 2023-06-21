package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.frameworkexception.FrameworkException;

public class DriverFactory 
{
	WebDriver driver;
	
//	public WebDriver initDriver(String browserName) --> we may need to use many properties parameter here so
// instead of using only browserName we can use call by reference concept (as shown below )which means supply 
// properties object so that we can use any of the property available in properties file.
	
	OptionsManager optionsManager;
	public static String highlightElement;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); 
	
	public WebDriver initDriver(Properties prop)
	{
		String browserName = prop.getProperty("browser").trim();
//		String browserName = System.getProperty("browser");
		System.out.println("Browser name is: " + browserName);
		
		highlightElement = prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		switch(browserName.toLowerCase())
		{
			case "chrome": 
//				driver = new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				break;
			case "firefox": 
//				driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				break;
			case "edge": 
//				driver = new EdgeDriver(optionsManager.getEdgeOptions());
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				break;
			default: 
				System.out.println("Please pass the right browser ...." + browserName);
				throw new FrameworkException("NOBROWSERFOUNDEXCEPTION");
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	// return the thread local copy of the driver
	public synchronized static WebDriver getDriver()
	{
		return tlDriver.get();
	}
	
	public Properties initProp()
	{
		// mvn clean install -Denv="qa"
	
		Properties prop = new Properties();
		FileInputStream fis = null;
		
		String envName = System.getProperty("env");
		System.out.println("env name is: " + envName);
		
		try
		{
			if(envName == null)
			{
				System.out.println("No env is given.... hence running on QA env....");
				fis = new FileInputStream("./src/main/resources/config/qa.config.properties"); 
			}
			else
			{
				System.out.println("Running test cases on env: " + envName);
				switch (envName.toLowerCase().trim())
				{
				case "qa":
					fis = new FileInputStream("./src/main/resources/config/qa.config.properties");
					break;
				case "stage":
					fis = new FileInputStream("./src/main/resources/config/stage.config.properties");
					break;
				case "dev":
					fis = new FileInputStream("./src/main/resources/config/dev.config.properties");
					break;
				case "uat":
					fis = new FileInputStream("./src/main/resources/config/uat.config.properties");
					break;
				case "prod":
					fis = new FileInputStream("./src/main/resources/config/config.properties");
					break;

				default:
					System.out.println("Please pass the right env name.... " + envName);
					throw new FrameworkException("NOVALIDENVGIVEN");
				}
			}
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			prop.load(fis);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return prop;
	}
	
	public static String getScreenshot()
	{
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try
		{
			FileUtils.copyFile(srcFile, destination);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return path;
	}
}
