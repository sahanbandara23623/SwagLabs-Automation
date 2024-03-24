package TestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;

public class itemCheckOutAutomation {

    String BaseURL = "https://www.saucedemo.com/";

    String expectedText;
    String actualText;
    WebDriver driver;

    @BeforeTest
    public void BeforeTestMethod() throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();


    }

    //Item Checkout Process TC:004
    @Test(priority = 1)
    public void itemCheckoutFunction() throws InterruptedException {
        System.out.println("TC:004");
        System.out.println("------------------------------------------------------");
        userLogin(); //User Logged to the System
        Thread.sleep(2000);

        slbAddCartClick(); //Sauce Labs Backpack Add to Cart
        Thread.sleep(2000);

        clickAddCart(); //Click cart Icon to Navigate for Your Cart Page
        Thread.sleep(2000);

        expectedText = "Your Cart";
        actualText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

        if (actualText.equals(expectedText))  //Check whether user in the Right Destination.
        {
            System.out.println("User in the Your Cart Page");
            System.out.println("Product Adding Process and Navigate to the Your Cart Page is Successfully");
        } else {
            System.out.println("Tc:004 is Failed");
        }

        clickCheckoutBtn(); // Go to Checkout Function

        Thread.sleep(2000);

        //Collect user information for the Checkout Process
        enterUserInformation();
        expectedText = "Checkout: Overview";
        actualText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();
        if (actualText.equals(expectedText)) {

            System.out.println("User in the Checkout Overview Page");
            System.out.println("User can See the Total Payment Cost");
        } else {
            System.out.println("TC:004 is Failed");
        }

    }

    //Validate Price total TC:005
    @Test(priority = 2)
    public void validatePriceTotal() throws InterruptedException {

        System.out.println("-----------------------------------------------------");
        System.out.println("TC:005");
        System.out.println("-----------------------------------------------------");

        Thread.sleep(2000);

        String itemTotal;
        String tax;
        String total;

        itemTotal = extractNumericValue(driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[6]")).getText());
        tax = extractNumericValue(driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[7]")).getText());
        total = extractNumericValue(driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]")).getText());

        try {
            double item_price = Double.parseDouble(itemTotal);
            double calculated_tax = Double.parseDouble(tax);
            double total_price = Double.parseDouble(total);

            if (Math.abs(total_price - (item_price + calculated_tax)) < 0.01) {
                expectedText = "32.39";
                actualText = String.valueOf(total_price);

                if (expectedText.equals(actualText)) {

                    System.out.println("Total calculation is Correct.");
                    System.out.println("TC 005 is PASS");
                } else {
                    System.out.println("Total calculation is Incorrect.");
                    System.out.println("TC 005 is Failed");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric values. Check the format of the text.");
            System.out.println("TC 005: FAIL");

        }



    }

    @Test(priority = 3)
    public void finishCheckoutProcess() throws InterruptedException {
        System.out.println("-----------------------------------------------------");
        System.out.println("TC:006");
        System.out.println("-----------------------------------------------------");
        finishedButtonClick(); //Call the Checkout Finished Button Function

        expectedText = "Thank you for your order!";
        actualText = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")).getText();

        if (actualText.equals(expectedText)) {  //Check whether Order is Successful or Not
            System.out.println("order Checkout Function Happened Correctly");


            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"back-to-products\"]")).click();

            expectedText = "Products";
            actualText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

            if (actualText.equals(expectedText)) {  //Checking User Navigate to the Landing Page(Product Page) or Not
                System.out.println("Order Checkout Function is Totally Finished properly");
                System.out.println("User Navigated to the Home Page(Product Page)");
                System.out.println("TC:006 is Pass");
                System.out.println("Item CheckOut Function is Correctly Proceed");

            }


        } else {
            System.out.println("something wrong in the Order Checkout Function");
            System.out.println("TC:006 is Failed");
        }


    }

    @AfterTest
    public void AfterTestMethod() {
    }

    //login Function
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
            System.out.println("user in the Product Page");
        } else {
            System.out.println("User Credentials are Wrong");
        }
    }

    //Item add cart function
    public void slbAddCartClick() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).click();
        Thread.sleep(3000);
    }

    //Go to Cart Page
    public void clickAddCart() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        Thread.sleep(4000);

    }


    //Click Checkout Function
    public void clickCheckoutBtn() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"checkout\"]")).click();
        Thread.sleep(3000);

        expectedText = "Checkout: Your Information";
        actualText = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

        if (actualText.equals(expectedText)) {
            System.out.println("User in the Checkout : Your Information Page");  //Check whether user in the Right Information Form.
        } else {
            System.out.println("User in the Wrong Destination");
        }
    }

    //Enter user Information Function
    public void enterUserInformation() throws InterruptedException {

        driver.findElement(By.xpath("//*[@id=\"first-name\"]")).sendKeys("Sahan"); //Send USer Information
        driver.findElement(By.xpath("//*[@id=\"last-name\"]")).sendKeys("Bandara"); //Send USer Information
        driver.findElement(By.xpath("//*[@id=\"postal-code\"]")).sendKeys("70620"); //Send USer Information

        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();

    }

    //Extract Numeric Values
    private String extractNumericValue(String text) {
        // Extract numeric part after colon and trim spaces
        String numericPart = text.split(":")[1].trim();

        // Remove non-numeric characters except for the decimal point
        return numericPart.replaceAll("[^0-9.]", "");
    }

    //Finish Checkout Button
    public void finishedButtonClick() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"finish\"]")).click();
        Thread.sleep(2000);
    }


}
