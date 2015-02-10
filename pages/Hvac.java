package MorpheusAutoTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Select;

public class Hvac extends ViewPage {
    public Hvac(WebDriver driver){
        super(driver, "hvacItem");
    }

    @FindBy(xpath = "//div[contains(@class,'HVAC-DistributionSystemId')]/div/div/select")
    public Select distributionSystem;

    @FindBy(id = "hvacSelectDistributionButton")
    public Button distributionButton;

    public ViewPage getDistributionPage() throws Exception {
        if (!distributionSystem.getFirstSelectedOption().getText().equals("Distribution system 1")) {
            getDisplayedForm().fillField("HVAC-DistributionSystemId", "--ADD-NEW--");
        }
        distributionButton.click();
        return new ViewPage(driver, "ductItem");
    }
}
