package letenky.pomsTest;

import letenky.poms.HomePage;
import letenky.poms.RezervacePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class HomePageTest {
    WebDriver driver;
    @BeforeEach
    public void setDriver(){
        System.setProperty("webdriver.chrome.driver","C:/Users/START/Downloads/jars na TESTOVANI/chrome_driver_114/chromedriver.exe");
        driver = new ChromeDriver();

    }
//    @AfterEach
    public void endDriver(){
        driver.close();
    }


    @Test
    public void search_fromVIE_toLON_returnsFlights() throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud("Vídeň");
        hp.typeKam("Londýn");

        WebElement day = hp.searchDayInMonth("16", "ČERVENEC 2023");
        if(day == null){
            System.out.println("failed");
        }
        if (day != null) {
            day.click();
        }
        hp.searchButtonClick();

        RezervacePage rp  = new RezervacePage(driver);

        List<WebElement> e1 = rp.getFlightStops().get(0).findElements(By.className("iata"));
        Assertions.assertEquals("VIE", e1.get(0).getText());
        Assertions.assertEquals("LGW", e1.get(1).getText());

        driver.close(); // todo remove
    }

//    Vídeň VIE	Praha PRG  date
    @ParameterizedTest(name = "#{index} run test with args {0}{1}{2}{3} expected result {4}{5}")
    @CsvSource({"Vídeň, Praha, 1, ČERVENEC 2023, VIE, PRG"})
    public void search_inputArgs_returnsFlights(String from, String to, String day, String month, String code_start, String code_end) throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud(from);
        hp.typeKam(to);

        WebElement dayButton = hp.searchDayInMonth(day, month);
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }

        hp.searchButtonClick();

        RezervacePage rp = new RezervacePage(driver);

//        List<WebElement> e1 = rp.getFlightStops().get(0).findElements(By.tagName("div"));
        List<WebElement> e1 = rp.getFlightStops().get(0).findElements(By.className("iata"));
        Assertions.assertEquals(code_start, e1.get(0).getText());
        Assertions.assertEquals(code_end, e1.get(1).getText());

        driver.close();
    }

//    Adana UAB London LON  2.8.  null
    @ParameterizedTest(name = "#{index} run test with args {0} {1} {2} expected result error dialog")
    @CsvSource({"Londýn, 2, SRPEN 2023"})
    public void search_fromUAB_toArg1_dateArg23_returnError(String to, String day, String month) throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud("Incirlik");
        hp.typeKam(to);

        WebElement dayButton = hp.searchDayInMonth(day, month);
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        try {
            Thread.sleep(1000);
            WebElement errorDialog = driver.findElement(By.xpath("//div[@slot='modal-header']"));
            Assertions.assertNotNull(errorDialog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driver.close();
    }

//    London LON  Adana UAB  2.8.  null
    @ParameterizedTest(name = "#{index} run test with args {0} {1} {2} expected result error dialog")
    @CsvSource({"Londýn, 2, SRPEN 2023"})
    public void search_fromArg1_toUAB_dateArg23_returnError(String from, String day, String month) throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud(from);
        hp.typeKam("Incirlik");

        WebElement dayButton = hp.searchDayInMonth(day, month);
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        try {
            Thread.sleep(1000);
            WebElement errorDialog = driver.findElement(By.xpath("//div[@slot='modal-header']"));
            Assertions.assertNotNull(errorDialog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driver.close();
    }

//    from VIE to Arg   date
    @ParameterizedTest(name = "#{index} run test with args {0} {1} {2} {3} expected result LON {4}")
    @CsvSource({"Praha, 2, SRPEN 2023, PRG"})
    public void search_fromLON_toArg1_dateArg23_returnFlights(String to, String day, String month, String code_end) throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud("VIE");
        hp.typeKam(to);

        WebElement dayButton = hp.searchDayInMonth(day, month);
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        RezervacePage rp = new RezervacePage(driver);

//        List<WebElement> e1 = rp.getFlightStops().get(0).findElements(By.tagName("div"));
        List<WebElement> e1 = rp.getFlightStops().get(0).findElements(By.className("iata"));
        Assertions.assertEquals("VIE", e1.get(0).getText());
        Assertions.assertEquals(code_end, e1.get(1).getText());

        driver.close();
    }

//    Adana UAB London LON  null  null
    @ParameterizedTest(name = "#{index} run test with args {0} {1} expected result nothing happened")
    @CsvSource({"Incirlik, Londýn"})
    public void search_fromArg1_toArg2_dateNULL_returnNothing(String from, String to) throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud(from);
        hp.typeKam(to);

        WebElement dayButton = hp.searchDayInMonth("", "");
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        try {
            Thread.sleep(2000);
            WebElement hledatButton = driver.findElement(By.xpath("//span[normalize-space()='Hledat']"));
            Assertions.assertNotNull(hledatButton);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        driver.close();
    }


    @Test
    public void search_fromUAB_toUAB_date_returnNothing() throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud("Incirlik");
        hp.typeKam("Incirlik");

        WebElement dayButton = hp.searchDayInMonth("2", "SRPEN 2023");
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        try {
            Thread.sleep(1000);
            WebElement errorDialog = driver.findElement(By.xpath("//div[@slot='modal-header']"));
            Assertions.assertNotNull(errorDialog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //todo make assertions

        driver.close();
    }

    @Test
    public void search_fromLON_toLON_date_returnNothing() throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud("Londýn");
        hp.typeKam("Londýn");

        WebElement dayButton = hp.searchDayInMonth("2", "SRPEN 2023");
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        try {
            Thread.sleep(1000);
            WebElement errorDialog = driver.findElement(By.xpath("//div[@slot='modal-header']"));
            try{
                WebElement errorMessageElement = driver.findElement(By.xpath("//p[normalize-space()='Musí být rozdílná volba odletu Odkud a Kam!']"));
                Assertions.assertNotNull(errorMessageElement);
            }catch (Exception e){
                e.printStackTrace();
            }
            Assertions.assertNotNull(errorDialog);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //todo make assertions

        driver.close();
    }

    @Test
    public void search_fromUAB_toUAB_dateNULL_returnNothing() throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud("Incirlik");
        hp.typeKam("Incirlik");

        WebElement dayButton = hp.searchDayInMonth("", "");
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        try {
            Thread.sleep(2000);
            WebElement hledatButton = driver.findElement(By.xpath("//span[normalize-space()='Hledat']"));
            Assertions.assertNotNull(hledatButton);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //todo make assertions

        driver.close();
    }

    @Test
    public void search_fromLON_toLON_dateNULL_returnNothing() throws InterruptedException {
        HomePage hp = new HomePage(driver);
//        hp.cookiesButtonClick();
        hp.clickJednosmernaButton();
        hp.typeOdkud("Londýn");
        hp.typeKam("Londýn");

        WebElement dayButton = hp.searchDayInMonth("", "");
        if(dayButton == null){
            System.out.println("failed");
        }
        if (dayButton != null) {
            dayButton.click();
        }
        hp.searchButtonClick();

        try {
            Thread.sleep(2000);
            WebElement hledatButton = driver.findElement(By.xpath("//span[normalize-space()='Hledat']"));
            Assertions.assertNotNull(hledatButton);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //todo make assertions

        driver.close();
    }

}
