//This is the base class file to use
package StepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//import org.openqa.selenium.remote.RemoteWebDriver;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class Base {
	
	WebDriver driver;
	String extentReportFile = "C:\\SeleniumReports\\extentReportFile.html";
	ExtentReports extent;// = new ExtentReports(extentReportFile, true);
	ExtentTest extentTest;// = extent.startTest(testName,testName);
	String extentReportFile_Final,scenarioName,currentDateTime;
	File reportfile, reportfile_Final;
	
	public void createDriver() {	
		System.setProperty("webdriver.gecko.driver", "E:\\\\Selenium\\geckodriver.exe");

		driver = new FirefoxDriver();	
		//driver = new ChromeDriver();
		//driver = new RemoteWebDriver(DesiredCapabilities.chrome());

	}
	
	public void navigateURL(String url) {
		createDriver();
		driver.manage().window().maximize();
		driver.get(url);
	}
	
	public void enterText(String textBoxPath, String textToEnter) {
		try {
			WebElement textBox = driver.findElement(By.xpath(textBoxPath));
			textBox.clear();
			textBox.click();
			textBox.sendKeys(textToEnter);
		}catch(Exception e){
			System.out.println("Element not found: "+e.getMessage());
			logEvent(LogStatus.FAIL, scenarioName, e.getMessage());
		}
	}
	public void clickButton(String btnPath) {
		driver.findElement(By.xpath(btnPath)).click();
	}
	
	public String readExcel(String filePath,String dataName) throws IOException {
		
		Row row;
		Cell cell;
		String returnValue = null;
		
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		for (int i=0;i<=sheet.getLastRowNum();i++) {
			 row = sheet.getRow(i);
			 cell = row.getCell(0);
			 if(cell.toString().equals(dataName)) {
				 returnValue = row.getCell(1).getStringCellValue();
			 }
		}
		 
		workbook.close();
		return returnValue;
	}
	
	public String readData(String dataName) throws IOException {
		
		String returnValue = null;
		
		returnValue = readExcel("C:\\Users\\Dell\\eclipse-workspace\\CucumberWithSelenium\\Data.xlsx",dataName);
		
		return returnValue;
	}

	public String readOR(String dataName) throws IOException {
		
		String returnValue = null;
		
		returnValue = readExcel("C:\\Users\\Dell\\eclipse-workspace\\CucumberWithSelenium\\ObjRepo.xlsx",dataName);
		
		return returnValue;
	}
	
	public void logEvent(LogStatus logStatus, String testName, String logText) {
		//String extentReportFile = System.getProperty("user.dir")+ "\\extentReportFile.html";
		//String extentReportFile = "C:\\SeleniumReports\\extentReportFile.html";
		//String extentReportImage = System.getProperty("user.dir")+ "\\extentReportImage.png";

		// Create object of extent report and specify the report file path.
		//ExtentReports extent = new ExtentReports(extentReportFile, true);

		// Start the test using the ExtentTest class object.
		//ExtentTest extentTest = extent.startTest(testName,testName);
		
		extentTest.log(logStatus, logText);
		
		//extent.flush();
		//return extentTest;
	
	}
}
