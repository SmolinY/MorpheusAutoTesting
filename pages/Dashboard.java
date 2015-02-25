package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;

import java.util.concurrent.TimeUnit;

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

    @FindBy(xpath = "//td[contains(@class,'dashboard-measures')]")
    public Button measures;

    @FindBy(id = "makeReportButton")
    public Button makeReportButton;

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

    public Recommendation getRecommendation(){
        measures.click();
        return new Recommendation(driver);
    }

    public PreviewReport makeReport(){
        makeReportButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'km-shim km-modalview-root k-state-border-down')]")));
        return new PreviewReport(driver);
    }
}
