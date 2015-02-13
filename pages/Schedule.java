package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Schedule extends ViewPage{
    public Schedule(WebDriver driver){
        super(driver, "schedule-view");
    }

    public boolean getScheduledAppointment(String type){
        By scheduleItem = By.xpath("//div[@class='m-field schedule-item' and @data-appointmenttype='"+type+"' and div/div[@class='appt-button__scheduled']]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'km-loader')]")));
        try {
            page.findElement(scheduleItem).click();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
