package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

import java.util.List;

public class ListPage extends ViewPage{
    public ListPage(WebDriver driver, String pageId){
        super(driver, pageId);
    }

    public WebElement checkListItem(String name) throws Exception {
        for (WebElement item : page.findElements(By.xpath(".//ul[contains(@class,'items-placeholder')]/li/div[@class='naming']/div/span"))) {
            if (item.getText().equalsIgnoreCase(name)){
                return item;
            }
        }
        throw new Exception("No item with name "+name);
    }

    public void addNew(){
        page.findElement(By.xpath(".//div[@class='km-rightitem']/a")).click();
    }

    public ViewPage getItem(String name, String view, String type) throws Exception {
        try {
            checkListItem(name).click();
            return new ViewPage(driver, view);
        }
        catch (Exception e){
            addNew();
            new ModalWindow(driver, "extended-list-selection").getItemByName(type).click();
            return new ViewPage(driver, view);
        }
    }

    public ViewPage getItem(String name, String view) throws Exception {
        try {
            checkListItem(name).click();
            return new ViewPage(driver, view);
        }
        catch (Exception e){
            addNew();
            return new ViewPage(driver, view);
        }
    }
}
