package MorpheusAutoTesting.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;

import java.util.Map;

public class Recommendation extends ViewPage{
    public Recommendation(WebDriver driver){
        super(driver, "Measures");
    }

    @FindBy(xpath = "//li[contains(@class,'MajorMeasure')]")
    public Button majorMeasure;

    public Recommendation addRecommendation(String name, String windowItem, String secondWindowItem, String pageId) throws Exception {
        WebElement element;
        try {
            element = page.findElement(By.xpath(".//div[contains(@class,'recommendation-type " + name + "') and not(contains(@class,'hidden-field'))]/span"));
        }
        catch (NoSuchElementException e) {
            return new Recommendation(driver);
        }
        if (!element.isDisplayed()) scrollDown();
        element.findElement(By.xpath("./span[contains(@class,'commonIcons-new')]")).click();

        ModalWindow modalWindow;
        if (!windowItem.isEmpty()) {
            modalWindow = new ModalWindow(driver,"wizard-list-selection");
            try {
                modalWindow.getItemByName(windowItem).click();
            }
            catch (Exception e){
                modalWindow.cancel();
                return new Recommendation(driver);
            }
        }
        if (!secondWindowItem.isEmpty()) {
            modalWindow = new ModalWindow(driver,"wizard-list-selection");
            try {
                modalWindow.getItemByName(secondWindowItem).click();
            }
            catch (Exception e){
                modalWindow.cancel();
                return new Recommendation(driver);
            }
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("wizard-list-selection")));
        new ViewPage(driver, pageId).getDisplayedForm().clickOnPartsField().selectFirstPart();
        ViewPage recommendation = new ViewPage(driver, pageId);
        recommendation.getDisplayedForm().fillRecommendationFields();
        recommendation.clickSave();

        return new Recommendation(driver);
    }
}
