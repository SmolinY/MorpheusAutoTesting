package MorpheusAutoTesting.elements;

import MorpheusAutoTesting.pages.ViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.Select;

public class Form {
    protected WebElement form;

    public Form(WebElement form){
        this.form = form;
    }

    public Form fillField(String name, String value){
        WebElement field = form.findElement(By.xpath(".//div[contains(@class,'"+name+"')]//input | .//div[contains(@class,'"+name+"')]//select"));
        if (field.getTagName().equalsIgnoreCase("input")) {
            field.clear();
            field.sendKeys(value);
        }
        else if(field.getTagName().equalsIgnoreCase("select")){
            Select select = new Select(field);
            select.selectByValue(value);
        }
        return this;
    }

    public ViewPage clickOnField(String fieldName, String pageId, WebDriver driver){
        form.findElement(By.xpath(".//div[contains(@class,'"+fieldName+"')]/div/span")).click();
        return new ViewPage(driver, pageId);
    }
}
