package capgeminiAutomationTest;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.junit.Assert;
import org.testng.annotations.*;

public class AutomationTestStore {
	public String baseURL = "https://automationteststore.com/";
	String chromeDriverPath = "./drivers/chromedriver.exe";
	String edgeDriverPath = "./drivers/msedgedriver.exe";
	public WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Edge")) {
			// set path to Edge.exe
			System.setProperty("webdriver.edge.driver", edgeDriverPath);
			driver = new EdgeDriver();
		} else {
			
			throw new Exception("Incorrect Browser");
		}
		

//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--remote-allow-origins=*");
//		options.addArguments("--disable notifications");
//		DesiredCapabilities cp = new DesiredCapabilities();
//		cp.setCapability(ChromeOptions.CAPABILITY, options);
//		options.merge(cp);
		driver = new ChromeDriver();
		driver.get(baseURL);
	}

	@Test
	public void Registration() throws Throwable {
		driver.findElement(By.xpath("//a[text()='Login or register']")).click();

		driver.findElement(By.xpath("//button[@title='Continue']")).click();
		driver.findElement(By.name("firstname")).sendKeys("ABC1234");
		driver.findElement(By.name("lastname")).sendKeys("DEF0984");
		driver.findElement(By.name("email")).sendKeys("abc.d@gmail.com");
		driver.findElement(By.name("address_1")).sendKeys("Boston");
		driver.findElement(By.id("AccountFrm_city")).sendKeys("Massachusetts ");
		Select state = new Select(driver.findElement(By.id("AccountFrm_zone_id")));
		state.selectByVisibleText("Aberdeen");
		driver.findElement(By.name("postcode")).sendKeys("00501");
		driver.findElement(By.name("loginname")).sendKeys("abc");
		driver.findElement(By.name("password")).sendKeys("abcdefg");
		driver.findElement(By.name("confirm")).sendKeys("abcdefg");
		Thread.sleep(2000);
		driver.findElement(By.id("AccountFrm_newsletter1")).click();
		driver.findElement(By.id("AccountFrm_agree")).click();
		driver.findElement(By.xpath("//button[@title='Continue']")).click();

	}

	@Test
	public void logIn() throws Throwable {
		driver.findElement(By.xpath("//a[text()='Login or register']")).click();
		Thread.sleep(2000);
		driver.manage().window().maximize();
		Thread.sleep(2000);

		driver.findElement(By.id("loginFrm_loginname")).sendKeys("xyz122");
		driver.findElement(By.id("loginFrm_password")).sendKeys("xyz!122");
		driver.findElement(By.xpath("//button[@title='Login']")).click();

		WebElement actualNameElement = driver
				.findElement(By.xpath("//ul[@id='customer_menu_top']//li[1]/a/div[@class='menu_text']"));
		String actualName = actualNameElement.getText();
		String expectedName = "Welcome back xyz";
		Assert.assertEquals(actualName, expectedName);
		System.out.println("Assert passed");

		Thread.sleep(2000);
		System.out.println("click on skincare");
		WebElement skinCare = driver.findElement(
				By.xpath("//nav/ul/li[4]/a['https://automationteststore.com/index.php?rt=product/category&path=43']"));
		Actions ac1 = new Actions(driver);
		ac1.moveToElement(skinCare).perform();
		System.out.println("after click on skincare");

		Thread.sleep(2000);
		WebElement Eyes = driver.findElement(
				By.xpath("//a[@href ='https://automationteststore.com/index.php?rt=product/category&path=43_47']"));
		Actions ac2 = new Actions(driver);
		ac2.doubleClick(Eyes).perform();
		System.out.println("click on eyes");

		String productName, productPrice;
		int productQuantity = 2;

		productName = driver.findElement(By.xpath("//div[@class='col-md-3 col-sm-6 col-xs-12'][1]/div[1]/div[1]/a[1]"))
				.getText();
		productPrice = driver.findElement(By.xpath(
				"//div[@class='col-md-3 col-sm-6 col-xs-12'][1]/div[2]/div[3]/div[@class='price']/div[@class='oneprice']"))
				.getText();
		driver.findElement(
				By.xpath("//div[@class='col-md-3 col-sm-6 col-xs-12'][1]/div[2]/div[3]/a[@class='productcart']"))
				.click();
		driver.findElement(By.xpath("(//span[@class='menu_text' and text()='Checkout'])[1]")).click();

		driver.findElement(By.id("checkout_btn")).click();

		String checkoutProdName = driver
				.findElement(By.xpath("//table[@class='table confirm_products']/tbody/tr[1]/td[2]/a")).getText();
		String checkoutProdPrice = driver
				.findElement(By.xpath("//table[@class='table confirm_products']/tbody/tr[1]/td[3]")).getText();
		System.out.println("before parseint");
		int checkoutProdQuantity = Integer.parseInt(
				driver.findElement(By.xpath("(//table[@class='table confirm_products']/tbody/tr[1]/td)[4]")).getText());

		Assert.assertEquals(productName.toLowerCase(), checkoutProdName.toLowerCase());
		Assert.assertEquals(productPrice, checkoutProdPrice);
		Assert.assertEquals(productQuantity, checkoutProdQuantity);

	}

	@AfterTest
	public void terminateBrowser() {
		driver.close();
	}

}
