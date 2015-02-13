package MorpheusAutoTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

public class Report extends ViewPage {
    public Report(WebDriver driver){
        super(driver, "Report");
    }

    @FindBy(xpath = "//button[@id='completeButton' and text()='Sign']")
    public Button sign;

    public ViewPage signReport(){
        sign.click();
        return new ViewPage(driver, "drawScreen");
    }
}
