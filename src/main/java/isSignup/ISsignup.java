package isSignup;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ISsignup {
	public static WebDriver driver;
	public static boolean atDashboard;
	public static boolean verifyAccount;
	public static boolean login;
	public static boolean backToStep1;
	public static boolean continueWithStep1;
	public static boolean planFresh;
	public static boolean onlyBookAnEnrollment;
	public static boolean nextInstallments;
	public static boolean secondPaidonly;
	public static boolean secondPaidWithBook;
	public static boolean academicStartDate;
	public static String nextText = "text one";
	public static boolean middle;
	public static boolean high;
	public static int gNumber;
	public static float credNumber;
	public static int numberOfCourse;
	public static boolean customPlanCheck;

	public static DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
	public static Date date = new Date();
	public static String currentDate1 = dateFormat.format(date);
	public static String[] curDateArray = currentDate1.split("/");
	public static String todaysDate = curDateArray[0] + "-" + curDateArray[1] + "-" + curDateArray[2];

	public static void main(String[] args) throws InterruptedException, AWTException {

		WebDriverManager.chromedriver().setup(); // FOR CHROME DRIVER
		driver = new ChromeDriver(); // FOR SELENIUM WEB DRIVER

// Open Registration page.
		if ("Personalized".equalsIgnoreCase(ISConstant.learningProgram)) {
			driver.navigate()
					.to("http://164.52.198.42:" + ISConstant.port + "/istest/student/signup/international-schooling"); // Personalized
			academicStartDate = true;
		} else if ("Collaborative".equalsIgnoreCase(ISConstant.learningProgram)) {
			driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/student/signup?signupMode=BATCH"); // Colaborative
		} else if ("Accelerated".equalsIgnoreCase(ISConstant.learningProgram)) {
			driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/student/signup/international-schooling?signupMode=SCHOLARSHIP");
			academicStartDate = true;
		}
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Explicit wait Variable created for
																				// loader
		JavascriptExecutor js = (JavascriptExecutor) driver; // To interact with pop-ups like fee selection

// SignUp page details	
		signupPage();
		

		if (atDashboard) {
			System.out.print("STUDENT AT DASHBOARD");
			if (driver.findElement(By.id("saveEligibleCourse")).isDisplayed() || driver.findElement(By.id("selectStudentCourseProceed")).isDisplayed()) {
				driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
				adminLoginPage();	

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/a")));
				driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/a")).click(); // Manage LMS

				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/ul/li[3]/a")));
				driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/ul/li[3]/a")).click(); // Manage session

				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[1]/div[2]/div/label/input")));
				driver.findElement(By.xpath(
						"/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[1]/div[2]/div/label/input"))
						.sendKeys(ISConstant.id); // Search Id
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[2]/div/table/tbody/tr/td[5]")));
					String stringGradeNumber = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[1]/div/div[2]/div/div/div[2]/div/table/tbody/tr/td[5]")).getText();
					String[] sgn = stringGradeNumber.split("-");
					gNumber= Integer.parseInt(sgn[1]);
					driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
					loginPage();
			}
			academicDate();
			restOfInstallments();
			managesession();
			transcript();
			migration();
			
		}

// Verify Account
		if (verifyAccount) {
			WebElement elementVerify = driver.findElement(By.xpath("//*[@id=\"accountConfirmation\"]/div[1]/h1[1]"));
			String VerifyText = elementVerify.getText();
			if ("Account Verification".equalsIgnoreCase(VerifyText)) {
				verifyAccount();
				Thread.sleep(2000); // to fetch data from database
			}
			login = true;
		}
// Login 
		if (login) {
			WebElement elementVerified = driver.findElement(By.id("statusMessage"));
			String VerifiedMessage = elementVerified.getText();
			if ("User has been verified now! Please login to proceed".equalsIgnoreCase(VerifiedMessage)) {
				driver.findElement(By.xpath("/html/body/div[12]/p/a")).click(); // accept cookies
				driver.findElement(By.xpath("/html/body/div[6]")).click();
				loginPage();
				continueWithStep1 = true;
				planFresh = true;
			}
		}

		if (backToStep1) {
			if(!driver.findElement(By.xpath("//*[@id=\"steps-uid-0\"]/div[3]/ul/li[3]/a")).isDisplayed()) {
			if(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[4]/div[1]/input")).isDisplayed()) {
				System.out.println("Step 1");
				continueWithStep1 = true;
			} else if(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/h3")).isDisplayed()) {
				System.out.println("Step 2");
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Back")));
				driver.findElement(By.linkText("Back")).click();
				continueWithStep1 = true;
			} else if(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[3]/form/div/h3")).isDisplayed()) {
				System.out.println("Step 3");
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Back")));
				driver.findElement(By.linkText("Back")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Back")));
				driver.findElement(By.linkText("Back")).click();
				continueWithStep1 = true;
			}
			} else {
				System.out.println("Step 4");
				String planText =driver.findElement(By.xpath("//*[@id=\"signupStage6Content\"]/section/div/div/div/div/h3")).getText();
				if(!planText.contains("CUSTOMIZED")) {
					wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Confirm & Pay")));
					driver.findElement(By.linkText("Confirm & Pay")).click();
				} else {
					wait.until(ExpectedConditions.elementToBeClickable(By.linkText("CONFIRM & PAY")));
					driver.findElement(By.linkText("CONFIRM & PAY")).click();
				}
					if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
						wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
								"//*[@id=\"primary-pg\"]/div[3]/div")));
						driver.findElement(By.xpath(
								"//*[@id=\"primary-pg\"]/div[3]/div"))
								.click();
						wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
								"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
						gatewayStripe();
						Thread.sleep(10000);
						driver.navigate().refresh();
						academicDate();
						restOfInstallments();
						managesession();
						transcript();
						migration();
					}
			}
		}
		if (continueWithStep1) {
		signupPageStep1();         // SignUp Form STEP 1 = User details
		signupPageStep2();         // SignUp Form STEP 2 = User Parent details
		signupPageStep3();         // SignUp Form Step 3 = Course Selection
		signupPageStep4();         // SignUp Form Step 4 = Payment Plan 

		if (!onlyBookAnEnrollment) {
			academicDate();        // Academic date selection				
			restOfInstallments();  // Paying Rest of installments 	
			managesession();       // Manage session		
			transcript();          // Transcript 
			migration();           // Migration		
			}
	}
	}
// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::	
	
	public static void migration () throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if(ISConstant.migrationEnable) {
			driver.navigate().refresh();
			String text = driver.findElement(By.className("page-title-wrapper")).getText();
			System.out.println(text);
			if(!text.equalsIgnoreCase("")) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("divNextSession")));
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			String mgn = driver.findElement(By.xpath("//*[@id=\"divNextSession\"]/div[2]/div/div/div/div/div[5]/span/b")).getText();
			String[] smgn = mgn.split("-");
			gNumber = Integer.parseInt(smgn[1]);
			gNumber = gNumber-1;
			driver.findElement(By.linkText("ENROLL IN NEXT GRADE")).click();
			if(ISConstant.learningProgramDuringMigration.equalsIgnoreCase("Personalized")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("choiceOneToOne")));
				driver.findElement(By.id("choiceOneToOne")).click();
			}else if(ISConstant.learningProgramDuringMigration.equalsIgnoreCase("Collaborative")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("choiceBatch")));
				driver.findElement(By.id("choiceBatch")).click();
			}else {wait.until(ExpectedConditions.elementToBeClickable(By.id("choiceScholarship")));
			driver.findElement(By.id("choiceScholarship")).click();
			}	
			}
			
			gNumber= gNumber+1;
			if(gNumber >=6 && gNumber <=8) {
				middle =true;
			} else if(gNumber >=9 && gNumber <=12) {
				high = true;
			}
			if(driver.findElement(By.id("nextSesionStep")).isDisplayed()) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("nextSesionStep")));
			if(gNumber>=6) {
			String cred = driver.findElement(By.id("totalCreditKg")).getText();
			credNumber = Float.parseFloat(cred);
			if((middle || high) && credNumber<2) {
			courseSelection();
			}
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.id("nextSesionStep")));
			nextText = driver.findElement(By.xpath("//*[@id=\"divNextSessionCourseChoose\"]/div[1]/div/div/div[2]")).getText();
			driver.findElement(By.id("nextSesionStep")).click();
			signupPageStep4();
		} else {
			wait.until(ExpectedConditions
					.elementToBeClickable(By.cssSelector("button[class='btn btn-success pl-4 pr-4 pull-right']")));
			driver.findElement(By.cssSelector("button[class='btn btn-success pl-4 pr-4 pull-right']")).click();
			
			}
			// check box and all... 
			wait.until(ExpectedConditions
					.elementToBeClickable(By.id("chkval")));
			driver.findElement(By.id("chkval")).click();
			wait.until(ExpectedConditions
					.elementToBeClickable(By.id("payTabData")));
			driver.findElement(By.id("payTabData")).click();
			
			if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"//*[@id=\"primary-pg\"]/div[3]/div")));
				driver.findElement(By.xpath(
						"//*[@id=\"primary-pg\"]/div[3]/div"))
						.click();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
				gatewayStripe();
				Thread.sleep(10000);
				driver.navigate().refresh();
				academicDate();
				restOfInstallments();
				managesession();
				transcript();
				migration();
			}
		}
	}
	
	public static void academicDate() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		
		Thread.sleep(4000);
		if (driver.findElement(By.id("saveEligibleCourse")).isDisplayed() || driver.findElement(By.id("selectStudentCourseProceed")).isDisplayed()) {
			if(gNumber==9) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("6631")));
				driver.findElement(By.id("6631")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("saveEligibleCourse")));
				driver.findElement(By.id("saveEligibleCourse")).click();				
			} else if(gNumber==10) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("6915")));
				driver.findElement(By.id("6915")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("9487")));
				driver.findElement(By.id("9487")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("saveEligibleCourse")));
				driver.findElement(By.id("saveEligibleCourse")).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/div[1]/div[3]/div/div/div[3]/div/span[2]/input")));
			driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div[3]/div/span[2]/input"))
					.sendKeys(Keys.ENTER);
					 
			driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div/div[3]/button")).click();
		}
	}
	
	public static void customPlan() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
		adminLoginPage();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText("Payment")));
		driver.findElement(By.linkText("Payment")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText("ADD PAYMENT/CUSTOM PAYMENT")));
		driver.findElement(By.linkText("ADD PAYMENT/CUSTOM PAYMENT")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("searchEmail")));
		driver.findElement(By.id("searchEmail")).sendKeys(ISConstant.id);
		driver.findElement(By.linkText("SEARCH")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText("CUSTOM PAYMENT DETAILS")));
		String currentTab = driver.getWindowHandle();
		driver.findElement(By.linkText("CUSTOM PAYMENT DETAILS")).click();  // on click new tab open
		Thread.sleep(2000);
		Set <String> newTabs = driver.getWindowHandles();
		for(String tab : newTabs) {
			if(!currentTab.equalsIgnoreCase(tab)) {
				driver.switchTo().window(tab);
			}
		}
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("premiumAmount")));
		driver.findElement(By.id("premiumAmount")).clear();
		driver.findElement(By.id("premiumAmount")).sendKeys("399");   // premium
		driver.findElement(By.id("discountAmount")).clear();
		driver.findElement(By.id("discountAmount")).sendKeys("399");  // discount
		if(driver.findElement(By.id("firstInstallment")).getText().equalsIgnoreCase("0")) {
		driver.findElement(By.id("firstInstallment")).clear();
		driver.findElement(By.id("firstInstallment")).sendKeys("786.687");
		}
		driver.findElement(By.id("noOfInstallment")).sendKeys(Keys.DOWN);
		driver.findElement(By.id("durationWithin")).sendKeys(Keys.UP);
		driver.findElement(By.id("discountApplicableFor")).sendKeys(Keys.DOWN , Keys.DOWN);
		driver.findElement(By.xpath("//*[@id=\"saveNConfirm\"]/button[2]")).click();       // save and confirm
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("resetDeleteErrorWarningYes1")));
		driver.findElement(By.id("resetDeleteErrorWarningYes1")).click();
		
		Thread.sleep(2000);
		driver.close();
		driver.switchTo().window(currentTab);
		driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
		loginPage();
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("CONFIRM & PAY")));
		customPlanCheck = true;
	}
	
	public static void signupPageStep4() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if ("advantage".equalsIgnoreCase(ISConstant.plan)) {
			Thread.sleep(700);
			if(nextText.contains("Enroll In Next Grade")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Proceed")));
				driver.findElement(By.linkText("Proceed")).click();
			}else {
			wait.until(ExpectedConditions
					.elementToBeClickable(By.cssSelector("button[class='btn theme-bg primary-bg white-txt-color text-right']")));
			driver.findElement(By.cssSelector("button[class='btn theme-bg primary-bg white-txt-color text-right']")).click();  // Proceed Button
			}
//			make condition for this
			if(nextText.contains("Enroll In Next Grade")) {
				wait.until(ExpectedConditions
						.elementToBeClickable(By.cssSelector("button[class='btn btn-success pl-4 pr-4 pull-right']")));
				driver.findElement(By.cssSelector("button[class='btn btn-success pl-4 pr-4 pull-right']")).click();
			} else {
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Confirm & Pay")));
			}
			if(ISConstant.customPlanEnabled) {
				customPlan();
			}
			if(customPlanCheck) {
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("CONFIRM & PAY")));
				driver.findElement(By.linkText("CONFIRM & PAY")).click();
			} else {
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Confirm & Pay")));
			driver.findElement(By.linkText("Confirm & Pay")).click();
				if(nextText.contains("Enroll In Next Grade")) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("chkval")));
					driver.findElement(By.id("chkval")).click();
					driver.findElement(By.id("payTabData")).click();
					
				}
			}
			Thread.sleep(500);
			if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
//check proceed button class at signup page
				driver.findElement(By.xpath(
						"//*[@id=\"primary-pg\"]/div[3]/div"))
						.click();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
				gatewayStripe();
			}

		} else if ("easy".equalsIgnoreCase(ISConstant.plan)) {
			WebElement easyPlan = driver.findElement(
					By.xpath("//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[1]/div/div[2]/label"));
			js.executeScript("arguments[0].click();", easyPlan);
			Thread.sleep(2000);
			wait.until(ExpectedConditions
					.elementToBeClickable(By.cssSelector("button[class='btn theme-bg primary-bg white-txt-color text-right']")));
			driver.findElement(By.cssSelector("button[class='btn theme-bg primary-bg white-txt-color text-right']")).click();// Proceed Button
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Confirm & Pay")));
			if(ISConstant.customPlanEnabled) {
				customPlan();
			}
			if(customPlanCheck) {
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("CONFIRM & PAY")));
				driver.findElement(By.linkText("CONFIRM & PAY")).click();
			} else {
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Confirm & Pay")));
			driver.findElement(By.linkText("Confirm & Pay")).click();
			}

			if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
				WebElement payNow = driver.findElement(By.xpath("//*[@id=\"primary-pg\"]/div[3]/div"));
				js.executeScript("arguments[0].click();", payNow);// Because button is not interact able
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
				gatewayStripe();
			}
//			nextInstallments = true;
		} else if ("BookeAnEnrollement".equalsIgnoreCase(ISConstant.plan)) {
			Thread.sleep(700);
			bookAnEnrollment();

			Thread.sleep(500);
			if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
				driver.findElement(By.xpath(
						"/html/body/div[1]/div[2]/div/div[2]/section[4]/div/div[3]/div/div/div[2]/section/div[3]/div[2]/div[1]/div[1]/div[3]/div/span"))
						.click();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
				gatewayStripe(); // payment final click
				onlyBookAnEnrollment = true;
			}
		} else if ("easyWithBookAnEnrollement".equalsIgnoreCase(ISConstant.plan)) {

			Thread.sleep(700);
			bookAnEnrollment();

			Thread.sleep(500);
			if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
				driver.findElement(By.xpath(
						"/html/body/div[1]/div[2]/div/div[2]/section[4]/div/div[3]/div/div/div[2]/section/div[3]/div[2]/div[1]/div[1]/div[3]/div/span"))
						.click();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
				gatewayStripe(); // payment final click

				Thread.sleep(10000);
				driver.navigate().refresh();

				js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
				driver.findElement(By.linkText("Back")).click();

				WebElement easyPlan = driver.findElement(By.xpath(
						"//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[1]/div/div[2]/label"));
				js.executeScript("arguments[0].click();", easyPlan);
				Thread.sleep(2000);
				WebElement feeProceed = driver.findElement(By.xpath(
						"//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[2]/button"));
				js.executeScript("arguments[0].scrollIntoView();", feeProceed);
				feeProceed.click();// Proceed Button
				js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Confirm & Pay")));
				driver.findElement(By.linkText("Confirm & Pay")).click();

				if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
					WebElement payNow = driver.findElement(By.xpath("//*[@id=\"primary-pg\"]/div[3]/div"));
					js.executeScript("arguments[0].click();", payNow);// Because button is not interactable
					wait.until(ExpectedConditions.elementToBeClickable(By.id("cardNumber")));
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
							"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
					gatewayStripe();
				}
			}
//			nextInstallments = true;
		} else if ("advantageWithBookAnEnrollement".equalsIgnoreCase(ISConstant.plan)) {
			Thread.sleep(700);
			bookAnEnrollment();

			Thread.sleep(500);
			if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
				driver.findElement(By.xpath(
						"/html/body/div[1]/div[2]/div/div[2]/section[4]/div/div[3]/div/div/div[2]/section/div[3]/div[2]/div[1]/div[1]/div[3]/div/span"))
						.click();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
				gatewayStripe(); // payment final click

				Thread.sleep(10000);
				driver.navigate().refresh();

				js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
				driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/ul/li[3]/a")).click();

				Thread.sleep(500);
				if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
					driver.findElement(By.xpath(
							"/html/body/div[1]/div[2]/div/div[2]/section[4]/div/div[3]/div/div/div[2]/section/div[3]/div[2]/div[1]/div[1]/div[3]/div/span"))
							.click();
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
							"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
					gatewayStripe();
				}
			}
		}
		Thread.sleep(10000);
		driver.navigate().refresh();
	}
	
	public static void transcript() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		if(!driver.findElement(By.xpath("//*[@id=\"main-nav\"]/li[5]/a")).getText().equalsIgnoreCase("My Transcript")) {
		generateTranscript();
	} else {
		driver.findElement(By.xpath("//*[@id=\"main-nav\"]/li[5]/a")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"stuTranscript\"]/tbody/tr/td[4]")));
		String getGradeString = driver.findElement(By.xpath("//*[@id=\"stuTranscript\"]/tbody/tr/td[4]")).getText();
		String[] arrayGrade= getGradeString.split("-");
		int getGradeNumber = Integer.parseInt(arrayGrade[1]);
		
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("button[class='btn-wide mb-2 mr-2 btn btn-primary btn-sm']")));
		driver.findElement(By.cssSelector("button[class='btn-wide mb-2 mr-2 btn btn-primary btn-sm']")).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"stuTranscript\"]/tbody/tr/td[6]/div/div")));
		String trans = driver.findElement(By.xpath("//*[@id=\"stuTranscript\"]/tbody/tr/td[6]/div/div")).getText();
		System.out.println(trans);  // using to check the user at the migration stage or not
		int xCharacters = trans.length();
		char gA =trans.charAt(xCharacters-2);
		char gB =trans.charAt(xCharacters-1);
		System.out.println(gA+gB);
		String stringTransGradeNumber = ""+gA+gB;
		int transGradeNumber = Integer.parseInt(stringTransGradeNumber);
		
//		if(!(getGradeNumber==transGradeNumber)) {
			System.out.println("print transcript");
			generateTranscript();
//		}
	}
	}
	
	public static void generateTranscript() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if(ISConstant.transcriptPrint) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		System.out.println("Create Transcript");
		driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
		adminLoginPage();	

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/a")));
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/ul/li[4]/a")).click(); // Manage LMS

		wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText("Student Transcript")));
		driver.findElement(By.linkText("Student Transcript")).click(); // Student transcript
		wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText("STUDENT TRANSCRIPT SEARCH")));   
		driver.findElement(By.linkText("STUDENT TRANSCRIPT SEARCH")).click();          // Student transcript search
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("studentEmail")));
		driver.findElement(By.id("studentEmail")).sendKeys(ISConstant.id);
		driver.findElement(By.cssSelector("button[class='btn btn-success btn-shadow float-right pr-4 pl-4']")).click();
		String cgd = driver.findElement(By.xpath("//*[@id=\"myTable\"]/tbody/tr/td[6]")).getText();
		String[] cgdNumber = cgd.split("-");
		gNumber = Integer.parseInt(cgdNumber[1]);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("button[class='btn btn-danger dropdown-toggle dropdown-toggle-split btn-sm']")));
		driver.findElement(By.cssSelector("button[class='btn btn-danger dropdown-toggle dropdown-toggle-split btn-sm']")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText("Transcript - GRADE-0"+gNumber)));    
		String currentTab = driver.getWindowHandle(); // current tab
		driver.findElement(By.linkText("Transcript - GRADE-0"+gNumber)).click();     // on click transcript open in new tab open
		Thread.sleep(2000);

// multiple tab handling
		Set <String> newTab = driver.getWindowHandles();  // after click there are two tabs
		
		for (String tab : newTab) {
			if(!currentTab.equalsIgnoreCase(tab)) {
				driver.switchTo().window(tab);
			}
		}
		String newtabTitle = driver.getTitle();
		System.out.println(newtabTitle);
//		driver.close(); //                    only for checkimg purpose ====================================
		if(driver.getTitle().equalsIgnoreCase("International Schooling :: Transcript")) {
		
		if(gNumber <= 8) {
		    numberOfCourse = 6;
		} else {
			numberOfCourse = 9;
		}
		for (int i=1; i<=numberOfCourse; i++) {
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("gradeObtained-"+i)));
		driver.findElement(By.id("gradeObtained-"+i)).sendKeys(Keys.DOWN);
		driver.findElement(By.id("percentageObtained-"+i)).clear();
		driver.findElement(By.id("percentageObtained-"+i)).sendKeys("99");
		}
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		driver.findElement(By.xpath("//*[@id=\"studentSemesterMarksForm\"]/div[5]/div/div[2]/div[2]/div")).sendKeys("This is sample remarks Text. This is sample remarks Text.");
		driver.findElement(By.id("semesterBGraduateDate")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("semesterBIssueDate")).sendKeys(Keys.ENTER);
		driver.findElement(By.id("programStatus")).sendKeys(Keys.DOWN);
		driver.findElement(By.id("search")).click();   // Save transcript
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"studentSemesterMarksForm\"]/div[6]/div[4]/a")));
		String previousTab = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[@id=\"studentSemesterMarksForm\"]/div[6]/div[4]/a")).click();   // on click open new tab
		Thread.sleep(3500);
		Set<String> nextTab = driver.getWindowHandles();
		for(String tab : nextTab) {
			if(!previousTab.equalsIgnoreCase(tab)&&!currentTab.equalsIgnoreCase(tab)) {
				driver.switchTo().window(tab);
			}
			
		}
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("showTranscriptStudent")));   
		driver.findElement(By.id("showTranscriptStudent")).click();     // click on save button
		Thread.sleep(1000);
		driver.findElement(By.id("publishSemestermarks")).click();   // publish transcript
		Thread.sleep(3000);
//		driver.navigate().refresh();
		driver.switchTo().window(previousTab);
		Thread.sleep(1000);
		driver.close();
		Thread.sleep(1001);
		driver.switchTo().window(currentTab);
		}
		driver.close();
		driver.switchTo().window(currentTab);
		driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
		Thread.sleep(2000);
		loginPage();
		}
	}
	
	public static void gatewayStripe() throws InterruptedException {

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

	public static void signupPage() throws InterruptedException {
		sendKeysID("email", ISConstant.id);
		sendKeysID("confirmEmail", ISConstant.id);
		Thread.sleep(1200);

		WebElement element1 = driver.findElement(By.xpath("/html/body/section/div/div[2]/div[1]/div/div[3]/a"));
		String infoText = element1.getText();
		System.out.println("getted text is " + infoText);

		if ("Login".equalsIgnoreCase(infoText)) {
			System.out.println("entered in loop");
			Thread.sleep(1000);
			driver.findElement(By.xpath("/html/body/section/div/div[2]/div[1]/div/div[3]/a")).click();
			driver.findElement(By.xpath("/html/body/div[12]/p/a")).click(); // accept cookies
			loginPage();
			Thread.sleep(2000);
			String titleName = driver.getTitle();
			verifyAccount = false;
			login = false;
			if (titleName.contains("Dash")) {
				atDashboard = true;
			} else {
				backToStep1 = true;
			}

		} else {
			sendKeysID("password", "a1234567");
			sendKeysID("confirmPassword", "a1234567");
			sendKeysID("captcha", "a");
			driver.findElement(By.id("checkTerms")).click();
			driver.findElement(By.id("signupButton")).click();
			verifyAccount = true;
		}
		Thread.sleep(3000); // to fetch data from databse
	}

	public static void verifyAccount() {
		driver.navigate().to("http://164.52.198.42:" + ISConstant.port
				+ "/istest/common/verify-email/international-schooling/?email=" + ISConstant.id);
	}

	public static void signupPageStep1() throws InterruptedException {
		Thread.sleep(2000);
		WebElement s1e1 = driver
				.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[1]/div[1]/input"));
		String s1element1 = s1e1.getAttribute("value");
		if (s1element1 != "") {
			System.out.println("Step1 Data Prefilled");
		} else {
			sendKeysXPATH("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[1]/div[1]/input",
					ISConstant.firstName);
			sendKeysXPATH("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[1]/div[2]/input",
					ISConstant.middleName);
			sendKeysXPATH("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[1]/div[3]/input",
					ISConstant.lastName);
			driver.findElement(By.xpath(
					"/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[2]/div[1]/span/span[1]/span/span[1]"))
					.click();
			if(ISConstant.grade<=5) {
			driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("GRADE-0"+ISConstant.grade, Keys.ENTER);
			} else if(ISConstant.grade>=6 && ISConstant.grade <=8) {
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("GRADE-0"+ISConstant.grade, Keys.ENTER);
//				middle =true;
			} else if(ISConstant.grade ==9) {
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("GRADE-0"+ISConstant.grade, Keys.ENTER);
//				high = true;
			} else {
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("GRADE-"+ISConstant.grade, Keys.ENTER);
//				high = true;
			}
			
			sendKeysXPATH("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[2]/div[2]/input", "11-22-2017");
			driver.findElement(By.xpath(
					"/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[2]/div[3]/span/span[1]/span/span[1]"))
					.click();
			driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("MALE", Keys.ENTER);
			sendKeysXPATH("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[4]/div[2]/div/div[2]/input",
					ISConstant.phoneNumber);
			if(driver.findElement(By.id("select2-countryId-container")).getText().equalsIgnoreCase("Select Country*")) {
				driver.findElement(By.id("select2-countryId-container")).click();
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("India" , Keys.DOWN, Keys.ENTER);
				driver.findElement(By.id("select2-stateId-container")).click();
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("Delhi", Keys.ENTER);
				driver.findElement(By.id("select2-cityId-container")).click();
				driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("Delhi", Keys.ENTER);
				
				
			}
		}
			WebElement elementGrade = driver.findElement(By.id("select2-applyStandardId-container"));
			String eleGrade = elementGrade.getText();
			String[] grade = eleGrade.split("-");
			gNumber = Integer.parseInt(grade[1]);
			if(gNumber >=6 && gNumber <=8) {
				middle =true;
			} else if(gNumber >=9 && gNumber <=12) {
				high = true;
			}
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/ul/li[2]/a")).click();
	}

	public static void signupPageStep2() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[1]/div[1]/input")));
		WebElement s2e1 = driver
				.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[1]/div[1]/input"));
		String s2element1 = s2e1.getAttribute("value");
		if (s2element1 != "") {
			System.out.println("Step2 Data Prefilled");
		} else {
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[1]/div[1]/input"))
					.sendKeys(ISConstant.parentFistName);
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[1]/div[2]/input"))
					.sendKeys(ISConstant.parentMiddleName);
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[2]/div[1]/input"))
					.sendKeys(ISConstant.parentLastName);
			driver.findElement(By.xpath(
					"/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[2]/div[2]/span/span[1]/span/span[1]"))
					.click();
			driver.findElement(By.xpath("/html/body/span/span/span[1]/input")).sendKeys("Father", Keys.ENTER);
		}
// Parent SignUp
		    parentSignup();
	}
	
	public static void signupPageStep3() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		Thread.sleep(2000);
		WebElement elementStep3 = driver
				.findElement(By.xpath("//*[@id=\"signupStage5Content\"]/div[1]/div[2]/h6/a"));
		String step3 = elementStep3.getText();
		if ("Course Selection Guide".equalsIgnoreCase(step3)) {
			courseSelection();
			wait.until(ExpectedConditions
					.elementToBeClickable(By.partialLinkText("Continue")));
			Thread.sleep(2000);
			driver.findElement(By.partialLinkText("Continue")).click();
			Thread.sleep(2000);
		}
	}
	
	public static void courseSelection() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		if(middle) {
			String cred = driver.findElement(By.id("totalCreditKg")).getText();
			if(cred.contains(":")) {
			String[] credit = cred.split(":");
			credNumber = Float.parseFloat(credit[1]);
			} else {
				credNumber = Float.parseFloat(cred);
			}
			if(credNumber<3) {
			 if(gNumber==6 || gNumber==7) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("courseId_1")));
			driver.findElement(By.id("courseId_1")).click();
			if(gNumber==6) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6560")));
			driver.findElement(By.id("course_id_6560")).click();
			}else if(gNumber==7) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6573")));
				driver.findElement(By.id("course_id_6573")).click();
				}					
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
			driver.findElement(By.id("courseId_2")).click();
			if(gNumber==6) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6562")));
			driver.findElement(By.id("course_id_6562")).click();
			}else if(gNumber==7) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6575")));
				driver.findElement(By.id("course_id_6575")).click();
				}
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
			driver.findElement(By.id("courseId_3")).click();
			if(gNumber==6) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6564")));
			driver.findElement(By.id("course_id_6564")).click();
			}else if(gNumber==7) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6577")));
				driver.findElement(By.id("course_id_6577")).click();
				}
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
			driver.findElement(By.id("courseId_4")).click();
			if(gNumber==6) {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6566")));
			driver.findElement(By.id("course_id_6566")).click();
			}else if(gNumber==7) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6579")));
				driver.findElement(By.id("course_id_6579")).click();
				}
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
			driver.findElement(By.id("courseId_5")).click();
			if(gNumber==6) {
				Thread.sleep(1000);
				if(driver.findElement(By.id("course_id_6568")).isDisplayed()) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6568")));
					driver.findElement(By.id("course_id_6568")).click();
			} 
			else { 
				Thread.sleep(1000); 
				if(driver.findElement(By.id("course_id_6917")).isDisplayed()) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6917")));
				driver.findElement(By.id("course_id_6917")).click();
			}
				}
			}else if(gNumber==7) {
				Thread.sleep(1000);
				if(driver.findElement(By.id("course_id_6581")).isDisplayed()) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6581")));
					Thread.sleep(1000);
					driver.findElement(By.id("course_id_6581")).click();
				} 
				else  {
				if(driver.findElement(By.id("course_id_6920")).isDisplayed()) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6920")));
					driver.findElement(By.id("course_id_6920")).click();
					}
				}
			}
			
			
			
			
			
			
			} else if (gNumber==8) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("courseId_1")));
				driver.findElement(By.id("courseId_1")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6542")));
				driver.findElement(By.id("course_id_6542")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_3")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6545")));
				driver.findElement(By.id("course_id_6545")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
									
				driver.findElement(By.id("courseId_5")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6551")));
				driver.findElement(By.id("course_id_6551")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_4")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_9494")));
				driver.findElement(By.id("course_id_9494")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("noTeacherAssistanceAvailableYes")));
				driver.findElement(By.id("noTeacherAssistanceAvailableYes")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
//				driver.findElement(By.id("courseId_7")).click();
//				wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6550")));
//				driver.findElement(By.id("course_id_6550")).click();
//				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
			}	
			}
		} else if(high) {
			String cred = driver.findElement(By.id("totalCreditKg")).getText();
			if(cred.contains(":")) {
				String[] credit = cred.split(":");
				credNumber = Float.parseFloat(credit[1]);
				} else {
					credNumber = Float.parseFloat(cred);
				}
			if(credNumber<3) {
				wait.until(ExpectedConditions.elementToBeClickable(By.id("courseId_1")));
				driver.findElement(By.id("courseId_1")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_12535")));
					driver.findElement(By.id("course_id_12535")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6663")));
						driver.findElement(By.id("course_id_6663")).click();
						}
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_2")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6932")));
					driver.findElement(By.id("course_id_6932")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6666")));
						driver.findElement(By.id("course_id_6666")).click();
						}
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_3")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6936")));
					driver.findElement(By.id("course_id_6936")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6937")));
						driver.findElement(By.id("course_id_6937")).click();
						}
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_4")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6922")));
					driver.findElement(By.id("course_id_6922")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6915")));
						driver.findElement(By.id("course_id_6915")).click();
						}
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_5")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6640")));
					driver.findElement(By.id("course_id_6640")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6716")));
						driver.findElement(By.id("course_id_6716")).click();
						}
				
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_6")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6656")));
					driver.findElement(By.id("course_id_6656")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_14344")));
						driver.findElement(By.id("course_id_14344")).click();
						}
				
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_7")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6631")));
					driver.findElement(By.id("course_id_6631")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6707")));
						driver.findElement(By.id("course_id_6707")).click();
						}
				
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				
				driver.findElement(By.id("courseId_8")).click();
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6661")));
					driver.findElement(By.id("course_id_6661")).click();
					wait.until(ExpectedConditions.elementToBeClickable(By.id("apCourseSelectionWarningClose")));
					driver.findElement(By.id("apCourseSelectionWarningClose")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_9479")));
						driver.findElement(By.id("course_id_9479")).click();
						wait.until(ExpectedConditions.elementToBeClickable(By.id("apCourseSelectionWarningClose")));
						driver.findElement(By.id("apCourseSelectionWarningClose")).click();
						wait.until(ExpectedConditions.elementToBeClickable(By.id("noTeacherAssistanceAvailableYes")));
						driver.findElement(By.id("noTeacherAssistanceAvailableYes")).click();
						}
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
				Thread.sleep(4000);
				driver.findElement(By.id("courseId_8")).click();  // for Material fee
				if(gNumber==9) {
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6662")));
					driver.findElement(By.id("course_id_6662")).click();
					}else if(gNumber==10) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_9487")));
						driver.findElement(By.id("course_id_9487")).click();
						wait.until(ExpectedConditions.elementToBeClickable(By.id("noTeacherAssistanceAvailableYes")));
						driver.findElement(By.id("noTeacherAssistanceAvailableYes")).click();
						}
				
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
			}
			
		}
	}

	public static void parentSignup() throws InterruptedException {
		if (ISConstant.parentIdCreation) {
			driver.findElement(
					By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[4]/div/div[2]/div[1]/input"))
					.sendKeys(ISConstant.parentId);
			driver.findElement(By.xpath("/html/body")).click();
			Thread.sleep(2000);

			driver.findElement(
					By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[4]/div/div[2]/div[1]/a"))
					.click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(
					"/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[5]/div/div/div[1]/div/div[1]/input"))
					.sendKeys("123456");
			Thread.sleep(1000);
			driver.findElement(By.xpath(
					"/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[5]/div/div/div[1]/div/div[2]/input"))
					.click();
			Thread.sleep(1000);
			driver.findElement(
					By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[6]/div/div/label/span[1]"))
					.click();
			Thread.sleep(1000);
			driver.findElement(
					By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[8]/div/div[1]/input"))
					.sendKeys("a1234567");
			driver.findElement(
					By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[8]/div/div[2]/input"))
					.sendKeys("a1234567");
		}

		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/ul/li[2]/a")).click();
	}

	public static void loginPage() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.id("email")));
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(ISConstant.id);
		sendKeysXPATH("/html/body/div[1]/div/div[1]/div[2]/div/div/form/div[2]/div/input", "a1234567");
		driver.findElement(By.id("loginButton")).click();
		Thread.sleep(1000);
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

	public static void managesession() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[4]/a/span")));
		driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[4]/a/span")).click();          // click on book a session
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div")));
		String bookAsession = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div")).getText();
		if (bookAsession.equalsIgnoreCase("Your LMS is Disabled. Please contact to administrator for further inquiry.") || bookAsession.equalsIgnoreCase("Your academic year has not started yet. You will be able to book your classroom sessions once your academic year starts")) {
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
			driver.findElement(By.id("sessionSubjectSave")).click();                                      // save 
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[2]/div[1]/div/div/div[1]/button")));
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[1]/div/div/div[2]/div[1]/div/div/div[1]/button")).click();		 // clicked on close
			driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/412dc28c-fdad-4fd3-a00b-667fc1da22ed/common/logout/international-schooling?=fromdashboard");
			loginPage();
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account")));
		}
	}

	public static void bookAnEnrollment() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[3]/button"))
				.click();
		Thread.sleep(500);
		driver.findElement(By.xpath(
				"//*[@id=\"studentPaymentBookSeatModal\"]/div/div/div[2]/div/div/div[2]/div/div[2]/div/div[3]/button"))
				.click();
		Thread.sleep(500);
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/ul/li[3]/a")).click();
		WebElement element2 = driver.findElement(By.xpath(
				"/html/body/div[1]/div[2]/div/div[2]/section[4]/div/div[2]/div/div/div[2]/form/div/div/div/span/label"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element2);
		Thread.sleep(500);
		element2.click();
		driver.findElement(By.xpath(
				"/html/body/div[1]/div[2]/div/div[2]/section[4]/div/div[2]/div/div/div[2]/form/div/div/div/button"))
				.click();
	}
	
	public static void payNow() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.findElement(By.cssSelector("button[class='btn btn-primary']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"chkval\"]")));
		driver.findElement(By.xpath("//*[@id=\"chkval\"]")).click();
		driver.findElement(By.id("payTabData")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"/html/body/div[1]/div[24]/div[3]/div/div/div[2]/section/div[2]/div[2]/div[1]/div[1]/div[3]/div/span")));
		driver.findElement(By.xpath(
				"/html/body/div[1]/div[24]/div[3]/div/div/div[2]/section/div[2]/div[2]/div[1]/div[1]/div[3]/div/span"))
				.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
		gatewayStripe();
		Thread.sleep(10000);
		driver.navigate().refresh();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[5]/a")));
		driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[5]/a")).click();
		Thread.sleep(2000);
	}

	public static void restOfInstallments() throws InterruptedException {
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText("My Account")));
		driver.findElement(By.linkText("My Account")).click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"feeSchedule\"]/tbody/tr[1]/td[3]")));
		String gnString = driver.findElement(By.xpath("//*[@id=\"feeSchedule\"]/tbody/tr[1]/td[3]")).getText();
		String [] gnArray = gnString.split("-");
		gNumber = Integer.parseInt(gnArray[1]);
		if(gNumber >=6 && gNumber <=8) {
			middle =true;
		} else if(gNumber >=9 && gNumber <=12) {
			high = true;
		}
		
		if (driver.findElement(By.cssSelector("button[class='btn btn-primary']")).isDisplayed()) {
			payNow();
			if (driver.findElement(By.cssSelector("button[class='btn btn-primary']")).isDisplayed()) {
				payNow();
				if (driver.findElement(By.cssSelector("button[class='btn btn-primary']")).isDisplayed()) {
					payNow();
			}
		}
	}
	}

}
