package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"success-msg\"]/a")));
		WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"success-msg\"]/a"));
		loginBtn.click();
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testAddNote() {
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"nav-notes-tab\"]")));

		WebElement noteBtn = driver.findElement(By.xpath("//*[@id=\"nav-notes-tab\"]"));
		noteBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"nav-notes\"]/button")));
		WebElement addNote = driver.findElement(By.xpath("//*[@id=\"nav-notes\"]/button"));
		addNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement titleBox = driver.findElement(By.id("note-title"));
		titleBox.sendKeys("Note 1");
		WebElement descBox = driver.findElement(By.id("note-description"));
		descBox.sendKeys("Description for note 1");

		WebElement saveBtn = driver.findElement(By.xpath("//*[@id=\"noteModal\"]/div/div/div[3]/button[2]"));
		saveBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/span/a")));
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/div/div/span/a"));
		loginBtn.click();

		WebElement noteBtn2 = driver.findElement(By.xpath("//*[@id=\"nav-notes-tab\"]"));
		noteBtn2.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"userTable\"]/tbody/tr[1]/th")));
		WebElement titleCol = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr[1]/th"));
		WebElement descCol = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[2]"));
		Assertions.assertEquals(titleCol.getText(), "Note 1");
		Assertions.assertEquals(descCol.getText(), "Description for note 1");
	}

	@Test
	public void testEditNote() {
		testAddNote();

		WebElement editBtn = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr[1]/td[1]/button"));
		editBtn.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement titleBox = driver.findElement(By.id("note-title"));
		titleBox.sendKeys(" edited");
		WebElement descBox = driver.findElement(By.id("note-description"));
		descBox.sendKeys(" edited");

		WebElement saveBtn = driver.findElement(By.xpath("//*[@id=\"noteModal\"]/div/div/div[3]/button[2]"));
		saveBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/span/a")));
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/div/div/span/a"));
		loginBtn.click();

		WebElement noteBtn2 = driver.findElement(By.xpath("//*[@id=\"nav-notes-tab\"]"));
		noteBtn2.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"userTable\"]/tbody/tr[1]/th")));
		WebElement titleCol = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr[1]/th"));
		WebElement descCol = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[2]"));
		Assertions.assertEquals(titleCol.getText(), "Note 1 edited");
		Assertions.assertEquals(descCol.getText(), "Description for note 1 edited");
	}

	@Test
	public void testDeleteNote() {
		testAddNote();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		WebElement delBtn = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr[1]/td[1]/a"));
		delBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/span/a")));
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/div/div/span/a"));
		loginBtn.click();

		WebElement noteBtn2 = driver.findElement(By.xpath("//*[@id=\"nav-notes-tab\"]"));
		noteBtn2.click();

		Exception exception = assertThrows(NoSuchElementException.class, () -> {
			WebElement titleCol = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr[1]/th"));
			WebElement descCol = driver.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr/td[2]"));
		});

	}

	@Test
	public void testAddCredentials() {
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"nav-credentials-tab\"]")));

		WebElement noteBtn = driver.findElement(By.xpath("//*[@id=\"nav-credentials-tab\"]"));
		noteBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"nav-credentials\"]/button")));
		WebElement addNote = driver.findElement(By.xpath("//*[@id=\"nav-credentials\"]/button"));
		addNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement urlBox = driver.findElement(By.id("credential-url"));
		urlBox.sendKeys("https://www.youtube.com/");
		WebElement usernameBox = driver.findElement(By.id("credential-username"));
		usernameBox.sendKeys("dainn4");
		WebElement passBox = driver.findElement(By.id("credential-password"));
		passBox.sendKeys("123");

		WebElement saveBtn = driver.findElement(By.xpath("//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]"));
		saveBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/span/a")));
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/div/div/span/a"));
		loginBtn.click();

		WebElement noteBtn2 = driver.findElement(By.xpath("//*[@id=\"nav-credentials-tab\"]"));
		noteBtn2.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th")));
		WebElement urlCol = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th"));
		WebElement usernameCol = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[2]"));
		WebElement passwordCol = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]"));
		Assertions.assertEquals(urlCol.getText(), "https://www.youtube.com/");
		Assertions.assertEquals(usernameCol.getText(), "dainn4");
		Assertions.assertNotEquals(passwordCol.getText(), "123");
	}

	@Test
	public void testEditCredentials() {
		testAddCredentials();

		WebElement editBtn = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button"));
		editBtn.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

        WebElement urlBox = driver.findElement(By.id("credential-url"));
        WebElement descBox = driver.findElement(By.id("credential-username"));
        WebElement passBox = driver.findElement(By.id("credential-password"));

        String initPass = passBox.getText();

		urlBox.sendKeys("demo");
		descBox.clear();
		descBox.sendKeys("dainnph13993@fpt.edu.vn");
		passBox.clear();
		passBox.sendKeys("123456");

        WebElement saveBtn = driver.findElement(By.xpath("//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]"));
        saveBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/span/a")));
        WebElement loginBtn = driver.findElement(By.xpath("/html/body/div/div/span/a"));
        loginBtn.click();

        WebElement noteBtn2 = driver.findElement(By.xpath("//*[@id=\"nav-credentials-tab\"]"));
        noteBtn2.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th")));
        WebElement urlCol = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th"));
        WebElement usernameCol = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[2]"));
        WebElement passwordCol = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]"));
        Assertions.assertEquals(urlCol.getText(), "https://www.youtube.com/demo");
        Assertions.assertEquals(usernameCol.getText(), "dainnph13993@fpt.edu.vn");
        Assertions.assertNotEquals(passwordCol.getText(), "123456");
        Assertions.assertNotEquals(passwordCol.getText(), initPass);
	}

    @Test
    public void testDeleteCredentials() {
        testAddCredentials();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        WebElement delBtn = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a"));
        delBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/span/a")));
        WebElement homeBtn = driver.findElement(By.xpath("/html/body/div/div/span/a"));
        homeBtn.click();

        WebElement noteBtn2 = driver.findElement(By.xpath("//*[@id=\"nav-credentials-tab\"]"));
        noteBtn2.click();

        assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th"));
            driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[2]"));
            driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]"));
        });
    }

}
