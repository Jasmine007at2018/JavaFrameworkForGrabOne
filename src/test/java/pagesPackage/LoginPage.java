package pagesPackage;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import helperPackage.BrowserFactory;
import testPackage.BaseClass;
import utilityPackage.ConfigReader;
import utilityPackage.CustomWait;
import utilityPackage.ExcelDataConfig;
import utilityPackage.ScreenShot;

public class LoginPage extends BaseClass {
	
	public LoginPage()
	{
		PageFactory.initElements(BrowserFactory.driver, this);
	}

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
	
	//reminder message for email
	@FindBy(how=How.XPATH, using="//*[@id='login']/fieldset/div[1]/ul/li[1]")
	static WebElement reminderEmail;

	//error message when without email&password
	@FindBy(how=How.XPATH, using="//*[@id='login']/fieldset/div[1]/ul/li[2]")
	static WebElement errorMessage;
	
	//error message when without email&password
	@FindBy(how=How.XPATH, using="//*[@id='login']/fieldset/div[1]/ul/li")
	static WebElement errorMessage1;
	
	//reminder message for password
	@FindBy(how=How.XPATH, using="//*[@id='login']/fieldset/div[2]/ul")
	static WebElement reminderPassword;
	
	
	//method to Login with valid data from "Login" menu item
		public void LoginWithValidData(int ID, String pageTitleBeforeLogin ) throws HeadlessException, AWTException, IOException//ID is defined in ./TestData/TestData.xlsx
		{		
//				//wait until title of login page is displayed
//				CustomWait.wait("//'[@id=filter-page-cat']/div[1]/h2",30,1);
			
				//popular login data from excel file: "./TestData/TestData.xlsx". ID=rowNumber
				ExcelDataConfig excel=new ExcelDataConfig(ConfigReader.getExcelPath());			
				email.sendKeys(excel.getData("Login", ID, 1));
				password.sendKeys(excel.getData("Login", ID, 2));
				
//				//un-check the "Remember Me" box
//				rememberMe.click();
				
				//click on Login button
				loginBtn.click();
				
				//After login, check if the AUT returns to the previous page before login
				String pageTitleAfterLogin=BrowserFactory.driver.getTitle();
				Assert.assertEquals(pageTitleAfterLogin, pageTitleBeforeLogin, "After login, doesn't display the previous page before login .");				
				
		}
		
		public void LoginWithNullOrInvalidData(int ID) throws HeadlessException, AWTException, IOException
		{
//			//wait until title of login page is displayed
//			CustomWait.wait("//'[@id=filter-page-cat']/div[1]/h2",30,1);
			
			//verify if user can login without entering username and password but just clicking on Login button
			
			//un-check the "Remember Me" box
			rememberMe.click();		
			
			//popular login data from excel file: "./TestData/TestData.xlsx". ID=rowNumber
			ExcelDataConfig excel=new ExcelDataConfig(ConfigReader.getExcelPath());			
			email.sendKeys(excel.getData("Login", ID, 1));
			password.sendKeys(excel.getData("Login", ID, 2));
			
			//click on Login button
			loginBtn.click();			
			
			//check if the user is still in login page 
			Assert.assertEquals(BrowserFactory.driver.findElement(By.xpath("//h2[contains(.,'Login To GrabOne ')]")).getText(),"Login To GrabOne","Not in Login page");
			
			//screenshot after trying login with null or invalid data
			ScreenShot.captureScreenshot(BrowserFactory.driver, "screenshotOfNullDataLogin");
			
			//check email box. 
			//if email box is null, reminder for email and error message are displayed; 
			//if email box is not null, error message is displayed. 
			if(excel.getData("Login", ID, 1).equals(""))
			{	System.out.println("null email");
				//check reminder for email
				Assert.assertTrue(reminderEmail.isDisplayed(),"No reminder for Email");
				if(!(reminderEmail.getText().equals("Please enter an email address")))
				{
					testLog.log(Status.FAIL, "reminder for email is not correct");
				}
			
				//check error message for valid email or password
				Assert.assertTrue(errorMessage.isDisplayed(), "No error message for Email");
				if(!(errorMessage.getText().equals("Incorrect email address or password")))
				{
					testLog.log(Status.FAIL, "error message is not correct");					
				}
			}
			else
			{	System.out.println("non-null email");
				//check error message for valid email or password
				Assert.assertTrue(errorMessage1.isDisplayed(), "No error message for Email");
				if(!(errorMessage1.getText().equals("Incorrect email address or password")))
				{
					testLog.log(Status.FAIL, "error message is not correct");
				}
			}
			
			if(excel.getData("Login", ID, 2).equals(""))
			{	System.out.println("null password");
				//check reminder for password
				Assert.assertTrue(reminderPassword.isDisplayed(), "No reminder for Password");
				if(!(reminderPassword.getText().equals("Please enter a password")))
				{	System.out.println("if null password");
					testLog.log(Status.FAIL, "reminder for password is not correct");
				}
			}			
			
		}
		
		
	
}
