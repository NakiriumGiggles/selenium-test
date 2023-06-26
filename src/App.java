import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
    static void test() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\juanm\\OneDrive\\Documents\\selenium driver\\chrome driver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(8))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);

        String usernameID = "user-name";
        String passID = "password";
        String loginbtnID = "login-button";
        String menuID = "react-burger-menu-btn";
        String logoutbtnID = "logout_sidebar_link";
        String errorclassName = "error-message-container";

        // Navigate to the login page
        driver.get("https://www.saucedemo.com/");
        System.out.println("Login page title: " + driver.getTitle());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(loginbtnID)));
        
        WebElement username = driver.findElement(By.id(usernameID));
        WebElement password = driver.findElement(By.id(passID));
        WebElement loginbtn = driver.findElement(By.id(loginbtnID));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginbtn.click();

        //scenario 1
        // check for successful login
        try {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(menuID)));
            System.out.println("Login success");
            WebElement menu = driver.findElement(By.id(menuID));
            menu.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(logoutbtnID)));

            WebElement logoutbtn = driver.findElement(By.id(logoutbtnID));
            logoutbtn.click();
            
            //check for successful logout
            try {

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(loginbtnID)));
                System.out.println("Logout success");

            } catch (Exception e) {
                System.out.println("Logout failed");
            }

        } catch (Exception e) {
            System.out.println("Login failed");
        }

        //scenario 2
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(loginbtnID)));
        
        username = driver.findElement(By.id(usernameID));
        password = driver.findElement(By.id(passID));
        loginbtn = driver.findElement(By.id(loginbtnID));

        username.sendKeys("locked_out_user");
        password.sendKeys("secret_sauce");
        loginbtn.click();

        //check for error message
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(errorclassName)));
            String message = driver.findElement(By.className(errorclassName)).getText();
            System.out.println("Error message displayed: " + message);
        } catch (Exception e) {
            System.out.println("Error message not displayed");
        }
        driver.quit();
    }

    public static void main(String[] args) throws Exception {
        test();
    }
}
