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

    public ViewPage getItem(String name, String view, String type) throws Exception {
        for (WebElement item : page.findElements(By.xpath(".//ul[contains(@class,'items-placeholder')]/li/div[@class='naming']/div/span"))) {
            if (item.getText().equalsIgnoreCase(name)){
                item.click();
                return new ViewPage(driver, view);
            }
        }
        page.findElement(By.xpath(".//div[@class='km-rightitem']/a")).click();
        new ModalWindow(driver, "extended-list-selection").getItemByName(type).click();
        return new ViewPage(driver, view);
    }

    public ViewPage getItem(String name, String view) throws Exception {
        for (WebElement item : page.findElements(By.xpath(".//ul[contains(@class,'items-placeholder')]/li/div[@class='naming']/div/span"))) {
            if (item.getText().equalsIgnoreCase(name)){
                item.click();
                return new ViewPage(driver, view);
            }
        }
        page.findElement(By.xpath(".//div[@class='km-rightitem']/a")).click();
        return new ViewPage(driver, view);
    }
}
