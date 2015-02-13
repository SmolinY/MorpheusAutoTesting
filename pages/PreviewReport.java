package MorpheusAutoTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

public class PreviewReport extends ViewPage {
    public PreviewReport(WebDriver driver){
        super(driver, "Savings");
    }

    @FindBy(xpath = "//button[@id='completeButton' and text()='Complete final report']")
    public Button completeButton;

    public Report completeReport(){
        completeButton.click();
        return new Report(driver);
    }
}
