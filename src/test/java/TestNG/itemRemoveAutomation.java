package TestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class itemRemoveAutomation {

    String BaseURL = "https://www.saucedemo.com/";

    WebDriver driver;

    String actualText;

    String expectedText;

    Boolean status;


    @BeforeTest
    public void BeforeTestMethod() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();


    }

    //    Test Case ValidateLogin and navigate to Product Page(Tc:001)
    @Test(priority = 1)
    public void validateLogin() throws InterruptedException {

        System.out.println("---TC:001");
        userLogin();//User Login Function Invoking

        Thread.sleep(3000);
        addItemCart();

        expectedText = "Products";
        actualText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText(); //Check User in the Right page or not

        if (expectedText.equals(actualText)) {
            System.out.println("Test:001 is Passed");
            System.out.println("user Correctly Logged in to the SwagLabs System");
            System.out.println("User in the Product Page");
        } else {
            System.out.println("Tc:001 is Failed");
            System.out.println("User Login Validation is Failed");
            System.out.println("User in the Wrong Destination");
        }


    }

    //    Test Case Add Item and Go to the Cart Page(Tc:002)
    @Test(priority = 2)
    public void addItemCart() throws InterruptedException {
        System.out.println("---TC:002");
        slbAddCartClick();  //Add Item Function Automation

        Thread.sleep(3000);
        clickAddCart();  //Go to Cart Page
        Thread.sleep(2000);

        expectedText = "Sauce Labs Backpack";
        actualText = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText();  //Check Added item or Not

        if (actualText.equals(expectedText)) {
            System.out.println("Test:002 is Passed");
            System.out.println("user Correctly Add a Item and Correctly Navigate to the Cart Page");
            System.out.println("User in the Your Cart Page");
        } else {
            System.out.println("Tc:002 is Failed");
            System.out.println("Unfortunately User Haven't Add a Item to Cart   ");
            System.out.println("User in the Wrong Destination");
        }


    }

    //Item Remove automation TestNG (TC:003)
    @Test(priority = 3)
    public void itemRemoveFromCart() throws InterruptedException {
        System.out.println("---TC:003");
        slbRemoveCart(); // Sauce Labs Backpack Remove form Cart

        expectedText = "Description";
        actualText = driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[2]")).getText();

        if (actualText.equals(expectedText)) {
            System.out.println("Tc:003 is Passed");
            System.out.println("Item was Successfully Removed From Cart");
        } else {
            System.out.println("Item Remove function is not working Properly");
        }

        Thread.sleep(3000);
        cstCart(); //Continue Shopping Function
        Thread.sleep(2000);

        expectedText = "Products";
        actualText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

        if (actualText.equals(expectedText)) {
            System.out.println("After Successfully Item Remove operation User Land to the Product Page correctly");
        } else {
            System.out.println("Tc:003 is Failed");
        }

    }

    @AfterTest
    public void AfterTestMethod() {

    }

    //Login Function
    public void userLogin() throws InterruptedException {
        driver.get(BaseURL);
        Thread.sleep(3000);

        driver.findElement(By.name("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        Thread.sleep(3000);

        expectedText = "Products";
        actualText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

        if (actualText.equals(expectedText)) {
            System.out.println("User Enter Valid USer Credentials");
        } else {
            System.out.println("User Credentials are Wrong");
        }
    }

    //Invalid USer Credentials Function
    public void invalidUSerLoginCredentials() throws InterruptedException {

        driver.get(BaseURL);
        Thread.sleep(3000);

        driver.findElement(By.name("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.name("password")).sendKeys("Chandana123");
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        Thread.sleep(3000);

        expectedText = "Epic sadface: Username and password do not match any user in this service";
        actualText = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")).getText();

        if (actualText.equals(expectedText)) {
            System.out.println("User Enter InValid USer Credentials");
        } else {
            System.out.println("***");
        }

    }

    //Item add cart function
    public void slbAddCartClick() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
        Thread.sleep(3000);
    }

    //Go to Cart Function
    public void clickAddCart() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        Thread.sleep(4000);

    }

    //Item Remove Function
    public void slbRemoveCart() throws InterruptedException {
        expectedText = "Sauce Labs Backpack";
        actualText = driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText();

        if (expectedText.equals(actualText)) {
            System.out.println("User Select the right Cart Item");

        } else {
            System.out.println("Wrong Item Cart");
        }

        driver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]")).click();
        Thread.sleep(3000);


    }

    //Re-direct to the continue Shopping cart Function
    public void cstCart() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"continue-shopping\"]")).click();
    }


}
