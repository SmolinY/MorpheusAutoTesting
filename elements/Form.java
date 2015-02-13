package MorpheusAutoTesting.elements;

import MorpheusAutoTesting.pages.Parts;
import MorpheusAutoTesting.pages.ViewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.Select;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form {
    final String SOME_DATA = "SOME_DATA";
    protected WebDriver driver;
    protected WebElement form;

    public Form(WebElement form, WebDriver driver){
        this.form = form;
        this.driver = driver;
    }

    public Form fillField(String name, String value){
        WebElement field = form.findElement(By.xpath(".//div[contains(@class,'"+name+"')]//input | .//div[contains(@class,'"+name+"')]//select"));
        return fillField(field, value);
    }

    public Form fillField(WebElement field, String value){
        if (!field.isDisplayed()) return this;
        if (field.getTagName().equalsIgnoreCase("input")) {
            if (value.equals(SOME_DATA)){
                field.click();
                if (field.getAttribute("value").isEmpty()) {
                    try {
                        Pattern p = Pattern.compile("Range is ([0-9]+) to ([0-9]+)");
                        Matcher m = p.matcher(field.findElement(By.xpath("../../div[contains(@class,'m-content-hint-block')]")).getText());
                        if (m.matches()) {
                            field.sendKeys(m.group(1));
                        } else {
                            field.sendKeys("1");
                        }
                    }
                    catch (NoSuchElementException e){
                        field.sendKeys("1");
                    }
                }
            }
            else {
                field.clear();
                field.sendKeys(value);
            }
        }
        else if(field.getTagName().equalsIgnoreCase("select")){
            Select select = new Select(field);
            if (value.equals(SOME_DATA)){
                if (select.getFirstSelectedOption().getAttribute("value").isEmpty()) {
                    for (WebElement option : select.getOptions()) {
                        String optionValue = option.getAttribute("value");
                        if (!optionValue.isEmpty()) {
                            select.selectByValue(optionValue);
                            break;
                        }
                    }
                }
            }
            else {
                select.selectByValue(value);
            }
        }
        return this;
    }

    public ViewPage clickOnField(String fieldName, String pageId){
        form.findElement(By.xpath(".//div[contains(@class,'"+fieldName+"')]/div/span")).click();
        return new ViewPage(driver, pageId);
    }

    public Parts clickOnPartsField(){
        form.findElement(By.xpath(".//div[contains(@class,'Part')]/div/input")).click();
        return new Parts(driver);
    }

    public void fillRecommendationFields() {
        try {
            fillField(form.findElement(By.xpath(".//div[contains(@class,'Quantity')]/div/input")), "1");
        }
        catch (NoSuchElementException e){}
        String requiredFields = ".//div[contains(@class,'required-field')]//input[not(@readonly='readonly')] |" +
                ".//div[contains(@class,'required-field')]//select[not(@readonly='readonly')] |"+
                ".//div[contains(@class,'DistributionSystemId')]//select";
        for (WebElement field : driver.findElements(By.xpath(requiredFields)) ) {
            fillField(field, SOME_DATA);
        }
    }


}
