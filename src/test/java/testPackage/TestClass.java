package testPackage;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pagesPackage.GrabOneHomePage;
import pagesPackage.LoginPage;


public class TestClass extends BaseClass{
	
	
	@Test(description = "Test of successful Login", priority=1)
	public void Login_success() throws HeadlessException, AWTException, IOException
	{
		testLog.log(Status.INFO, "Login_success test started");
		
		//Launch and validate GrabOne home page
		GrabOneHomePage obj_HomePage=new GrabOneHomePage();
		obj_HomePage.VerifyHomePage();
		//obj_HomePage.GotoLogin();
		
		//validate Login with valid data successfully
		//Tester can choose which set of data in "Login" sheet in the TestData excel file to test by providing ID
		LoginPage obj_LoginPage= new LoginPage();		
		int ID=1;
		obj_LoginPage.LoginWithValidData(ID, obj_HomePage.GotoLogin() );
		
		
		
	}
	
	@Test(description = "Test of failed Login", priority=1)
	public void Login_failure() throws HeadlessException, AWTException, IOException
	{
		testLog.log(Status.INFO, "Login_failure test started");
		
		//Launch and validate GrabOne home page
		GrabOneHomePage obj_HomePage=new GrabOneHomePage();
		obj_HomePage.VerifyHomePage();
		
		//go to Login page from "Login" menu item
		obj_HomePage.GotoLogin();
 		
		//validate that user can't login without entering email/password or entering invalid email/password
		int ID=7;
		LoginPage obj_LoginPage= new LoginPage();
		obj_LoginPage.LoginWithNullOrInvalidData(ID);
		
		
		
		
		
		
	}

}
