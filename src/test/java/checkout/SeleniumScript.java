package checkout;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class SeleniumScript {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void invokeDriver() {
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));         //implicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));    //Explicit wait
    }


    public boolean selectFirstProduct() {

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[class='col-lg-4 col-md-6 mb-4']")));
        List<WebElement> productList = driver.findElements(By.cssSelector("div[class='col-lg-4 col-md-6 mb-4']"));
        for (WebElement product : productList) {
            product.click();
            return true;
        }
        return false;
    }

    public boolean AddToCart() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a[onclick='addToCart(1)']")));
            driver.findElement(By.cssSelector("a[onclick='addToCart(1)']")).click();
            driver.switchTo().alert().accept();
            return true;
        } catch (TimeoutException e) {
            System.out.println("AddTOCart button not found");
            return false;
        }
    }

    public void checkTheCart() {
        driver.findElement(By.id("cartur")).click();
    }

    public void clickOnPlaceOrder() {
        driver.findElement(By.cssSelector("button[class='btn btn-success']")).click();
    }

    public void validateProductList() {
    }

    public void fillUpTheForm() {
        driver.findElement(By.id("name")).sendKeys("Shivam");
        driver.findElement(By.id("country")).sendKeys("India");
        driver.findElement(By.id("city")).sendKeys("Noida");
        driver.findElement(By.id("card")).sendKeys("XYZ");
        driver.findElement(By.id("month")).sendKeys("1");
        driver.findElement(By.id("year")).sendKeys("2023");
    }

    public void clickOnPurchase() {
        driver.findElement(By.cssSelector("button[onclick='purchaseOrder()']")).click();
    }

    public boolean validatePurchaseConform(){
        try {
            driver.findElement(By.cssSelector("div[class='sweet-alert  showSweetAlert visible']")).isDisplayed();
            return true;
        }
        catch (NoSuchElementException e){
            return false;
        }
    }
    @Test
    public void Successful_Checkout() {

        Assert.assertTrue(selectFirstProduct());
        Assert.assertTrue(AddToCart());
        checkTheCart();
        clickOnPlaceOrder();
        fillUpTheForm();
        clickOnPurchase();
        Assert.assertTrue(validatePurchaseConform());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
