package hooks;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooksfile {

	public static WebDriver driver;

	@Before
	public void setup() {
		if (driver == null) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, java.util.concurrent.TimeUnit.SECONDS);
		}
	}

	@After
	public void teardown(Scenario sc) {
		if (sc.isFailed() == true) {
			String fileWithPath = "E:\\ATTPL_Project\\Attpltesting\\Screenshot\\failedScreenshot.png";
			TakesScreenshot scrShot = ((TakesScreenshot) driver);

			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination
			File DestFile = new File(fileWithPath);

			// Copy file at destination
			try {
				FileUtils.copyFile(SrcFile, DestFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Attach screenshot to report
			byte[] screenshot = scrShot.getScreenshotAs(OutputType.BYTES);
			sc.attach(screenshot, "image/png", "Failed Test Screenshot");
		}

		driver.quit();
		driver = null;
	}
}
