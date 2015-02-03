package MorpheusAutoTesting;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateTestStrategy extends TestBase {
    private boolean acceptNextAlert = true;

    @Test()
    public void test() throws Exception {
        log("Opening " + baseUrl + "/webclient/");
        driver.get(baseUrl + "/webclient/");

        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='mainframe']")));

        log("Type login and password");
        fillInput("//input[../../../tr/td[text()='Login'] and @type='text' and @placeholder='Username']", "morpheus1");
        fillInput("//input[../../../tr/td[text()='Password'] and @type='password' and @placeholder='Password']", "123");

        log("Authorization");
        driver.findElement(By.xpath("//div[span[text()='Log In']]")).click();
        assertTrue("Failed login", isElementPresent(By.xpath("//header/div/div[@class='km-view-title']/span[text()='Appointments']")));

        String appointmentType = "AS_AUDIT";
        log("Search scheduled appointment "+appointmentType);
        By scheduleItem = By.xpath("//div[@class='m-field schedule-item' and @data-appointmenttype='"+appointmentType+"' and div/div[@class='appt-button__scheduled']]");
        assertTrue("Appointment in not found", isElementPresent(scheduleItem));

        log("Get dashboard page");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'km-loader')]")));
        driver.findElement(scheduleItem).click();
        wait.withTimeout(40, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header/div/div[@class='km-view-title']/span[text()='Dashboard']")));
        assertTrue("Dashboard is not displayed", isElementPresent(By.xpath("//header/div/div[@class='km-view-title']/span[text()='Dashboard']")));

        log("Get site details page");
        driver.findElement(By.xpath("//td[contains(@class,'dashboard-sitedetails')]")).click();

        log("Filling customer tab");
        assertTrue("Customer tab is not displayed", isElementPresent(By.xpath("//li[@id='CustomerTab' and contains(@class,'km-state-active')]")));
        fillInput("//div[contains(@class,'CustomerInformation-Name')]/div/input", "VERNON E HOFFAMANN");
        fillInput("//div[contains(@class,'CustomerInformation-Street')]/div/input", "720 Forest Ave");
        fillInput("//div[contains(@class,'CustomerInformation-City')]/div/input", "BELLEVILLE");
        fillInput("//div[contains(@class,'CustomerInformation-State')]/div/input", "IL");
        fillInput("//div[contains(@class,'CustomerInformation-Zip')]/div/input", "62220");
        jse.executeScript("$('#SiteDetails').data('kendoMobileView').scroller.scrollTo(0,-500 );");
        String[] providers = {"AmerenElectric","AmerenGas"};
        for (int i = 0; i < providers.length; i++) {
            int providerNumber = i+1;
            if (!isElementPresent(By.xpath("//header/span[text()='Utility Provider "+providerNumber+"']"))) {
                driver.findElement(By.xpath("//header[text()='Utility providers']/div/span[@class='commonIcons-new']")).click();
                jse.executeScript("$('#SiteDetails').data('kendoMobileView').scroller.scrollTo(0,-500 );");
            }
            fillSelect("//div[header/span[text()='Utility Provider "+providerNumber+"']]/div/div[contains(@class,'CustomerInformation-UtilityProvider-Name')]/div/select", providers[i]);
            fillInput("//div[header/span[text()='Utility Provider "+providerNumber+"']]/div/div[contains(@class,'CustomerInformation-UtilityProvider-Account')]/div/input", "4045448975");
        }

        log("Open building tab");
        jse.executeScript("$('#SiteDetails').data('kendoMobileView').scroller.scrollTo(0,500 );");
        driver.findElement(By.xpath("//li[@id='BuildingTab']")).click();
        assertTrue("Building tab is not displayed", isElementPresent(By.xpath("//li[@id='BuildingTab' and contains(@class,'km-state-active')]")));

        log("Filling building tab");
        fillSelect("//div[contains(@class,'BuildingInformation-DwellingType')]/div/select", "SingleFamilyDetached");
        fillSelect("//div[contains(@class,'BuildingInformation-BuildingType')]/div/select", "TwotoFourFamily");
        fillInput("//div[contains(@class,'BuildingInformation-YearBuilt')]/div/input", "2012");
        fillInput("//div[contains(@class,'BuildingInformation-Bedrooms')]/div/input", "4");
        fillInput("//div[contains(@class,'BuildingInformation-Occupants')]/div/input", "5");
        fillSelect("//div[contains(@class,'BuildingInformation-FloorsAboveGrade')]/div/select", "2");
        fillInput("//div[contains(@class,'BuildingInformation-AboveGradeConditionedFloorArea')]/div/input", "2500");

        log("Open Air Tightness");
        clickLeftItem("SiteDetails");
        waitAndClick("//div[@class='drawerItem-child' and div/div/span[text()='Air Tightness']]");
        log("Filling Air Tightness");
        fillSelect("//div[contains(@class,'AirTightness-HouseLeakiness')]/div/select", "Average");
        clickLeftItem("AirTightness");

        log("Open Wall section");
        driver.findElement(By.xpath("//li[@class='enclosureItem-item' and div/div/span[text()='Wall']]")).click();
        log("Filling Wall form");
        fillSelect("//div[contains(@class,'PrimaryWall-WallConstructionType')]/div/select", "WoodFrame");
        fillSelect("//div[contains(@class,'PrimaryWall-WallInsulationType')]/div/select", "FiberglassBatt");
        fillInput("//div[contains(@class,'PrimaryWall-WallInsulationThickness')]/div/input", "7");
        log("Return back");
        clickLeftItem("PrimaryWall");

        log("Create wall 1");
        checkEnclosure("Wall", "Exterior wall");
        log("Filling Wall form");
        fillInput("//div[contains(@class,'ExteriorWall-Name')]/div/input", "Exterior wall");
        fillSelect("//div[contains(@class,'ExteriorWall-WallOtherType')]/div/select", "AboveGradeExteriorWall");
        fillInput("//div[contains(@class,'ExteriorWall-WallArea')]/div/input", "10");
        log("Return back");
        clickLeftItem("ExteriorWall");

        log("Create wall 2");
        checkEnclosure("Wall", "Foundation wall");
        log("Filling Wall form");
        fillInput("//div[contains(@class,'ExteriorWall-Name')]/div/input", "Foundation wall");
        fillSelect("//div[contains(@class,'ExteriorWall-WallOtherType')]/div/select", "BelowGradeExteriorWall");
        fillInput("//div[contains(@class,'ExteriorWall-WallArea')]/div/input", "10");
        log("Return back");
        clickLeftItem("ExteriorWall");

        log("Create wall 3");
        checkEnclosure("Wall", "Other wall");
        log("Filling Wall form");
        fillInput("//div[contains(@class,'ExteriorWall-Name')]/div/input", "Other wall");
        fillSelect("//div[contains(@class,'ExteriorWall-WallOtherType')]/div/select", "AtticGableWall");
        fillInput("//div[contains(@class,'ExteriorWall-WallArea')]/div/input", "10");
        log("Return back");
        clickLeftItem("ExteriorWall");

        log("Open Window section");
        driver.findElement(By.xpath("//li[@class='enclosureItem-item' and div/div/span[text()='Window']]")).click();
        log("Filling Window form");
        fillSelect("//div[contains(@class,'PrimaryWindow-WindowGlazingType')]/div/select", "DoublePane");
        fillSelect("//div[contains(@class,'PrimaryWindow-WindowFrameType')]/div/select", "Vinyl");
        fillSelect("//div[contains(@class,'PrimaryWindow-StormWindows')]/div/select", "No");
        log("Return back");
        clickLeftItem("PrimaryWindow");

        log("Open Foundation Space section");
        driver.findElement(By.xpath("//li[@class='enclosureItem-item' and div/div/span[text()='Foundation Space' or text()='Conditional foundation space']]")).click();
        log("Filling Foundation Space form");
        fillInput("//div[contains(@class,'FoundationSpace-Name')]/div/input", "Conditional foundation space");
        fillSelect("//div[contains(@class,'FoundationSpace-FoundationType')]/div/select", "ConditionedBasement");
        fillSelect("//div[contains(@class,'FoundationSpace-WallInsulationType')]/div/select", "None");
        fillSelect("//div[contains(@class,'FoundationSpace-BasementFloorType')]/div/select", "Slab");
        driver.findElement(By.xpath("//div[contains(@class,'FoundationSpace-FoundationArea')]/div/span")).click();
        fillInput("//div[contains(@class,'FoundationSpace-FoundationArea') and span[text()='Conditional foundation space']]/span/input", "1000");
        log("Return back");
        clickLeftItem("foundationAreaDefine");
        clickLeftItem("FoundationSpace");

        log("Create Foundation Space");
        checkEnclosure("Foundation Space", "Foundation space over garage");
        log("Filling Foundation Space form");
        fillInput("//div[contains(@class,'FoundationSpace-Name')]/div/input", "Foundation space over garage");
        fillSelect("//div[contains(@class,'FoundationSpace-FoundationType')]/div/select", "FloorOverGarage");
        fillSelect("//div[contains(@class,'FoundationSpace-BasementCeilingInsulationType')]/div/select", "FiberglassBatt");
        fillInput("//div[contains(@class,'FoundationSpace-BasementCeilingInsulationDepth')]/div/input", "7");
        waitAndClick("//div[contains(@class,'FoundationSpace-FoundationArea')]/div/span");
        fillInput("//div[contains(@class,'FoundationSpace-FoundationArea') and span[text()='Foundation space over garage']]/span/input", "400");
        log("Return to dashboard");
        clickLeftItem("foundationAreaDefine");
        clickLeftItem("FoundationSpace");

        log("Open attic");
        driver.findElement(By.xpath("//li[@class='enclosureItem-item' and div/div/span[text()='Attic']]")).click();
        log("Filling attic form");
        fillSelect("//div[contains(@class,'Attic-PrimaryAtticType')]/div/select", "Attic");
        fillSelect("//div[contains(@class,'Attic-InsulationType')]/div/select", "FiberglassBatt");
        fillInput("//div[contains(@class,'Attic-InsulationDepth')]/div/input", "0");
        fillSelect("//div[contains(@class,'Attic-InsulationQuality')]/div/select", "Fair");
        driver.findElement(By.xpath("//div[contains(@class,'Attic-Area')]/div/span")).click();
        fillInput("//div[contains(@class,'Attic-Area')]/span/input", "1000");
        log("Return back");
        clickLeftItem("atticAreaDefine");
        clickLeftItem("Attic");

        log("Open Dashboard");
        clickLeftItem("Enclosure");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='leftPanel']")));
        waitAndClick("//div[@id='leftPanel']/header/a[text()='Dashboard']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header/div/div[@class='km-view-title']/span[text()='Dashboard']")));

        log("Open HVAC");
        waitAndClick("//td[contains(@class,'dashboard-hvac')]");
        log("Add HVAC");
        try {
            driver.findElement(By.xpath("//li[@class='hvacItem-item' and div/div/span[text()='HVACd']]")).click();
        }
        catch (WebDriverException e){
            waitAndClick("//div[@id='HVAC']/header/div/div[@class='km-rightitem']/a");
            waitAndClick("//div[@class='extended-list-selection-item']/span[text()='HVAC']");
        }
        log("Filling HVAC form");
        fillInput("//div[contains(@class,'HVAC-Name')]/div/input", "HVACd");
        fillSelect("//div[contains(@class,'HVAC-HVACSystemType')]/div/select", "HeatingAndCooling");
        fillSelect("//div[contains(@class,'HVAC-HeatingSystemType')]/div/select", "Gshp");
        fillInput("//div[contains(@class,'HVAC-HeatingYear')]/div/input", "2012");
        Select DistributionSystem = new Select(driver.findElement(By.xpath("//div[contains(@class,'HVAC-DistributionSystemId')]/div/div/select")));
        if (!DistributionSystem.getFirstSelectedOption().getText().equals("Distribution system 1")) {
            fillSelect("//div[contains(@class,'HVAC-DistributionSystemId')]/div/div/select", "--ADD-NEW--");
        }
        waitAndClick("//a[@id='hvacSelectDistributionButton']");
        fillSelect("//div[contains(@class,'DistributionSystem-HeatDistributionType')]/div/select", "Duct");
        log("Return to dashboard");
        clickLeftItem("ductItem");
        clickLeftItem("HVAC");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='leftPanel']")));
        waitAndClick("//div[@id='leftPanel']/header/a[text()='Dashboard']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header/div/div[@class='km-view-title']/span[text()='Dashboard']")));

        log("Open Water Heater");
        waitAndClick("//td[div/span[text()='Water Heater']]");
        log("Add HVAC");
        try {
            driver.findElement(By.xpath("//li[@class='dhwItem-item' and div/div/span[text()='Water Heater']]")).click();
        }
        catch (WebDriverException e){
            waitAndClick("//div[@id='DHW']/header/div/div[@class='km-rightitem']/a");
        }
        log("Filling Water Heater form");
        fillInput("//div[contains(@class,'WaterHeater-Name')]/div/input", "Water Heater");
        fillSelect("//div[contains(@class,'WaterHeater-FuelType')]/div/select", "Electricity");
        log("Return to dashboard");
        clickLeftItem("dhwHeater");
        clickLeftItem("DHW");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='leftPanel']")));
        waitAndClick("//div[@id='leftPanel']/header/a[text()='Dashboard']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header/div/div[@class='km-view-title']/span[text()='Dashboard']")));
        log("Open recommendation");
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
        driver.findElement(By.xpath("//div[@id='drawScreen']/footer/div/span[span[@class='flaticon-mark']]")).click();

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
