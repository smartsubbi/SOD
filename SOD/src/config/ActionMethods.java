package config;

import static executionEngine.DriverScript.OR;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.Reporter;

import executionEngine.DriverScript;

public class ActionMethods 
{
	public static WebDriver driver;
	
	public static void openBrowser(String object, String data)
	{
		try
		{
			if(data.equalsIgnoreCase("mozilla"))
			{
				ProfilesIni listProfiles = new ProfilesIni();
				FirefoxProfile profile = listProfiles.getProfile("default");
				
				driver = new FirefoxDriver(profile);
				driver.manage().window().maximize();
				Reporter.log("Mozilla browser Started...", true);
			}
			else if(data.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				Reporter.log("Chrome browser Started...", true);
			}
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			Reporter.log("Not able to start Browser...", true);
			DriverScript.aResult = false;			
		}		
	}
	
	public static void navigate(String object, String data)
	{
		try
		{			
			driver.get(data);	
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			Reporter.log("Not able to navigate to url..."+e.getMessage(), true);
			fScreenShot();
			DriverScript.aResult = false;
		}		
	}
	
	public static void click(String object, String data)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			driver.findElement(By.xpath(OR.getProperty(object))).click();	
		}
		catch(Exception e)
		{
			Reporter.log("Not able to click "+e.getMessage(), true);
			fScreenShot();
			DriverScript.aResult=false;
		}		
	}
	
	public static void input(String object, String data)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);	
		}
		catch(Exception e)
		{
			Reporter.log("Not able to enter data : "+e.getMessage(),true);
			fScreenShot();
			DriverScript.aResult=false;
		}		
	}
	
	public static void closeBrowser(String object, String data)
	{
		try
		{			
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			driver.close();
		}
		catch(Exception e)
		{
			Reporter.log("Not able to Close Browser : "+e.getMessage(),true);
			fScreenShot();
			DriverScript.aResult=false;
		}
		
	}
	
	public static void screenShot(String object, String data)
	{		
		try
		{
			DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy-HH.mm.ss");			
			Date date = new Date();					 
		    String date1= dateFormat.format(date);				
			String[] parts=date1.split("-");
			String part1 = "Date["+parts[0]+"]";
			String part2 = "Time["+parts[1]+"]";
			String fPart=part1+"-"+part2;
			String fileName=data+fPart;			 
			System.out.println("Current date and time is " +fileName);			 
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	        
	        FileUtils.copyFile(screenshot,new File("D:\\workspace\\SchoolOfDragons\\ScreenShots\\Passed\\"+part1+"\\ScreenShot-"+fileName+".png"));
		}
		catch(Exception e)
		{
			Reporter.log("Error with Screenshot : "+e.getMessage(), true);
			DriverScript.aResult=false;
		}		
	}
	
	public static void fScreenShot()
	{		
		String data=DriverScript.testStepNum;
		try
		{	
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	        
	        FileUtils.copyFile(screenshot,new File("D:\\workspace\\SchoolOfDragons\\ScreenShots\\Failed\\ScreenShot.png"));
		}
		catch(Exception e)
		{
			Reporter.log("Error with Screenshot : "+e.getMessage(), true);
			DriverScript.aResult=false;
		}		
	}
	
	public static void verifyObject(String object, String data)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			Reporter.log("Value is : "+OR.getProperty(object),true);
			driver.findElement(By.xpath(OR.getProperty(object)));	
		}
		catch(Exception e)
		{
			Reporter.log("Element not found"+e.getMessage(),true);
			fScreenShot();
			DriverScript.aResult=false;
		}			
	}
	
	public static void pause(String object, String data)
	{
		try {
			Reporter.log("Pausing for 5 seconds...");
			Thread.sleep(2000);
			Reporter.log("Pausing for 5 seconds done...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Reporter.log("Sleep Error"+e.getMessage(),true);			
		}
	}
}