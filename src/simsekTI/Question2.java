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

		// Modify this string to try run the method with different entries.
		String zipcode = "07070";

		// ========== //

		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "Util/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.weightwatchers.com/us/");

		SoftAssert sa = new SoftAssert();
		sa.assertEquals(driver.getTitle(), "WW (Weight Watchers): Weight Loss & Wellness Help | WW USA",
				"Title does not match.");

		driver.findElement(By.linkText("Find a Workshop")).click();
		sa.assertEquals(driver.getTitle(), "Find WW Studios & Meetings Near You | WW USA", "Title does not match.");

		driver.findElement(By.id("location-search")).sendKeys(zipcode);
		driver.findElement(By.id("location-search-cta")).click();
		Thread.sleep(5000);

		//List<WebElement> results = driver.findElements(By.xpath("//div[@class='container-3SE46']"));
		List<WebElement> results = driver.findElements(By.className("container-3SE46"));
		WebElement firstResult = results.get(0).findElement(By.className("linkUnderline-1_h4g"));

		String firstTitle = firstResult.getText();
		String firstDistance = results.get(0).findElement(By.className("distance-OhP63")).getText();

		System.out.println("First result for " + zipcode + " is: " + firstTitle + ".");
		System.out.println("Distance to " + firstTitle + " is: " + firstDistance + ".");
		System.out.println();

		firstResult.click();

		String studioTitle = driver.findElement(By.className("locationName-1jro_")).getText();
		sa.assertEquals(studioTitle, firstTitle, "Studio titles do not match.");

		try {
			WebElement hoursOp = driver.findElement(By.className("announcementText-1td3y"));
			System.out.println("Announcement in lieu of Hours of Operation: " + hoursOp.getText());
			System.out.println();
		} catch (NoSuchElementException exception) {
			System.out.println("This location might be temporarily closed due to COVID-19.");
		}

		// ========== //

		// Modify this string parameter to get class information from a different day.
		printMeetings("sat", driver);

		System.out.println();
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
			return;
		}

		List<WebElement> workshops = driver.findElements(By.className("workshopSchedule-2foP8")); // Studio &
																									// Virtual

		if (workshops.isEmpty()) {
			System.out.println("This location currently does not offer any studio or virtual classes.");
		} else if (workshops.size() == 1) {
			List<WebElement> virtualWorkshops = workshops.get(0)
					.findElements(By.cssSelector("div[class^='day-NhBOb']"));
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
		} else {

			/* STUDIO CLASSES */
			List<WebElement> studioWorkshops = workshops.get(0).findElements(By.cssSelector("div[class^='day-NhBOb']"));
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

			/* VIRTUAL CLASSES */
			List<WebElement> virtualWorkshops = workshops.get(1)
					.findElements(By.cssSelector("div[class^='day-NhBOb']"));
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
