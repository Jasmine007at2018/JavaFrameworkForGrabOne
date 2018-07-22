package utilityPackage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

import helperPackage.BrowserFactory;

public class CustomWait {
	//Method to wait
			public static void wait(final String xPath,int timeout,int pollingtime)
			{
				Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(BrowserFactory.driver).withTimeout(Duration.ofSeconds(timeout))
				        .pollingEvery(Duration.ofMillis(pollingtime)).ignoring(NoSuchElementException.class);

							WebElement elementName = fluentWait.until(new Function<WebDriver, WebElement>() {
						    public WebElement apply(WebDriver driver) {
						    return driver.findElement(By.xpath(xPath));
						     }
						   });
			}
}
