package StepDefinition;		
//package Base;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;		
import cucumber.api.java.en.Then;		
import cucumber.api.java.en.When;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class Steps extends Base{				

	//@SuppressWarnings("unused")
	@Before 
	public void before(Scenario scenario) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		LocalDateTime now = LocalDateTime.now();
		//System.out.println(dtf.format(now));
		currentDateTime = dtf.format(now);
		extentReportFile_Final = "C:\\SeleniumReports\\extentReportFile_"+currentDateTime+".html";
				
		reportfile = new File(extentReportFile);
		reportfile_Final = new File(extentReportFile_Final);
		File temp_file = new File("C:\\SeleniumReports\\Report_Old_"+currentDateTime+".html");
		
		if(reportfile.exists()) {
			//extent = new ExtentReports(extentReportFile, false);
			if(reportfile.renameTo(temp_file)) {
				System.out.println("Renamed old report file");
			}
		}
		extent = new ExtentReports(extentReportFile, false);
		
		scenarioName = scenario.getName();
		extentTest = extent.startTest(scenarioName,scenarioName);
	}
	
	@After
	public void flush() {
		extent.flush();

		if(reportfile.renameTo(reportfile_Final)) {
			System.out.println("Renamed report file to: "+reportfile_Final);
		}
	}
	
	@AfterTest
	public void teardown() {
		System.out.println("Inside AfterAllClass");
		if(reportfile.renameTo(reportfile_Final)) {
			System.out.println("Renamed report file to: "+reportfile_Final);
		}
	}

    @Given("^Open the Firefox and launch the application$")				
    public void open_the_Firefox_and_launch_the_application() throws Throwable							
    {	
    	String URL = readData("URL");
    	
        System.out.println("This Step open the Firefox and launch the application.");
       
        navigateURL(URL);
        logEvent(LogStatus.INFO, scenarioName, "Navigated to www.techbeamers.com");
    }		

    @When("^Enter the Username and Password$")					
    public void enter_the_Username_and_Password() throws Throwable 							
    {		
    	System.out.println("This step enter the Username and Password on the login page.");		
        String username_path = readOR("username_path");
        String username = readData("Username");
        String password_path = readOR("password_path");
        String password = readData("Password");
        String login_btn_path = readOR("login_btn_path");
       
        System.out.print("Username: "+username);
        enterText(username_path,username);
        logEvent(LogStatus.INFO, scenarioName, "Add username");
       
        enterText(password_path,password);
        logEvent(LogStatus.INFO, scenarioName, "Add password");
       
        clickButton(login_btn_path); 
        logEvent(LogStatus.INFO, scenarioName, "Clicked on login button");
    }		

    @Then("Verify user navigate to home page")
    public void verify_user_navigate_to_home_page() throws IOException {
        
        String login_pg_marquee = readOR("login_pg_marquee");
        String login_pg_mgrid = readOR("login_pg_mgrid");
                
        
        try {	
	        if(driver.findElement(By.xpath(login_pg_marquee)).isDisplayed() && driver.findElement(By.xpath(login_pg_mgrid)).isDisplayed()){
	            System.out.println("Opened login page");
	        	logEvent(LogStatus.PASS, scenarioName, "Hopepage is opened");
	        }else {
	        	System.out.println("Login Failed");
	        	logEvent(LogStatus.FAIL, scenarioName, "Wrong credentials");
	        }
        }catch(Exception e) {
            logEvent(LogStatus.FAIL, scenarioName, "Wrong credentials");
        	logEvent(LogStatus.FAIL, scenarioName, e.getMessage());
        }

        
    }

}