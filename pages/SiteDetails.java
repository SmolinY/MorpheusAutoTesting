package MorpheusAutoTesting.pages;

import MorpheusAutoTesting.elements.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;

public class SiteDetails extends ViewPage{
    public SiteDetails(WebDriver driver){
        super(driver, "SiteDetails");
    }

    @FindBy(xpath = "//header[text()='Utility providers']/div/span[@class='commonIcons-new']")
    private Button addProviderBtn;

    @FindBy(xpath = "//li[@id='BuildingTab']")
    private Button buildingTab;

    @FindBy(xpath = "//li[@id='InterviewTab']")
    private Button interviewTab;

    @FindBy(xpath = "//div[@id='BuildingPage']/div[contains(@class,'advanced-fields')]/div/span")
    private Button advancedFieldsBtn;

    public void checkProvider(int providerNumber) throws InterruptedException {
        scrollDown();
        By providerLocator = By.xpath("//header/span[text()='Utility Provider " + providerNumber + "']");
        if (!isElementPresent(providerLocator)) {
            addProviderBtn.click();
            Thread.sleep(1000);
            scrollDown();
            wait.until(ExpectedConditions.visibilityOfElementLocated(providerLocator));
        }
    }

    public void selectBuildingTab(){
        scrollUp();
        buildingTab.click();
    }

    public Form getAdvancedFields(){
        advancedFieldsBtn.click();
        By advancedFieldsLocator = By.xpath("//div[@id='BuildingPage']/div[contains(@class,'advanced-fields')]/div/div");
        wait.until(ExpectedConditions.visibilityOfElementLocated(advancedFieldsLocator));
        return getForm(advancedFieldsLocator);
    }

    public void fillInterview() throws Exception {
        interviewTab.click();
        getDisplayedForm().fillFieldsSomeData();
    }
}
