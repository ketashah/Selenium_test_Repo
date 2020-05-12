package selenium;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.Select;

public class MakeSpaceTest {

	private static WebDriver driver;
	
	// Declare UI elements
	WebElement btnBookToday;
	WebElement txtZipCode;
	WebElement btnGetStarted;
	WebElement btnHelpMeFind;
	WebElement inputBedsideTableCounter;
	
	@BeforeClass
	public static void Initialize() {
		
		// Environment Variable PATH refers to this directory. No need to add it everytime
		System.setProperty("webdriver.chrome.driver", "C://Selenium//chromedriver.exe");
		driver = new ChromeDriver();
		
		// implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		// Open home page of MakeSpace - 1
		driver.get("https://logistics.staging.mksp.co/");
		
		// Maximize browser 
		driver.manage().window().maximize();
		
		// Assert top nav bar is rendered - 2
		WebElement topNavBar = driver.findElement(By.className("menu-bar"));
		assertTrue("Top Nav Bar should have renderd", topNavBar != null);
		System.out.println("Nav bar found");
	}
	
	@AfterClass
	public static void CleanUp() throws InterruptedException {
		Thread.sleep(5000);
	driver.close();
	}
	
	
	@Before
	public void SetUp() {

	}
	
	
	@Test
	public void TestBooking() throws InterruptedException {
		
		String zipCodeStr = "10038";
		String countbedsideTabStr = "1";
		
		// Book Today - 3
		btnBookToday = driver.findElement(By.xpath("//a[@href='/book']"));
		btnBookToday.click();
		
		// Zipcode and Get Started - 4
		txtZipCode = driver.findElement(By.xpath("//input[@name='zip']"));
		txtZipCode.sendKeys(zipCodeStr);
		btnGetStarted = driver.findElement(By.xpath("//button[@type='submit'][text() [contains(., 'Get Started')]]"));
		btnGetStarted.click();
		
		// Help Me Find a Plan - 5
		btnHelpMeFind = driver.findElement(By.xpath("//a[@href='/storage-calculator/']"));
		btnHelpMeFind.click();
		
		// Zipcode - 6
		txtZipCode = driver.findElement(By.xpath("//input[@name='zip']"));
		txtZipCode.sendKeys(zipCodeStr);
		txtZipCode.sendKeys(Keys.ENTER);
		
		// Bedroom - 7
		WebElement divBedroom = driver.findElement(By.xpath("//div/p[text()='Bedroom']"));
		divBedroom.click();
	
		// Bedside Table - 8
		inputBedsideTableCounter = driver.findElement(By.xpath("//input[@name='bedside-table']"));
		inputBedsideTableCounter.sendKeys(countbedsideTabStr);

		// Ensure Total Cubic Footage is more than 0 - 8
		WebElement spanTotalCubeFootLabel =  driver.findElement(By.xpath("//span[text()[contains(., 'Total Cubic Footage')]]"));
		WebElement spanTotalCubeFootVal = spanTotalCubeFootLabel.findElement(By.xpath("./following-sibling::span[1]"));
		int totalCubeFootRequired = Integer.parseInt(spanTotalCubeFootVal.getText());
		System.out.println("Total Cubic Footage - " + totalCubeFootRequired);
		assertTrue("Upon selecting item, Total Cubic Footage value should be more than 0", totalCubeFootRequired > 0);
		
		// Ensure presence of Customized Plan div - 8
		WebElement divCustomizedPlan = driver.findElement(By.xpath("//div[text()[contains(., 'Customized Plan')]]"));
		assertTrue("Upon selecting item, Customized Plan should be populated", divCustomizedPlan != null);
		
		WebElement spanStoragePlan = driver.findElement(By.xpath("//span[text()[contains(., 'Storage Plan')]]"));
		String storagePlan = spanStoragePlan.getText();
		System.out.println(storagePlan);
		WebElement spanStoragePlanVal = spanStoragePlan.findElement(By.xpath("./following-sibling::span[1]"));
		String storagePlanPrice = spanStoragePlanVal.getText(); 
		System.out.println(storagePlanPrice);
		
		// Living Room - 9
		((JavascriptExecutor) driver).executeScript("scroll(0, -250)");
		WebElement parLivingroom = driver.findElement(By.xpath("//p[text()='Living Room']"));
		WebElement divLivingroom = parLivingroom.findElement(By.xpath(".."));
		divLivingroom.click();
		
		// TV Stand selection - 9
		WebElement inputTvStand = driver.findElement(By.xpath("//input[@name='tv-stand']"));
		inputTvStand.sendKeys("1");
		
		// Side Table selection - 10
		WebElement inputSideTable = driver.findElement(By.xpath("//input[@name='side-table']"));
		inputSideTable.sendKeys("1");
		
		// Ensure total cubic footage and customized plan changed - 11
		spanTotalCubeFootLabel =  driver.findElement(By.xpath("//span[text()[contains(., 'Total Cubic Footage')]]"));
		spanTotalCubeFootVal = spanTotalCubeFootLabel.findElement(By.xpath("./following-sibling::span[1]"));
		int totalCubeFoot2 = Integer.parseInt(spanTotalCubeFootVal.getText());
		System.out.println("Total Cubic Footage 2 - " + totalCubeFoot2);
		assertTrue("Total Cube Footage should have changed", totalCubeFootRequired != totalCubeFoot2);
		divCustomizedPlan = driver.findElement(By.xpath("//div[text()[contains(., 'Customized Plan')]]"));
		spanStoragePlan = driver.findElement(By.xpath("//span[text()[contains(., 'Storage Plan')]]"));
		String storagePlan2 = spanStoragePlan.getText();
		System.out.println("Storage Plan 2 - " + storagePlan2);
		assertTrue("Storage Plan should have changed", storagePlan != storagePlan2);
		
		// Misc. Items - 12
		((JavascriptExecutor) driver).executeScript("scroll(0, -250)");
		WebElement parMiscItems = driver.findElement(By.xpath("//p[contains(., 'Misc. Items')]"));
		WebElement divMiscItems = parMiscItems.findElement(By.xpath(".."));
		divMiscItems.click();
		System.out.println("Misc. Items selected");
		
		// Bins - 13
		WebElement inputBins = driver.findElement(By.xpath("//input[@name='bins']"));
		inputBins.sendKeys("4");
		System.out.println("Bins selected");
		
		// Book Today - 14
		WebElement buttonBookToday = driver.findElement(By.xpath("//button[text()[contains(., 'Book Today')]]"));
		buttonBookToday.click();
		
		// MakeSpace Bin - 15
		WebElement inputMakeSpaceBins = driver.findElement(By.id("bin"));
		inputMakeSpaceBins.sendKeys("4");
		inputMakeSpaceBins.sendKeys(Keys.ENTER);
		
		// Got it
		WebElement buttonGotIt = driver.findElement(By.xpath(".//button[text()[contains(., 'Got it')]]"));
		buttonGotIt.click();
		
		// Yes, No, No, No, No -  16
		(driver.findElement(By.xpath("//div[@data-service='large-items'][text()='Yes']"))).click();
		(driver.findElement(By.xpath("//div[@data-service='bulky-items'][text()='No']"))).click();
		(driver.findElement(By.xpath("//div[@data-service='fragile-items'][text()='No']"))).click();
		(driver.findElement(By.xpath("//div[@data-service='walk-up'][text()='No']"))).click();
		(driver.findElement(By.xpath("//div[@data-service='disassembly'][text()='No']"))).click();
		(driver.findElement(By.className("booking-step-button"))).click();
		
		// Address fill - 17
		(driver.findElement(By.name("address_pretty"))).sendKeys("123 William St");
		(driver.findElement(By.xpath("//div/span[contains(.,'New York, NY, USA')]"))).click();

		// Pick any date - 17
		(driver.findElement(By.className("booking-address-calendar-wrapper"))).click();
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		(driver.findElement(By.xpath("//a[@class='ui-state-default'][contains(.,'30')]"))).click();
		
		WebElement selectedBookingCard = driver.findElement(By.className("booking-card-selected"));
		(selectedBookingCard.findElement(By.xpath(".//div[@class='day-card-timeslot'][contains(.,'8am-11am')]"))).click();
		
		(driver.findElement(By.xpath("//button[@class='button booking-apt-button']"))).click();
		
		// Enter name and phone number - 18
		(driver.findElement(By.xpath("//input[@name='name']"))).sendKeys("Keta");
		(driver.findElement(By.name("phone"))).sendKeys("1234567890");
		System.out.println("Name and phone done!");
		
		(driver.findElement(By.xpath("//span[@color='#3eae5f'][@class='makespace-ui-library-io1oly css-fn76al0']"))).click();
		
		// Continue to Billing - 19
		(driver.findElement(By.xpath("//button[contains(.,'Continue to Billing')]"))).click();
		
		// Enter Credit Card info - 20
		(driver.findElement(By.id("js-cc-number"))).sendKeys("4242424242424242");
		(driver.findElement(By.id("js-cc-exp-date"))).sendKeys("0122");
		(driver.findElement(By.id("js-cc-cvc"))).sendKeys("123");
		(driver.findElement(By.id("js-cc-name"))).sendKeys("Keta Shah");
		(driver.findElement(By.className("ms-radio-circle"))).click();
		
		(driver.findElement(By.xpath("//input[@type='submit'][@value='Continue to Review']"))).sendKeys(Keys.ENTER);
		
		// Phone number and email - 21
		(driver.findElement(By.name("phone"))).sendKeys("1234567890");
		(driver.findElement(By.name("email"))).sendKeys("ketashah@gmail.com");
		
		WebElement divActiveMilitaryRadio = driver.findElement(By.className("armed-forces-radio-wrapper"));
		(divActiveMilitaryRadio.findElement(By.xpath(".//label[@class='booking-radio-label flex']"))).click();
		System.out.println("Military Selection done");
		
		WebElement divTermsRadio = driver.findElement(By.className("booking-review-terms"));
		(divTermsRadio.findElement(By.xpath(".//label[@class='booking-radio-label flex']"))).click();
		System.out.println("Terms agreed!");
		
		// Submit - 22
		(driver.findElement(By.xpath("//input[@type='submit'][@value='Confirm Appointment']"))).sendKeys(Keys.ENTER);
		
		// Confirmation assertion - 23
		WebElement confirmationDiv = driver.findElement(By.xpath("//span[@class='blue-confirmation-check animated booking-inventory-check']"));
		assertTrue("No Confirmation message found", confirmationDiv != null);
				
		// Done - 24
		System.out.println("Successful booking done!");
	}
}	