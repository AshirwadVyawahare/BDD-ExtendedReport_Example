package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {
	
	WebDriver driver;
	
	public void createDriver() {		
		driver = new FirefoxDriver();		
	}
	
	public void navigateURL(String url) {
		createDriver();
		driver.get(url);
	}

}
