package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

public class SiteDetails extends ViewPage{
    public SiteDetails(WebDriver driver){
        super(driver, "SiteDetails");
    }

    @FindBy(xpath = "//header[text()='Utility providers']/div/span[@class='commonIcons-new']")
    private Button addProviderBtn;

    @FindBy(xpath = "//li[@id='BuildingTab']")
    private Button buildingTab;

    public void checkProvider(int providerNumber){
        scrollDown();
        if (!isElementPresent(By.xpath("//header/span[text()='Utility Provider " + providerNumber + "']"))) {
            addProviderBtn.click();
            scrollDown();
        }
    }

    public void selectBuildingTab(){
        scrollUp();
        buildingTab.click();
    }
}
