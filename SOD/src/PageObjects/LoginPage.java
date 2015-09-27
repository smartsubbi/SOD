package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage
{	
	final WebDriver driver;
	
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
	}
	
	
	
	@FindBy(id="ctl00_mcp_tbUserName")
	public static WebElement userNameInputField;
	
	@FindBy(id="ctl00_mcp_tbPassword")
	public static WebElement passwordInputField;
	
	@FindBy(id="ctl00_mcp_btnLogin")
	public static WebElement playNowButton;
}