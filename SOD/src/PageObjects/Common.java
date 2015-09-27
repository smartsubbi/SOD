package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class Common 
{
	final WebDriver driver;
	
	public Common(WebDriver driver)
	{
		this.driver = driver;			
	}	
	
	@FindBy(xpath="//div[@id='ctl00_logindiv']/a[@class='logo'][@title='Log in'][@href='/Login.aspx']")
	public static WebElement headerLoginLink;	
	
}