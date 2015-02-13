package MorpheusAutoTesting.pages;


import MorpheusAutoTesting.elements.Form;
import MorpheusAutoTesting.utils.WaitForScriptsCondition;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ViewPage {
    protected WebDriver driver;
    public WebElement page;
    protected JavascriptExecutor jse;
    protected WebDriverWait wait;

    public ViewPage(WebDriver driver, String pageId){
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        this.wait = new WebDriverWait(driver, 60);
        jse = (JavascriptExecutor) driver;

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@style,'z-index: 20000')]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(pageId)));
        this.page = driver.findElement(By.id(pageId));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='"+pageId+"']//div[contains(@style,'z-index: 20000')]")));

        HtmlElementLoader.populatePageObject(this, driver);
    }

    public Form getDisplayedForm() throws Exception {
        List<WebElement> forms = page.findElements(By.xpath(".//div[contains(@class,'basic-fields')]"));
        for (WebElement form : forms){
            if (form.isDisplayed()) {
                return new Form(form, driver);
            }
        }
        throw new Exception("Form not found");
    }

    public Form getForm(By by) {
        return new Form(page.findElement(by), driver);
    }

    public void scrollDown(){
        jse.executeScript("$('#"+page.getAttribute("id")+"').data('kendoMobileView').scroller.scrollTo(0,-500 );");
    }

    public void scrollUp(){
        jse.executeScript("$('#"+page.getAttribute("id")+"').data('kendoMobileView').scroller.scrollTo(0,500 );");
    }

    protected boolean isElementPresent(By by) {
        try {
            page.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public LeftPanel getLeftPanel(){
        page.findElement(By.xpath(".//a[contains(@class,'layout-panel-left')]")).click();
        return new LeftPanel(driver);
    }

    public void goBack(){
        WebElement btn = page.findElement(By.xpath(".//a[contains(@class,'layout-panel-back')]"));
        wait.until(ExpectedConditions.visibilityOf(btn));
        btn.click();
    }

    public void clickSave(){
        page.findElement(By.xpath(".//span[span[@class='flaticon-mark']]")).click();
    }
}
