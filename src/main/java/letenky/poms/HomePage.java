package letenky.poms;

import net.bytebuddy.matcher.EqualityMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class HomePage {
    public WebDriver driver;
    public WebDriverWait wait;

    public HomePage(WebDriver driver){

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        this.driver = driver;
        driver.get("https://www.letuska.cz/");
        wait.until(ExpectedConditions.visibilityOf(cookiesButtonAgree));
//        wait.until(ExpectedConditions.visibilityOf(hledatButton));

    }

    public HomePage goToHomePage(){
        driver.get("https://www.letuska.cz/");
        return this;
    }
    @FindBy(xpath = "//span[normalize-space()='Souhlasím']")
    WebElement cookiesButtonAgree;
    @FindBy(xpath = "//input[@placeholder='Odkud']")
    WebElement odkudField;
    @FindBy(xpath = "//horizontal-form-item[@class='isIcon ska-horizontal-form-item']//div[@class='innerWrap']")
    WebElement kamField;
    @FindBy(xpath = "//horizontal-form-item[@class='sfCalendar isIcon ska-horizontal-form-item']//div[@class='innerWrap']")
    WebElement odletNavratField;
    @FindBy(xpath = "//horizontal-form-item[@class='passengers isIcon ska-horizontal-form-item']//div[@class='innerWrap']")
    WebElement osobaField;
    @FindBy(xpath = "//span[normalize-space()='Hledat']")
    WebElement hledatButton;
    @FindBy(xpath = "//span[contains(text(),'Jednosměrná')]")
    WebElement jednosmernaButton;

//    @FindBy(xpath = "")
//    WebElement ;
//    @FindBy(xpath = "")
//    WebElement ;
//    @FindBy(xpath = "")
//    WebElement ;
//

    public HomePage cookiesButtonClick(){
        cookiesButtonAgree.click();
        return this;
    }
    public HomePage clickOdkudField(){
        odkudField.click();
        return this;
    }
    public HomePage typeOdkud(String text) throws InterruptedException {
        clickOdkudField();
        WebElement field2 = driver.findElement(By.xpath("//whisper-input[@class='ng-star-inserted']"));
        field2.sendKeys(text);
        Thread.sleep(2000);
        List<WebElement> tempList = driver.findElements(By.className("whisperList-dstName"));
        try {
//            System.out.println(tempList.get(0).getText());
            tempList.get(0).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public HomePage clickKamField(){
        kamField.click();
        return this;
    }
    public HomePage typeKam(String text) throws InterruptedException {
        clickKamField();
        WebElement field2 = driver.findElement(By.xpath("//whisper-input[@class='ng-star-inserted']"));
        field2.sendKeys(text);
        Thread.sleep(2000);
        List<WebElement> tempList = driver.findElements(By.className("whisperList-dstName"));
        try {
//            System.out.println(tempList.get(0).getText());
            tempList.get(0).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    public HomePage clickJednosmernaButton(){
        jednosmernaButton.click();
        return this;
    }

    public WebElement searchDayInMonth(String day_arg, String month_arg) {
        odletNavratField.click();
        List<WebElement> monthsInCalendar = driver.findElements(By.tagName("datepick-month"));

        WebElement monthInCalendar = null;
        for (WebElement month : monthsInCalendar) {
            WebElement header = month.findElement(By.className("month-header"));
            WebElement nameOfMonth = header.findElement(By.tagName("th"));
            if (Objects.equals(nameOfMonth.getText(), month_arg)) {
                monthInCalendar = month;
//                System.out.println("MONTH FOUND " + nameOfMonth.getText());
            }

        }
        if (monthInCalendar != null){
//            List<WebElement> dayButtons = monthInCalendar.findElements(By.className("date inMonth clickable ng-star-inserted"));
            List<WebElement> dayButtons = monthInCalendar.findElements(By.tagName("td"));

            for (WebElement dayButton : dayButtons) {
                if(Objects.equals(dayButton.getText(), day_arg)){
//                    System.out.println("DAY FOUND " + dayButton.getText() + ", " + dayButton.getTagName());
                    return dayButton;
                }
            }
        }
        else{
            System.err.println("desired month not found");
        }
        return null;
    }

    public HomePage clickOsobaField(){
        osobaField.click();
        return this;
    }
    public HomePage searchButtonClick(){
        wait.until(ExpectedConditions.visibilityOf(hledatButton));
        hledatButton.click();
//        return new RezervacePage(driver);
        return this;
    }
}
