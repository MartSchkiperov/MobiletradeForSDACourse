package org.webdriver.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.*;

/*
Tests description
=================
firstItemSimpleAddToCart                               - default adding (1) item to cart
firstItemWriteQuantity1AddToCart                       - in first item page write quantity 1
firstItemClickMinusQuantityCheck                       - clicking quantity "-", with quantity 0 add to cart must give alert
firstItemClickPlusMinusCartQuantityCheck               - clicking quantity "+", clicking quantity "-", quantity check in cart
firstItemWriteQuantityStockPlus1AddToCartMustGiveAlert - in first item page write stock quantity + 1 - add to cart must give alert
firstItemWriteQuantity0AddToCartMustGiveAlert          - in first item page write quantity 0 - add to cart must give alert
firstItemWriteNegativeQuantityAddToCartMustGiveAlert   - in first item page write quantity -1 - add to cart must give alert
firstItemWriteLetterQuantityAddToCartMustGiveAlert     - in first item page write quantity "a" - add to cart must give alert
firstItemWriteEmptyQuantityAddToCartMustGiveAlert      - in first item page write quantity "" - add to cart must give alert
firstItemWriteSpaceQuantityAddToCartMustGiveAlert      - in first item page write quantity " " - add to cart must give alert
firstItemChangeColour                                  - in first item page change colour and check colour in cart
searchItem                                             - search item and open in item page
searchItemAndAddToCart                                 - search item and open in item page and add to cart
 */
public class AddItemsToCartTests {

    private WebDriver driver;
    private final String searchItem = "Apple iPhone 14 128GB";

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mobiletrade.ee/");
        driver.findElement(By.xpath("//*[@id=\"sr-cookie-policy\"]/div/div/div/button")).click();
    }


    @Test
    public void firstItemSimpleAddToCart() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        driver.findElement(By.id("button-cart")).click();

        Thread.sleep(2000); // to avoid glitches with 2 chat elements
        String quantity = driver.findElement(By.xpath("//*[@id=\"cartForm\"]/div[1]/table/tbody/tr/td[3]/input")).getAttribute("value");
        Assertions.assertEquals(1, Integer.parseInt(quantity));
        emptyCart();
    }

    @Test
    public void firstItemClickMinusQuantityCheck() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"multiAddToCartForm\"]/div/a[1]")).click();
        driver.findElement(By.id("button-cart")).click();
        Assertions.assertTrue(isAlertPresent());
        driver.switchTo().alert().accept();
    }

    @Test
    public void firstItemClickPlusMinusCartQuantityCheck() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"multiAddToCartForm\"]/div/a[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"multiAddToCartForm\"]/div/a[1]")).click();
        driver.findElement(By.id("button-cart")).click();

        Thread.sleep(2000); // to avoid glitches with 2 chat elements
        String quantity = driver.findElement(By.xpath("//*[@id=\"cartForm\"]/div[1]/table/tbody/tr/td[3]/input")).getAttribute("value");
        Assertions.assertEquals(1, Integer.parseInt(quantity));
        emptyCart();
    }

    @Test
    public void firstItemWriteQuantity1AddToCart() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        WebElement quantityField = driver.findElement(By.xpath("//*[@id=\"matrix-select-qty\"]"));
        quantityField.clear();
        quantityField.sendKeys("1");
        driver.findElement(By.id("button-cart")).click();

        Thread.sleep(2000); // to avoid glitches with 2 chat elements
        String quantity = driver.findElement(By.xpath("//*[@id=\"cartForm\"]/div[1]/table/tbody/tr/td[3]/input")).getAttribute("value");
        Assertions.assertEquals(1, Integer.parseInt(quantity));
        emptyCart();
    }

    @Test
    public void firstItemWriteQuantityStockPlus1AddToCartMustGiveAlert() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        String itemStockQuantity = driver.findElement(By.id("stock-str")).getText();
        int wrongQuantity = Integer.parseInt(itemStockQuantity) + 1;
        WebElement orderQuantityField = driver.findElement(By.xpath("//*[@id=\"matrix-select-qty\"]"));
        orderQuantityField.clear();
        orderQuantityField.sendKeys(wrongQuantity + "");
        driver.findElement(By.id("button-cart")).click();
        Assertions.assertTrue(isAlertPresent());
        driver.switchTo().alert().accept();
    }

    @Test
    public void firstItemWriteQuantity0AddToCartMustGiveAlert() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        WebElement quantityField = driver.findElement(By.xpath("//*[@id=\"matrix-select-qty\"]"));
        quantityField.clear();
        quantityField.sendKeys("0");
        driver.findElement(By.id("button-cart")).click();
        Assertions.assertTrue(isAlertPresent());
        driver.switchTo().alert().accept();
    }

    @Test
    public void firstItemWriteNegativeQuantityAddToCartMustGiveAlert() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        WebElement quantityField = driver.findElement(By.xpath("//*[@id=\"matrix-select-qty\"]"));
        quantityField.clear();
        quantityField.sendKeys("-1");
        driver.findElement(By.id("button-cart")).click();
        Assertions.assertTrue(isAlertPresent());
        driver.switchTo().alert().accept();
    }

    @Test
    public void firstItemWriteLetterQuantityAddToCartMustGiveAlert() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        WebElement quantityField = driver.findElement(By.xpath("//*[@id=\"matrix-select-qty\"]"));
        quantityField.clear();
        quantityField.sendKeys("a");
        driver.findElement(By.id("button-cart")).click();
        Assertions.assertTrue(isAlertPresent());
        driver.switchTo().alert().accept();
    }

    @Test
    public void firstItemWriteEmptyQuantityAddToCartMustGiveAlert() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        WebElement quantityField = driver.findElement(By.xpath("//*[@id=\"matrix-select-qty\"]"));
        quantityField.clear();
        quantityField.sendKeys("");
        driver.findElement(By.id("button-cart")).click();
        Assertions.assertTrue(isAlertPresent());
        driver.switchTo().alert().accept();
    }

    @Test
    public void firstItemWriteSpaceQuantityAddToCartMustGiveAlert() {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        WebElement quantityField = driver.findElement(By.xpath("//*[@id=\"matrix-select-qty\"]"));
        quantityField.clear();
        quantityField.sendKeys(" ");
        driver.findElement(By.id("button-cart")).click();
        Assertions.assertTrue(isAlertPresent());
        driver.switchTo().alert().accept();
    }

    @Test
    public void firstItemChangeColour() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[2]/div/a")).click();
        driver.findElement(By.id("matrix-select-korpuse-varv")).click();
        driver.findElement(By.cssSelector("option:nth-child(3)")).click();
        String dropdownColourString = driver.findElement(By.cssSelector("option:nth-child(3)")).getText();
        driver.findElement(By.id("button-cart")).click();

        Thread.sleep(2000); // to avoid glitches with 2 chat elements
        Optional<Object> optionalColourString = Optional.empty();
        try {
            optionalColourString = Optional.ofNullable(driver.findElement(By.xpath("//*[@id=\"cartForm\"]/div[1]/table/tbody/tr/td[2]/div")).getText());
        } catch (InvalidSelectorException e) {
            System.out.println(e);
        }
        String colourInCart = "";
        if (optionalColourString.isEmpty()) {
            System.out.println("PROBLEM: Error getting properties from cart");
        } else {
            colourInCart = extractColour(String.valueOf(optionalColourString.get()));
        }
        Assertions.assertEquals(dropdownColourString, colourInCart);
        emptyCart();
    }

    @Test
    public void searchItem() {
        WebElement searchField = driver.findElement(By.id("filter_name"));
        searchField.sendKeys(searchItem);
        driver.findElement(By.className("button-search")).click();
        WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[3]/div[1]/a"));
        Assertions.assertEquals(searchItem, searchResult.getText());
    }

    @Test
    public void searchSmartphoneAndAddToCart() throws InterruptedException {
        WebElement searchField = driver.findElement(By.id("filter_name"));
        searchField.sendKeys(searchItem);
        driver.findElement(By.className("button-search")).click();
        WebElement searchResult = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[3]/div[1]/a"));
        Assertions.assertEquals(searchResult.getText(), searchItem);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div[2]/div/a/img")).click();
        driver.findElement(By.id("button-cart")).click();

        Thread.sleep(2000); // to avoid glitches with 2 chat elements
        String smartphoneNameInCart = driver.findElement(By.xpath("//*[@id=\"cartForm\"]/div[1]/table/tbody/tr/td[2]/a")).getText();
        if (smartphoneNameInCart == null || smartphoneNameInCart.length() < searchItem.length()) smartphoneNameInCart = "";
        Assertions.assertEquals(searchItem, smartphoneNameInCart.substring(0, searchItem.length()));
        emptyCart();
    }


    public void emptyCart() {
        driver.findElement(By.cssSelector("#cartForm > div.cart-info > table > tbody > tr > td.quantity > a > img")).click();
        driver.switchTo().alert().accept();
    }

    @After
    public void tearDown() {
        driver.close();
    }


    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    /*
    Expected input string:
    "Optional[Qty: 1
    Delivery time: Esinduses kohal

    Housing color: Red]"
     */
    public String extractColour(String colourString) {
        if (colourString == null) {
            System.out.println("PROBLEM: cart properties string: null");
            return "";
        }
        if (colourString.equals("")) {
            System.out.println("PROBLEM: cart properties string: empty");
            return "";
        }
        List<String> lines = new ArrayList<>();
        colourString.lines().forEach(lines::add);
        String colourLine = lines.get(lines.size() - 1);
        return lines.get(lines.size() - 1).length() > 14 ? colourLine.substring(15) : "";
    }

}
