package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Parts extends ViewPage{
    public Parts(WebDriver driver){
        super(driver, "Parts");
    }

    public List<WebElement> getAllParts(){
        return driver.findElements(By.xpath(".//div[contains(@class,'part-item')]"));
    }

    public void selectFirstPart(){
        getAllParts().get(0).click();
    }
}
