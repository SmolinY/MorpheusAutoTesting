package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Select;

public class Report extends ViewPage {
    public Report(WebDriver driver){
        super(driver, "Report");
    }

    @FindBy(xpath = "//button[@id='completeButton' and text()='Sign']")
    public Button sign;

    @FindBy(id = "Agreements")
    public WebElement agreementsForm;

    public ViewPage signReport(){
        sign.click();
        return new ViewPage(driver, "drawScreen");
    }

    public Report checkAllAgreements(){
        for (WebElement field : agreementsForm.findElements(By.xpath(".//input[@type='checkbox']"))) {
            if (!field.isSelected()) field.click();
        }
        for (WebElement field : agreementsForm.findElements(By.xpath(".//select"))) {
            new Select(field).selectByValue("Yes");
        }
        return this;
    }
}
