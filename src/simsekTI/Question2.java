package simsekTI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.testng.asserts.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Question2 {

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "Util/chromedriver");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.weightwatchers.com/us/");

		SoftAssert sa = new SoftAssert();
		sa.assertEquals(driver.getTitle(), "WW (Weight Watchers): Weight Loss & Wellness Help | WW USA",
				"Title does not match.");

		WebElement findWorkshop = driver.findElement(By.linkText("Find a Workshop"));
		findWorkshop.click();
		sa.assertEquals(driver.getTitle(), "Find WW Studios & Meetings Near You | WW USA", "Title does not match.");

		String zipcode = "22031";

		WebElement locationFinder = driver.findElement(By.id("location-search"));
		locationFinder.sendKeys(zipcode);

		WebElement searchButton = driver.findElement(By.id("location-search-cta"));
		searchButton.click();
		Thread.sleep(3000);

		List<WebElement> results = driver.findElements(By.xpath("//div[@class='container-3SE46']"));

		WebElement firstResult = results.get(0).findElement(By.className("linkUnderline-1_h4g"));

		String firstTitle = firstResult.getText();
		String firstDistance = results.get(0).findElement(By.className("distance-OhP63")).getText();

		System.out.println("First result for " + zipcode + " is: " + firstTitle + ".");
		System.out.println("Distance to " + firstTitle + " is: " + firstDistance + ".");

		firstResult.click();

		// ===== &&& ===== //

		String studioTitle = driver.findElement(By.className("locationName-1jro_")).getText();

		sa.assertEquals(studioTitle, firstTitle, "Studio titles do not match.");

		try {
			WebElement hoursOp = driver.findElement(By.className("announcementText-1td3y"));
			System.out.println(hoursOp.getText());
		} catch (NoSuchElementException exception) {
			System.out.println("This location might be closed due to COVID-19.");
		}

		printMeetings("sat", driver);

		System.out.println("Print operation is over.");

		driver.quit();
	}

	private static void printMeetings(String day, WebDriver driver) {
		String[] days = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
		int indexNo = -1;
		for (int i = 0; i < days.length; i++) {
			if (days[i].equalsIgnoreCase(day)) {
				indexNo = i;
			}
		}

		if (indexNo == -1) {
			System.out.println("Day entry is invalid.");
		} else {

			List<WebElement> workshops = driver.findElements(By.className("workshopSchedule-2foP8")); // Studio &
																										// Virtual

			if (workshops.isEmpty()) {
				System.out.println("This location currently does not offer any studio or virtual classes.");
			} else {

				List<WebElement> studioWorkshops = workshops.get(0)
						.findElements(By.cssSelector("div[class^='day-NhBOb']"));
				List<WebElement> virtualWorkshops = workshops.get(1)
						.findElements(By.cssSelector("div[class^='day-NhBOb']"));

				// ===== Studio Classes ===== //

				List<WebElement> sdClasses = studioWorkshops.get(indexNo)
						.findElements(By.cssSelector(".meeting-14qIm>span:nth-child(2)"));

				if (sdClasses.isEmpty()) {
					System.out.println("There are no Studio Classes on the indicated day.");
				} else {
					List<String> sdTeachers = new ArrayList<String>();
					for (int i = 0; i < sdClasses.size(); i++) {
						sdTeachers.add(sdClasses.get(i).getText());
					}
					System.out.println("Studio Classes:");
					Set<String> setSTeachers = new HashSet<String>(sdTeachers);
					for (String t : setSTeachers) {
						System.out.println(t + "\t" + Collections.frequency(sdTeachers, t));
					}
				}

				// ===== Virtual Classes ===== //

				List<WebElement> vdClasses = virtualWorkshops.get(indexNo)
						.findElements(By.cssSelector(".meeting-14qIm>span:nth-child(2)"));

				if (vdClasses.isEmpty()) {
					System.out.println("There are no Virtual Classes on the indicated day.");
				} else {
					List<String> vdTeachers = new ArrayList<String>();

					for (int i = 0; i < vdClasses.size(); i++) {
						vdTeachers.add(vdClasses.get(i).getText());
					}
					System.out.println("Virtual Classes:");
					Set<String> setVTeachers = new HashSet<String>(vdTeachers);
					for (String t : setVTeachers) {
						System.out.println(t + "\t" + Collections.frequency(vdTeachers, t));
					}
				}
			}
		}
	}

}
