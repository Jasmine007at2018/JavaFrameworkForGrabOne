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
	
		
	//Method to check if GrabOne home page is launched
	public void VerifyHomePage() throws HeadlessException, AWTException, IOException
	{
			String title=null;
		
			//wait until the title bar is displayed. Can it be used to verify that the page is loaded? 
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
	
	
	//method to launch Login page "Login" menu item
	public String GotoLogin() throws HeadlessException, AWTException, IOException
	{
			//get the title of the current page before login
			String pageTitleBeforeLogin=BrowserFactory.driver.getTitle();
		
			//click on the person figure on the left top banner of home page
			pFlag.click();
			
			//wait for Login item displaying in the drop-down menu
			//BrowserFactory.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			//CustomWait.wait("//*[@id='user-nav-account']/li[7]/a", 5, 1);
			
			//click on "Login" menu item
			login.click();
			
			//wait for page loading after clicking Login. The xpath is the title bar's .
			CustomWait.wait("//h2[contains(.,'Login To GrabOne ')]", 5, 1);
			
			//verify if login page is launched by page title.
			assertEquals(BrowserFactory.driver.getTitle(),"Login To GrabOne","Login page isn't launched properly");
			testLog.log(Status.PASS, "Go to Login page successfully.");
			
			return pageTitleBeforeLogin;
						
//			//After login, check if the AUT returns to the previous page before login
//			String titleAfterLogin=BrowserFactory.driver.getTitle();
//			Assert.assertEquals(titleAfterLogin, titleBeforeLogin, "Not back to the previous page.");			
			
	}
			


}
