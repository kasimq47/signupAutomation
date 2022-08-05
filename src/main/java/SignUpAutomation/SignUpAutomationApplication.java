package SignUpAutomation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.bonigarcia.wdm.WebDriverManager;
import isSignup.ISConstant;
import isSignup.ISsignup;

@SpringBootApplication
public class SignUpAutomationApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SignUpAutomationApplication.class, args);
		
		
		WebDriverManager.chromedriver().setup(); // FOR CHROME DRIVER
		// ChromeDriverManager.getInstance().setup();
		WebDriver driver = new ChromeDriver(); // FOR SELENIUM WEB DRIVER
		
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20)); //Explicit wait Variable created for loader
		JavascriptExecutor js = (JavascriptExecutor) driver; //To interact with pop-ups like fee selection
		
		// Open Registration page.
				if ("p".equalsIgnoreCase(ISConstant.learningProgram)) {
					driver.navigate()
							.to("http://164.52.198.42:" + ISConstant.port + "/istest/student/signup/international-schooling"); // Personalized
				} else if ("c".equalsIgnoreCase(ISConstant.learningProgram)) {
					driver.navigate().to("http://164.52.198.42:" + ISConstant.port + "/istest/student/signup?signupMode=BATCH"); // Colaborative
				}

				driver.manage().window().maximize();

																						// loader

		// SignUp page details	
				ISsignup.signupPage();
				Thread.sleep(2000); // to fetch data from databse

	}

}
