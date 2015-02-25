package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ModalWindow extends ViewPage {
    public ModalWindow(WebDriver driver, String windowId){
        super(driver, windowId);
    }

    public List<WebElement> selectionItems() throws Exception {
        for (WebElement placeholder : page.findElements(By.xpath(".//div[contains(@class,'election-placeholder')]"))){
            if (placeholder.isDisplayed()) {
                return placeholder.findElements(By.xpath(".//div[contains(@class,item)]/span"));
            }
        }
        throw new Exception("No items in modal window");
    }

    public WebElement getItemByName(String itemName) throws Exception {
        for (WebElement item : selectionItems()){
            if (item.getText().equalsIgnoreCase(itemName)) return item;
        }
        throw new Exception("No items with name "+itemName);
    }

    public void cancel(){
        page.findElement(By.xpath(".//a[span[text()='Cancel']]")).click();
    }
}
