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

    @FindBy(xpath = "//li[contains(@class,'HvacSystem au-tab')]")
    public Button basicTab;

    @FindBy(xpath = "//li[contains(@class,'HeatingSystem au-tab')]")
    public Button heatingSystemTab;

    @FindBy(xpath = "//li[contains(@class,'CoolingSystem au-tab')]")
    public Button coolingSystemTab;

    public ViewPage getDistributionPage() throws Exception {
        if (!distributionSystem.getFirstSelectedOption().getText().equals("Distribution system 1")) {
            getDisplayedForm().fillField("HVAC-DistributionSystemId", "--ADD-NEW--");
        }
        distributionButton.click();
        return new ViewPage(driver, "ductItem");
    }

    public Hvac fillHeatingSystem() throws Exception {
        heatingSystemTab.click();
        getDisplayedForm().fillFieldsSomeData();
        return this;
    }

    public Hvac fillCoolingSystem() throws Exception {
        coolingSystemTab.click();
        getDisplayedForm().fillFieldsSomeData();
        return this;
    }
}
