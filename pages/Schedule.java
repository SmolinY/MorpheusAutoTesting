package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class Schedule extends ViewPage{
    public Schedule(WebDriver driver){
        super(driver, "schedule-view");
    }

    public boolean getScheduledAppointment(String type){
        By scheduleItem = By.xpath(".//div[@class='m-field schedule-item' and @data-appointmenttype='"+type+"' and div/div[@class='appt-button__scheduled' or @class='appt-button__inprogress' or @class='appt-button__completed']]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'km-loader')]")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("confirmationView")));
        try {
            page.findElement(scheduleItem).click();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
