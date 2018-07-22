package pagesPackage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.asserts.*;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import helperPackage.BrowserFactory;
import testPackage.BaseClass;
import utilityPackage.ConfigReader;
import utilityPackage.CustomWait;
import utilityPackage.ExcelDataConfig;
import utilityPackage.ScreenShot;

public class GrabOneHomePage extends BaseClass{
	//POM constructor
	public GrabOneHomePage()
	{
		PageFactory.initElements(BrowserFactory.driver, this);
	}
	
	//person figure on the left top banner
	@FindBy(how=How.XPATH,using=".//*[@class='dropdown-overlay-container']")
	static WebElement pFlag;
	
	//"Login" item in the drop-down menu
	@FindBy(how=How.XPATH,using="//*[@id='user-nav-account']/li[7]/a")
	static WebElement login;
	
	//Email input-box on Login page
	@FindBy(how=How.ID, using="login_email")
	static WebElement email;
	
	//Password input-box on Login page
	@FindBy(how=How.ID, using="login_password")
	static WebElement password;
	
	//box of Remember Me
	@FindBy(how=How.ID, using="login_remember_me")
	static WebElement rememberMe;
	
	//Login button
	@FindBy(how=How.CLASS_NAME,using="user-form-button")
	static WebElement loginBtn;
		
	//Method to check if GrabOne home page is launched
	public void VerifyHomePage() throws HeadlessException, AWTException, IOException
	{
			String title=null;
		
			//wait until the title is displayed
			CustomWait.wait("//button[@data-toggle-target='#user-nav-account']",30,1);
				
			//get the title of the current page
			title=BrowserFactory.driver.getTitle();	
			
			//check if the title contains "GrabOne"
			if(title.contains("GrabOne NZ"))
			{
				testLog.log(Status.PASS, "GrabOne home page is launched successfully");
			}
			else
			{
				//Capture screenshot of the fail page
				ScreenShot.captureScreenshot(BrowserFactory.driver, "Failed home page");
				//Get the failure log
				testLog.log(Status.FAIL, "GrabOne home page isn't launched successfully");
				//Set an assertion of failure
				Assert.fail("Failed to launch GrabOne home page");
			}		
	}
	
	
	//method to Login from "Login" menu item
	public void Login(int ID ) throws HeadlessException, AWTException, IOException//ID is defined in ./TestData/TestData.xlsx
	{
			//get the title of the current page before login
			String titleBeforeLogin=BrowserFactory.driver.getTitle();
		
			//click on the person figure on the left top banner of home page
			pFlag.click();
			
			//wait
			//BrowserFactory.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			CustomWait.wait("//*[@id='user-nav-account']/li[7]/a", 5, 1);
			
			//click on "Login" menu item
			login.click();
			//verify if login page is launched.
			assertEquals(BrowserFactory.driver.getTitle(),"Login To GrabOne","Login page isn't launched properly");
			
			//popular login data from excel file: "./TestData/TestData.xlsx". ID=rowNumber
			ExcelDataConfig excel=new ExcelDataConfig(ConfigReader.getExcelPath());			
			email.sendKeys(excel.getData("Login", ID, 1));
			password.sendKeys(excel.getData("Login", ID, 2));
			
			//un-check the "Remember Me" box
			rememberMe.click();
			
			//click on Login button
			loginBtn.click();
			
//			//verify login already
//			//click on the person figure to show the drop-down menu
//			pFlag.click();
//			//check if "Logout" display
//			String logout=BrowserFactory.driver.findElement(ByXPath.xpath("//*[@id='user-nav-account']/li[6]/a")).getText();
//			System.out.println(logout);
			
			//After login, check if the AUT returns to the previous page before login
			String titleAfterLogin=BrowserFactory.driver.getTitle();
			Assert.assertEquals(titleAfterLogin, titleBeforeLogin, "Not back to the previous page.");			
			
			
			
			
			
			
	}
			


}
