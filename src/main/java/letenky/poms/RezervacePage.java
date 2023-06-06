package letenky.poms;

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

public class RezervacePage {
    public WebDriver driver;
    public WebDriverWait wait;

    public RezervacePage(WebDriver driver) throws InterruptedException {

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        this.driver = driver;
        Thread.sleep(30000);
//        wait.until(ExpectedConditions.visibilityOf(dataLetu));

    }

//    public RezervacePage goToRezervacePage(){
//        driver.get("");
//        return this;
//    }

    @FindBy(xpath = "//div[@id='filtersContainer']//div[@class='sorter TotalPrice selected ng-star-inserted']")
    WebElement sortByPrice;
    @FindBy(xpath = "//div[@id='filtersContainer']//div[@class='filter DepartureDayToleranceFilter ng-star-inserted']//div[@class='description']")
    WebElement dataLetu;
//    @FindBy(xpath = "")
//    WebElement ;
//    @FindBy(xpath = "")
//    WebElement ;
//    @FindBy(xpath = "")
//    WebElement ;
//    @FindBy(xpath = "")
//    WebElement ;

    public List<WebElement> getFlightStops(){
        return driver.findElements(By.className("iata-stops"));
    }

    public boolean validateResultStops(String start, String destinnation){
        if (getFlightStops() != null) {
            System.out.println(getFlightStops().get(0).getText());
            if (Objects.equals(getFlightStops().get(0).findElement(By.className("iata form")).getText(), start)){
                return true;
            }
            return false;
        }
        else return false;
    }
}
