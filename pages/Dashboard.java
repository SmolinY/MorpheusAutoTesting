package MorpheusAutoTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;

public class Dashboard extends ViewPage{
    public Dashboard(WebDriver driver){
        super(driver, "dashboard-view");
    }

    @FindBy(xpath = "//td[contains(@class,'dashboard-sitedetails')]")
    public Button siteDetails;

    @FindBy(xpath = "//td[contains(@class,'dashboard-enclosure')]")
    public Button enclosure;

    @FindBy(xpath = "//td[contains(@class,'dashboard-hvac')]")
    public Button hvac;

    @FindBy(xpath = "//td[contains(@class,'dashboard-dhw')]")
    public Button dhw;

    public SiteDetails getSiteDetails(){
        siteDetails.click();
        return new SiteDetails(driver);
    }

    public ListPage getHVAC(){
        hvac.click();
        return new ListPage(driver, "HVAC");
    }

    public ListPage getWaterHeater(){
        dhw.click();
        return new ListPage(driver, "DHW");
    }

    public ListPage getEnclosure(){
        enclosure.click();
        return new ListPage(driver, "Enclosure");
    }
}
