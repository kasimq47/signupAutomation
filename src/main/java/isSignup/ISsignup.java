package isSignup;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
	public static boolean middle;
	public static boolean high;
	public static int gNumber;
	public static int numberOfCourse;

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
		Thread.sleep(3000); // to fetch data from databse

		if (atDashboard) {
			System.out.print("STUDENT AT DASHBOARD");
			restOfInstallments();
			managesession();
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
			Thread.sleep(2000);
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)"); // scroll 1
			Robot rob = new Robot();
			rob.mouseMove(350, 780);
			rob.mousePress(InputEvent.BUTTON1_DOWN_MASK); // click 1
			Thread.sleep(100);
			rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

//			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");         //scroll 2
			Thread.sleep(2000);
			rob.mouseMove(1100, 680);
			rob.mousePress(InputEvent.BUTTON1_DOWN_MASK); // click 2
			Thread.sleep(100);
			rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

//			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");         //scroll 3
			Thread.sleep(2000);
			rob.mouseMove(1299, 306);
			rob.mousePress(InputEvent.BUTTON1_DOWN_MASK); // click 3
			Thread.sleep(100);
			rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			js.executeScript("window.scrollBy(0,document.body.scrollHeight)"); // scroll 4
			Thread.sleep(2000);
			rob.mouseMove(350, 780);
			rob.mousePress(InputEvent.BUTTON1_DOWN_MASK); // click 4
			Thread.sleep(100);
			rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			js.executeScript("window.scrollBy(0,document.body.scrollHeight)"); // scroll 5
			Thread.sleep(2000);
			rob.mouseMove(350, 780);
			rob.mousePress(InputEvent.BUTTON1_DOWN_MASK); // click 5
			Thread.sleep(100);
			rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			continueWithStep1 = true;
			planFresh = false;
		}

// SignUp Form STEP 1
		Thread.sleep(2000);
		if (continueWithStep1) {
			signupPageStep1();
			System.out.println(gNumber);
// SignUp Form STEP 2

			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[2]/form/div/div[1]/div[1]/input")));

			signupPageStep2();
// Parent SignUp
			parentSignup();

// SignUp Form Step 3
			Thread.sleep(2000);
			WebElement elementStep3 = driver
					.findElement(By.xpath("//*[@id=\"signupStage5Content\"]/div[1]/div[2]/h6/a"));
			String step3 = elementStep3.getText();
			if ("Course Selection Guide".equalsIgnoreCase(step3)) {
				if(middle) {
					String cred = driver.findElement(By.id("totalCreditKg")).getText();
					String[] credit = cred.split(":");
					float credNumber = Float.parseFloat(credit[1]);
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
					wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6568")));
					driver.findElement(By.id("course_id_6568")).click();
					}else if(gNumber==7) {
						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6581")));
						driver.findElement(By.id("course_id_6581")).click();
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
						
//						driver.findElement(By.id("courseId_7")).click();
//						wait.until(ExpectedConditions.elementToBeClickable(By.id("course_id_6550")));
//						driver.findElement(By.id("course_id_6550")).click();
//						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("commonloaderId")));
					}	
					}
				} else if(high) {
					String cred = driver.findElement(By.id("totalCreditKg")).getText();
					String[] credit = cred.split(":");
					Float credNumber = Float.parseFloat(credit[1]);
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
				
				
				
				wait.until(ExpectedConditions
						.elementToBeClickable(By.partialLinkText("Continue")));
				Thread.sleep(2000);
				driver.findElement(By.partialLinkText("Continue")).click();
				
				Thread.sleep(2000);
			}

// Plan Selection

			if ("advantage".equalsIgnoreCase(ISConstant.plan)) {
				Thread.sleep(700);
				if (planFresh) {
					WebElement elementFresh = driver.findElement(By.xpath(
							"//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[2]/button"));
					         //*[@id="studentPaymentModal"]/div/div/div[2]/div/div[2]/div[4]/div/div[3]/div/div[2]/button          // Previous
					       
					js.executeScript("arguments[0].scrollIntoView();", elementFresh);
					elementFresh.click();
				} else if (driver.findElement(By.xpath(
						"/html/body/div[1]/div[2]/div/div[2]/section[3]/form/div/div[2]/div[5]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[2]/button"))
						.isDisplayed()) {
					WebElement elementExistwithBook = driver.findElement(By.xpath(
							"/html/body/div[1]/div[2]/div/div[2]/section[3]/form/div/div[2]/div[5]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[2]/button"));
					js.executeScript("arguments[0].scrollIntoView();", elementExistwithBook);
					elementExistwithBook.click();
				} else {
					WebElement elementExsist = driver.findElement(By.xpath(
							"/html/body/div[1]/div[2]/div/div[2]/section[3]/form/div/div[2]/div[5]/div/div/div[2]/div/div[2]/div[4]/div/div[3]/div/div[2]/button"));
					js.executeScript("arguments[0].scrollIntoView();", elementExsist);
					elementExsist.click();
				}
				Thread.sleep(1000);
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

			} else if ("easy".equalsIgnoreCase(ISConstant.plan)) {
				WebElement easyPlan = driver.findElement(
						By.xpath("//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[1]/div/div[2]/label"));
				js.executeScript("arguments[0].click();", easyPlan);
				Thread.sleep(2000);
				WebElement feeProceed = driver.findElement(By.xpath(
						"//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[4]/div/div[3]/div/div[2]/button"));
				js.executeScript("arguments[0].scrollIntoView();", feeProceed);
				driver.findElement(By.xpath(
						"//*[@id=\"studentPaymentModal\"]/div/div/div[2]/div/div[2]/div[4]/div/div[3]/div/div[2]/button"))
						.click();// Proceed Button
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Confirm & Pay")));
				driver.findElement(By.linkText("Confirm & Pay")).click();

				if ("Stripe".equalsIgnoreCase(ISConstant.gateway)) {
					WebElement payNow = driver.findElement(By.xpath("//*[@id=\"primary-pg\"]/div[3]/div"));
					js.executeScript("arguments[0].click();", payNow);// Because button is not interact able
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
							"/html/body/div[1]/div/div/div[2]/div/div[2]/form/div[1]/div/div/div[2]/div/div[1]/div[1]/fieldset/div/div[1]/div/div[1]/span/input")));
					gatewayStripe();
				}
//				nextInstallments = true;
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
//				nextInstallments = true;
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

			if (!onlyBookAnEnrollment) {
				driver.navigate().refresh();
				if (academicStartDate) {
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
			
		

// Paying Rest of installments 	
		restOfInstallments();
		
// Manage session		
		managesession();
		
			}
	}
	}
// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::	
	
	
	
	
	
	
	
	
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
	}

	public static void verifyAccount() {
		driver.navigate().to("http://164.52.198.42:" + ISConstant.port
				+ "/istest/common/verify-email/international-schooling/?email=" + ISConstant.id);
	}

	public static void signupPageStep1() {

		WebElement s1e1 = driver
				.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[2]/section[1]/form/div/div[1]/div[1]/input"));
		String s1element1 = s1e1.getAttribute("value");
		if (s1element1 != "") {
			System.out.println("Step1 Data Prefilled");
//			WebElement elementGrade = driver.findElement(By.id("select2-applyStandardId-container"));
//			String eleGrade = elementGrade.getText();
//			String[] grade = eleGrade.split("-");
//			gNumber = Integer.parseInt(grade[1]);
//			if(gNumber >=6 && gNumber <=8) {
//				middle =true;
//			} else if(gNumber >=9 && gNumber <=12) {
//				high = true;
//			}
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

	public static void signupPageStep2() {

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
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(ISConstant.id);
		sendKeysXPATH("/html/body/div[1]/div/div[1]/div[2]/div/div/form/div[2]/div/input", "a1234567");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/form/div[3]/input")).click();
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

	public static void restOfInstallments() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[5]/a")));
		driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[4]/div[1]/ul/li[5]/a")).click();
		if(driver.findElement(By.xpath("//*[@id=\"feeSchedule\"]/tbody/tr[1]/td[4]")).getText()
				.equalsIgnoreCase("Course Fee - One Time Payment")) {
			nextInstallments = false;
		}else {
			nextInstallments = true;
		}
		if (nextInstallments) {
				if (driver.findElement(By.xpath("//*[@id=\"feeSchedule\"]/tbody/tr[1]/td[4]")).getText()
					.equalsIgnoreCase("Book an Enrollment Seat Fee")) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[3]/td[8]/button")));
				driver.findElement(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[3]/td[8]/button"))
						.click();
				secondPaidWithBook = true;
			} else {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[2]/td[8]/button")));
				driver.findElement(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[2]/td[8]/button"))
						.click();
				secondPaidonly = true;
			}
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
			if (secondPaidWithBook) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[4]/td[8]/button")));
				driver.findElement(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[4]/td[8]/button"))
						.click();
			} else if (secondPaidonly) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[3]/td[8]/button")));
				driver.findElement(By.xpath(
						"/html/body/div/div[2]/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div/table/tbody/tr[3]/td[8]/button"))
						.click();
			}

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
		}

	}

}
