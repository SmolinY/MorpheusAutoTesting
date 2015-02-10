package MorpheusAutoTesting;

import static org.junit.Assert.*;

import MorpheusAutoTesting.pages.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTestStrategy extends TestBase {

    @Test()
    public void test() throws Exception {
        log("Opening " + baseUrl + "/webclient/");
        driver.get(baseUrl + "/webclient/");
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='mainframe']")));

        log("Authorization");
        boolean loginResult = new Login(driver).authorization("morpheus2", "12345");
        assertTrue("Login failed", loginResult);

        String appointmentType = "AS_AUDIT";
        log("Search and get scheduled appointment "+appointmentType);
        boolean searchResult = new Schedule(driver).getScheduledAppointment(appointmentType);
        assertTrue("Appointment in not found", searchResult);

        log("Get site details page");
        Dashboard dashboard = new Dashboard(driver);
        SiteDetails siteDetailsPage = dashboard.getSiteDetails();
        log("Filling customer tab");
        siteDetailsPage.getDisplayedForm()
                .fillField("CustomerInformation-Name", "VERNON E HOFFAMANN")
                .fillField("CustomerInformation-Street", "720 Forest Ave")
                .fillField("CustomerInformation-City", "BELLEVILLE")
                .fillField("CustomerInformation-State", "IL")
                .fillField("CustomerInformation-Zip", "62220");
        String[] providers = {"AmerenElectric","AmerenGas"};
        for (int i = 0; i < providers.length; i++) {
            int providerNumber = i+1;
            siteDetailsPage.checkProvider(providerNumber);
            siteDetailsPage.getForm(By.xpath("//div[header/span[text()='Utility Provider " + providerNumber + "']]/div"))
                    .fillField("CustomerInformation-UtilityProvider-Name",providers[i])
                    .fillField("CustomerInformation-UtilityProvider-Account", "4045448975");
        }

        log("Open building tab");
        siteDetailsPage.selectBuildingTab();

        log("Filling building tab");
        siteDetailsPage.getDisplayedForm()
                .fillField("BuildingInformation-DwellingType", "SingleFamilyDetached")
                .fillField("BuildingInformation-BuildingType", "TwotoFourFamily")
                .fillField("BuildingInformation-YearBuilt", "2012")
                .fillField("BuildingInformation-Bedrooms", "4")
                .fillField("BuildingInformation-Occupants", "5")
                .fillField("BuildingInformation-FloorsAboveGrade", "2")
                .fillField("BuildingInformation-AboveGradeConditionedFloorArea", "2500");
        log("Return back");
        dashboard = siteDetailsPage.getLeftPanel().getDashboard();

        log("Open Air Tightness");
        ViewPage enclosure = dashboard.getEnclosure().getItem("Air Tightness", "AirTightness", "");
        log("Filling Air Tightness");
        enclosure.getDisplayedForm().fillField("AirTightness-HouseLeakiness","Average");
        log("Return back");
        enclosure.goBack();

        log("Open Wall");
        enclosure = new ListPage(driver, "Enclosure").getItem("Wall", "PrimaryWall", "");
        log("Filling Wall form");
        enclosure.getDisplayedForm().fillField("PrimaryWall-WallConstructionType","WoodFrame")
                .fillField("PrimaryWall-WallInsulationType","FiberglassBatt")
                .fillField("PrimaryWall-WallInsulationThickness","7");
        log("Return back");
        enclosure.goBack();

        log("Open Exterior wall");
        enclosure = new ListPage(driver, "Enclosure").getItem("Exterior wall", "ExteriorWall", "Wall");
        log("Filling Wall form");
        enclosure.getDisplayedForm().fillField("ExteriorWall-Name","Exterior wall")
                .fillField("ExteriorWall-WallOtherType", "AboveGradeExteriorWall")
                .fillField("ExteriorWall-WallArea","10");
        log("Return back");
        enclosure.goBack();

        log("Open Foundation wall");
        enclosure = new ListPage(driver, "Enclosure").getItem("Foundation wall", "ExteriorWall", "Wall");
        log("Filling Wall form");
        enclosure.getDisplayedForm().fillField("ExteriorWall-Name","Foundation wall")
                .fillField("ExteriorWall-WallOtherType", "BelowGradeExteriorWall")
                .fillField("ExteriorWall-WallArea", "10");
        log("Return back");
        enclosure.goBack();

        log("Create other wall");
        enclosure = new ListPage(driver, "Enclosure").getItem("Other wall", "ExteriorWall", "Wall");
        log("Filling Wall form");
        enclosure.getDisplayedForm().fillField("ExteriorWall-Name","Other wall")
                .fillField("ExteriorWall-WallOtherType", "AtticGableWall")
                .fillField("ExteriorWall-WallArea", "10");
        log("Return back");
        enclosure.goBack();

        log("Open Window section");
        enclosure = new ListPage(driver, "Enclosure").getItem("Window", "PrimaryWindow", "");
        log("Filling Window form");
        enclosure.getDisplayedForm().fillField("PrimaryWindow-WindowGlazingType","DoublePane")
                .fillField("PrimaryWindow-WindowFrameType", "Vinyl")
                .fillField("PrimaryWindow-StormWindows", "No");
        log("Return back");
        enclosure.goBack();

        log("Open Foundation Space");
        enclosure = new ListPage(driver, "Enclosure").getItem("Foundation Space", "FoundationSpace", "Foundation Space");
        log("Filling Foundation Space form");
        ViewPage enclosureParam = enclosure.getDisplayedForm().fillField("FoundationSpace-Name", "Foundation Space")
                .fillField("FoundationSpace-FoundationType", "ConditionedBasement")
                .fillField("FoundationSpace-WallInsulationType", "None")
                .fillField("FoundationSpace-BasementFloorType", "Slab")
                .clickOnField("FoundationSpace-FoundationArea", "foundationAreaDefine", driver);
        enclosureParam.getForm(By.id("foundationArea")).fillField("FoundationSpace-FoundationArea", "1000");
        log("Return back");
        enclosureParam.goBack();
        enclosure.goBack();

        log("Create Foundation space over garage");
        enclosure = new ListPage(driver, "Enclosure").getItem("Foundation space over garage", "FoundationSpace", "Foundation Space");
        log("Filling Foundation Space form");
        enclosureParam = enclosure.getDisplayedForm().fillField("FoundationSpace-Name", "Foundation space over garage")
                .fillField("FoundationSpace-FoundationType", "FloorOverGarage")
                .fillField("FoundationSpace-BasementCeilingInsulationType", "FiberglassBatt")
                .fillField("FoundationSpace-BasementCeilingInsulationDepth", "7")
                .clickOnField("FoundationSpace-FoundationArea", "foundationAreaDefine", driver);
        enclosureParam.getForm(By.id("foundationArea")).fillField("FoundationSpace-FoundationArea", "400");
        log("Return back");
        enclosureParam.goBack();
        enclosure.goBack();

        log("Open attic");
        enclosure = new ListPage(driver, "Enclosure").getItem("Attic", "Attic", "Attic");
        log("Filling attic form");
        enclosureParam = enclosure.getDisplayedForm().fillField("Attic-PrimaryAtticType","Attic")
                .fillField("Attic-InsulationType", "FiberglassBatt")
                .fillField("Attic-InsulationDepth", "0")
                .fillField("Attic-InsulationQuality", "Fair")
                .clickOnField("Attic-Area", "atticAreaDefine", driver);
        enclosureParam.getForm(By.className("listPlaceholder")).fillField("Attic-Area", "1000");
        log("Return back");
        enclosureParam.goBack();
        enclosure.goBack();
        dashboard = new ListPage(driver, "Enclosure").getLeftPanel().getDashboard();

        log("Open HVAC");
        dashboard.getHVAC().getItem("HVACd", "HVAC", "HVAC");
        log("Filling HVAC form");
        Hvac hvac = new Hvac(driver);
        hvac.getDisplayedForm().fillField("HVAC-Name", "HVACd")
                .fillField("HVAC-HVACSystemType", "HeatingAndCooling")
                .fillField("HVAC-HeatingSystemType","Gshp")
                .fillField("HVAC-HeatingYear","2012");
        ViewPage distributionPage = hvac.getDistributionPage();
        distributionPage.getDisplayedForm().fillField("DistributionSystem-HeatDistributionType", "Duct");
        log("Return back");
        distributionPage.goBack();
        dashboard = new ListPage(driver, "HVAC").getLeftPanel().getDashboard();

        log("Open Water Heater");
        ViewPage waterHeater = dashboard.getWaterHeater().getItem("Water Heater", "dhwHeater");
        log("Filling Water Heater form");
        waterHeater.getDisplayedForm().fillField("WaterHeater-Name","Water Heater")
                .fillField("WaterHeater-FuelType", "Electricity");
        log("Return back");
        waterHeater.goBack();
        dashboard = new ListPage(driver, "DHW").getLeftPanel().getDashboard();

        /*log("Open recommendation");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        waitAndClick("//td[contains(@class,'dashboard-measures')]");
        WebElement modalWindow = driver.findElement(By.xpath("//div[@class='wizard-list-selection-placeholder-first-page']"));
        WebElement extendedModalWindow = driver.findElement(By.xpath("//div[@class='extended-list-selection-placeholder']"));
        List<String> tabsId = Arrays.asList("DirectInstall","MajorMeasure");
        for (String tabId : tabsId){
            log("Open "+tabId);
            driver.findElement(By.xpath("//li[contains(@class,'"+tabId+"')]")).click();
            for (WebElement elem : driver.findElements(By.xpath("//div[@id='"+tabId+"']/div/div[contains(@class,'recommendation-type')]")) ) {
                WebElement addBtn = driver.findElement(By.xpath("//div[@class='"+elem.getAttribute("class")+"']/span/span[contains(@class,'commonIcons-new')]"));
                if (!addBtn.isDisplayed()) continue;
                wait.until(ExpectedConditions.visibilityOf(addBtn));
                addBtn.click();
                Thread.sleep(1000);
                if (modalWindow.isDisplayed()) {
                    modalWindowHandling(modalWindow, addBtn);
                }
                else if(extendedModalWindow.isDisplayed()){
                    modalWindowHandling(extendedModalWindow, addBtn);
                }
                else {
                    fillRecommendation();
                }
                //Thread.sleep(2000);
                //driver.findElement(By.xpath("//div[@class='" + elem.getAttribute("class") + "']/span")).click();
                break;
            }
        }
        clickLeftItem("Measures");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='leftPanel']")));
        waitAndClick("//div[@id='leftPanel']/header/a[text()='Dashboard']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header/div/div[@class='km-view-title']/span[text()='Dashboard']")));
        waitAndClick("//button[@id='makeReportButton']");
        wait.withTimeout(120, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='Savings']")));
        waitAndClick("//div[@id='Savings']/footer/button[@id='completeButton']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='Report']")));
        waitAndClick("//div[@id='Report']/footer/button[@id='completeButton']");
        driver.findElement(By.xpath("//div[@id='drawScreen']/footer/div/span[span[@class='flaticon-mark']]")).click();*/

    }

    private void modalWindowHandling(WebElement modalWindow, WebElement addBtn) throws InterruptedException {
        WebElement secondModalWindow = driver.findElement(By.xpath("//div[@class='wizard-list-selection-placeholder-second-page']"));
        String modalWindowClass = modalWindow.getAttribute("class");
        List<String> windowItemsName = getModalWindowItemsNames(modalWindow);
        for (String windowItemName : windowItemsName) {
            log("Open "+windowItemName);
            By windowItemLocation = By.xpath("//div[@class='" + modalWindowClass + "']/div/span[text()='" + windowItemName + "' and not(@class='grayed')]");
            if (!isElementPresent(windowItemLocation)) {
                log("Can't open "+windowItemName);
                continue;
            }
            driver.findElement(windowItemLocation).click();
            Thread.sleep(1000);
            if (secondModalWindow.isDisplayed()) {
                String secondModalWindowClass = secondModalWindow.getAttribute("class");
                List<String> secondWindowItemsName = getModalWindowItemsNames(secondModalWindow);
                for (String secondWindowItemName : secondWindowItemsName) {
                    if (secondWindowItemName.equalsIgnoreCase("Add a new HVAC system")) continue;
                    log("Open "+secondWindowItemName);
                    By secondWindowItemLocation = By.xpath("//div[@class='" + secondModalWindowClass + "']/div/span[text()='" + secondWindowItemName + "' and not(@class='grayed')]");
                    if (!isElementPresent(secondWindowItemLocation)) {
                        log("Can't open "+secondWindowItemName);
                        continue;
                    }
                    driver.findElement(secondWindowItemLocation).click();
                    fillRecommendation();
                    wait.until(ExpectedConditions.visibilityOf(addBtn));
                    addBtn.click();
                    if (!isElementPresent(windowItemLocation)){
                        cancelModalWindow(modalWindow);
                        break;
                    }
                    driver.findElement(windowItemLocation).click();
                    break;
                }
                cancelModalWindow(secondModalWindow);
            }
            else {
                fillRecommendation();
            }
            /*wait.until(ExpectedConditions.visibilityOf(addBtn));
            addBtn.click();*/
            break;
        }
        //cancelModalWindow(modalWindow);
    }

    private void cancelModalWindow(WebElement modalWindow){
        if (modalWindow.isDisplayed()) {
            if (modalWindow.getAttribute("class").equalsIgnoreCase("extended-list-selection-placeholder")) {
                driver.findElement(By.xpath("//div[@id='extended-list-selection']/div/a[span[text()='Cancel']]")).click();
            } else {
                driver.findElement(By.xpath("//div[@id='wizard-list-selection']/div/a[span[text()='Cancel']]")).click();
            }
        }
    }

    private List<String> getModalWindowItemsNames(WebElement modalWindow){
        String xpath = "//div[@class='"+modalWindow.getAttribute("class")+"']/div/span[not(@class='grayed')]";
        List<WebElement> windowItems = driver.findElements(By.xpath(xpath));
        List<String> windowItemsName = new ArrayList<String>();
        for (WebElement windowItem : windowItems) {
            windowItemsName.add(windowItem.getText());
        }
        return windowItemsName;
    }

    private void fillRecommendation() throws InterruptedException {
        Thread.sleep(2000);
        String viewId = getCurrentViewId();
        log("Filling " + viewId);
        driver.findElement(By.xpath("//div[@id='" + viewId + "']/div/div/div/div/div[contains(@class,'Part')]/div/input")).click();
        if (isElementPresent(By.xpath("//div[contains(@class,'part-item')]"))) {
            driver.findElement(By.xpath("//div[contains(@class,'part-item')]")).click();
        }
        else {
            clickLeftItem("Parts");
            driver.findElement(By.xpath("//div[@id='"+viewId+"']/footer/div/span[span[@class='flaticon-delete']]")).click();
            return;
        }
        if (isElementPresent(By.xpath("//div[@id='"+viewId+"']/div/div/div/div/div[contains(@class,'Quantity')]/div/input"))){
            fillInput("//div[@id='"+viewId+"']/div/div/div/div/div[contains(@class,'Quantity')]/div/input", "1");
        }
        String requiredFields = "//div[@id='"+viewId+"']/div/div/div/div/div[contains(@class,'required-field') and not(contains(@class,'Part'))] |" +
                "//div[@id='"+viewId+"']/div/div/div/ul/li/div[contains(@class,'required-field')] |"+
                "//div[@id='"+viewId+"']/div/div/div/div/div[contains(@class,'MM-HVAC-AddHVACSystem-DistributionSystemId')]";
        for (WebElement field : driver.findElements(By.xpath(requiredFields)) ) {
            String fieldXpath = "//div[@class='"+field.getAttribute("class")+"']/div";
            WebElement fieldElement = driver.findElement(By.xpath(fieldXpath+"/input|"+fieldXpath+"/select"));
            if (fieldElement.getTagName().equalsIgnoreCase("input")) {
                fieldElement.click();
                if (fieldElement.getAttribute("value").isEmpty()) {
                    By rangeElementLocation = By.xpath("//div[@class='" + field.getAttribute("class") + "']/div[contains(@class,'m-content-hint-block')]");
                    if (isElementPresent(rangeElementLocation)) {
                        Pattern p = Pattern.compile("Range is ([0-9]+) to ([0-9]+)");
                        Matcher m = p.matcher(driver.findElement(rangeElementLocation).getText());
                        if (m.matches()) {
                            fieldElement.sendKeys(m.group(1));
                        } else {
                            fieldElement.sendKeys("1");
                        }
                    } else {
                        fieldElement.sendKeys("1");
                    }
                }
            }
            else {
                Select selectElement = new Select(fieldElement);
                if (selectElement.getFirstSelectedOption().getAttribute("value").isEmpty()) {
                    for (WebElement option : selectElement.getOptions()) {
                        String value = option.getAttribute("value");
                        if (!value.isEmpty()) {
                            selectElement.selectByValue(value);
                            break;
                        }
                    }
                }
            }
        }
        driver.findElement(By.xpath("//div[@id='"+viewId+"']/footer/div/span[span[@class='flaticon-mark']] |" +
                "//div[@id='"+viewId+"']/div/div/span[span[@class='flaticon-mark']]")).click();
    }

    private String getCurrentViewId() {
        for (WebElement view : driver.findElements(By.xpath("//div[@data-role='view']"))){
            if (view.isDisplayed()) {
                return view.getAttribute("id");
            }
        }
        return "error";
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void waitAndClick(String xpath) throws WebDriverException{
        WebElement element = driver.findElement(By.xpath(xpath));
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    private void clickLeftItem(String pageId) throws InterruptedException {
        try {
            waitAndClick("//div[@id='" + pageId + "']/header/div/div[@class='km-leftitem']/a");
        }
        catch (WebDriverException e) {
            jse.executeScript("$('#" + pageId + "').children('header').children('div').children('div.km-leftitem').children('a').click();");
        }
    }

    private void checkEnclosure(String type, String name){
        try {
            driver.findElement(By.xpath("//li[@class='enclosureItem-item' and div/div/span[text()='"+name+"']]")).click();
        }
        catch (WebDriverException e){
            waitAndClick("//div[@id='Enclosure']/header/div/div[@class='km-rightitem']/a");
            waitAndClick("//div[@class='extended-list-selection-item']/span[text()='"+type+"']");
        }
    }

    private void fillInput(String xpath, String text){
        WebElement element = driver.findElement(By.xpath(xpath));
        element.clear();
        element.sendKeys(text);
    }

    private void fillSelect(String xpath, String value){
        Select element = new Select(driver.findElement(By.xpath(xpath)));
        element.selectByValue(value);
    }
}
