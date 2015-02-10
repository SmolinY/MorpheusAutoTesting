package MorpheusAutoTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

public class LeftPanel extends ViewPage {
    public LeftPanel(WebDriver driver){
        super(driver, "leftPanel");
    }

    @FindBy(xpath = "//div[@id='leftPanel']/header/a[text()='Dashboard']")
    public Button dashboard;

    public Dashboard getDashboard(){
        dashboard.click();
        return new Dashboard(driver);
    }
}
