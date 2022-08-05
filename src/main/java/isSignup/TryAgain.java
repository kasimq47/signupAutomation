package isSignup;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

public class TryAgain {

	public static WebDriver driver;
	public static boolean secondPaidonly;
	public static boolean secondPaidWithBook;
	
	public static DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
	public static Date date = new Date();
	public static String currentDate1 = dateFormat.format(date);
	public static String [] curDateArray = currentDate1.split("/");
	public static String todaysDate = curDateArray[0]+"-"+curDateArray[1]+"-"+curDateArray[2];
	public static int numberOfCourse;
	
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		
		
		System.out.println(todaysDate);
		
		
		

		WebDriverManager.chromedriver().setup(); // FOR CHROME DRIVER
		driver = new ChromeDriver();
		driver.navigate().to("http://164.52.198.42:8080/istest/common/login/international-schooling");
		driver.manage().window().maximize();
		Thread.sleep(3000);
//		driver.findElement(By.xpath("/html/body/section/div/div[2]/div[1]/div/div[3]/a")).click();
		driver.findElement(By.xpath("/html/body/div[12]/p/a")).click(); // accept cookies
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(ISConstant.id);
		sendKeysXPATH("/html/body/div[1]/div/div[1]/div[2]/div/div/form/div[2]/div/input", "a1234567");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/form/div[3]/input")).click();
		managesession();
		
		
		


	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void managesession() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[4]/a/span")));
		driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[4]/a/span")).click();          // click on book a session

		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div[2]/h4")));
		if (driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div[2]/h4")).getText()
				.equalsIgnoreCase("Your LMS is Disabled. Please contact to administrator for further inquiry.")) {
			System.out.println("Create session");
			driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
			adminLoginPage();	

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/a")));
			driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/a")).click(); // Manage LMS

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/ul/li[3]/a")));
			driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/ul/li[3]/a")).click(); // Manage
																												// session

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[1]/div[2]/div/label/input")));
			driver.findElement(By.xpath(
					"/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[1]/div[2]/div/label/input"))
					.sendKeys(ISConstant.id); // Search Id
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[2]/div/table/tbody/tr/td[5]")));
				String stringGradeNumber = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[2]/div/table/tbody/tr/td[5]")).getText();
			driver.findElement(By.xpath(
					"/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[2]/div/table/tbody/tr/td[8]/div/button"))
					.click(); // Action

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("View")));
			driver.findElement(By.linkText("View")).click(); // View

			wait.until(ExpectedConditions.elementToBeClickable(By.id("semesterDateStart")));
			driver.findElement(By.id("semesterDateStart")).clear();
			System.out.println(todaysDate);

			driver.findElement(By.id("semesterDateStart")).sendKeys(todaysDate); // current date

			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[2]/div[1]/div/div/div[1]"))
					.click();

			driver.findElement(By.id("semesterDateYear")).clear();
			driver.findElement(By.id("semesterDateYear")).sendKeys("2022", Keys.ENTER); // session 2022

			driver.findElement(By.id("weeklyReportFrequency")).click();
			driver.findElement(By.id("weeklyReportFrequency")).sendKeys("T", Keys.ENTER); // weekly report frequency
			
			String[] sdn = stringGradeNumber.split("-");
			int gradeNumber= Integer.parseInt(sdn[1]);
			if(gradeNumber <= 8) {
			    numberOfCourse = 6;
			} else {
				numberOfCourse = 9;
			}
			
			for (int i=1; i<=numberOfCourse; i++) {
			
			Select se1 = new Select(driver.findElement(By.id("lmsSubjectId"+i)));
			se1.selectByIndex(2);
			Thread.sleep(500);
			if(driver.findElement(By.id("statusMessage")).isDisplayed()) {
				driver.findElement(By.id("modalMessage")).click();
				se1.selectByIndex(3);
				Thread.sleep(500);
				if(driver.findElement(By.id("statusMessage")).isDisplayed()) {
					driver.findElement(By.id("modalMessage")).click();
					se1.selectByIndex(4);
				}
			}
	}		
			wait.until(ExpectedConditions.elementToBeClickable(By.id("sessionSubjectSave")));
			driver.findElement(By.id("sessionSubjectSave")).click();                                      // save disable 
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[2]/div[1]/div/div/div[1]/button")));
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[2]/div[1]/div/div/div[1]/button")).click();		
		

		}
	}
	
	
	
	

	public static void clickButton(String element) {
		driver.findElement(By.id(element)).click();
	}

	public static void sendKeys(String element, String text) {
		driver.findElement(By.id(element)).sendKeys(text);
	}

	public static void gatewayStripe() throws InterruptedException {

//		driver.findElement(By.xpath(
//				"//*[@id=\"primary-pg\"]/div[3]/div/span"))
//				.click();
		Thread.sleep(1000);

		sendKeysXPATH(
				"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input",
				"4111111111111111");
		sendKeysXPATH(
				"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[2]/div/div/span/input",
				"1122");
		sendKeysXPATH(
				"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[3]/div/div[1]/span/input",
				"123");
		sendKeysXPATH(
				"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[3]/div/div/div[1]/div/div[2]/div/div[1]/div/div/span/input",
				"DemoName");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[2]/div[2]/button/div[3]"))
				.click(); // payment final click

	}

	public static void sendKeysID(String element, String text) {
		driver.findElement(By.id(element)).sendKeys(text);
	}

	public static void sendKeysXPATH(String element, String text) {
		driver.findElement(By.xpath(element)).sendKeys(text);
	}
	
	public static void adminLoginPage() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("kedar@seriindia.org");
		sendKeysXPATH("/html/body/div[1]/div/div[1]/div[2]/div/div/form/div[2]/div/input", "a1234567");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/form/div[3]/input")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div/div/div[2]/div[1]/div/div/div/a")));
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[2]/div[1]/div/div/div/a")).click(); // SMS
																												// Click
	}

}
