package testPackage;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import helperPackage.BrowserFactory;

public class BaseClass {
	//Extent report init
		public static ExtentReports reports;
		public static ExtentTest testLog;
		public static ExtentHtmlReporter htmlReporter;
		
		@BeforeTest
		public void testInit()
		{
			//Setting the path to view the report
			htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/test-output/MyExtentReport.html"));
			
			//loading the extent report config xml 
			htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"/extent-config.xml"));
			
			//report definition
			reports = new ExtentReports();
			
			//Environment information
			reports.setSystemInfo("Environment", "QA");
			reports.setSystemInfo("Application", "QA Application");
			reports.setSystemInfo("Regression", "On Test Environment");
			reports.attachReporter(htmlReporter);
		}
		
		@BeforeMethod
		public void setUpTest(Method method)
		{
			//extent test init
			String testName = method.getName();
			testLog = reports.createTest(testName);
			
			//Initiate driver
			BrowserFactory.startBrowser("chrome");
		}
		
		@AfterMethod
		public void tearDownTest(ITestResult result)
		{
			if(result.getStatus()==ITestResult.SUCCESS)
			{
				testLog.log(Status.PASS, "Test passed");
			}else if(result.getStatus()==ITestResult.FAILURE)
			{
				testLog.log(Status.FAIL, "Test failed");
			}else if(result.getStatus()==ITestResult.SKIP)
			{
				testLog.log(Status.SKIP, "Test skipped");
			}
		
			//quit the instance of driver
			//BrowserFactory.driver.quit();
		}
		
		@AfterTest
		public void clearUp()
		{
			//flush the reports
			reports.flush();
			
			
		}

}
