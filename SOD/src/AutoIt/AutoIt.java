package AutoIt;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

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
import org.testng.annotations.Test;

import config.ActionMethods;
import executionEngine.DriverScript;

public class AutoIt 
{
	static WebDriver driver;
	public static int i=0;

	@Test
	public static void mainP() throws Exception 
	{
		System.out.println(System.getenv("USERNAME"));


        
		
		i=i+1;
		fScreenShot();
		System.out.println("Initialize browser");
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("Initialize browser done");
		System.out.println("Maximize browser");
		driver.manage().window().maximize();
		System.out.println("Maximize browser done");
		System.out.println("Setting wait time");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Setting wait time done");
		i=i+1;
		fScreenShot();		
		
		Runtime.getRuntime().exec("C:\\Users\\subramanyakb\\Desktop\\EnterCredentials.exe");
		i=i+1;
		fScreenShot();
		fScreenShott();
		System.out.println("Open URL");
		driver.get("http://qa.schoolofdragons.com/");
		
		System.out.println("Open URL done");
		fScreenShottt();
		i=i+1;
		fScreenShot();
		System.out.println("URL open Done");	
		driver.findElement(By.xpath("//div[@id='ctl00_logindiv']/a[@title='Log in']"));
		driver.close();
		
		
		// TODO Auto-generated method stub

	}
	
	private static void fScreenShot() throws Exception 
	{		
		//String data=DriverScript.testStepNum;
		BufferedImage screencapture = new Robot().createScreenCapture(
		           new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );

		    
			// Save as JPEG
		     File file = new File("C:\\Users\\subramanyakb\\Desktop\\jenkins Screenshot Autoit\\screencapture"+i+"+.jpg");
		     ImageIO.write(screencapture, "jpg", file);
	}
	
	private static void fScreenShott()
	{		
		
		try
		{	
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	        
	        FileUtils.copyFile(screenshot,new File("C:\\Users\\subramanyakb\\Desktop\\jenkins Screenshot Autoit\\ScreenShotBeforeOpenURL.png"));
		}
		catch(Exception e)
		{
			Reporter.log("Error with Screenshot : "+e.getMessage(), true);
			DriverScript.aResult=false;
		}		
	}
	
	private static void fScreenShottt()
	{		
		
		try
		{	
			File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);	        
	        FileUtils.copyFile(screenshot,new File("C:\\Users\\subramanyakb\\Desktop\\jenkins Screenshot Autoit\\ScreenShotAfterOpenURL.png"));
		}
		catch(Exception e)
		{
			Reporter.log("Error with Screenshot : "+e.getMessage(), true);
			DriverScript.aResult=false;
		}		
	}

}
