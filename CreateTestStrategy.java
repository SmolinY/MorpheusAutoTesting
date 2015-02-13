package MorpheusAutoTesting;

import static org.junit.Assert.*;

import MorpheusAutoTesting.pages.*;
import org.openqa.selenium.*;

import org.testng.annotations.Test;

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
        assertTrue("Appointment is not found", searchResult);

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
                .clickOnField("FoundationSpace-FoundationArea", "foundationAreaDefine");
        enclosureParam.getForm(By.id("foundationArea")).fillField(driver.findElement(By.xpath("//div[contains(@class,'FoundationSpace-FoundationArea') and span[text()='Foundation Space']]/span/input")), "1000");
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
                .clickOnField("FoundationSpace-FoundationArea", "foundationAreaDefine");
        enclosureParam.getForm(By.id("foundationArea")).fillField(driver.findElement(By.xpath("//div[contains(@class,'FoundationSpace-FoundationArea') and span[text()='Foundation space over garage']]/span/input")), "400");
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
                .clickOnField("Attic-Area", "atticAreaDefine");
        enclosureParam.getForm(By.className("listPlaceholder")).fillField("Attic-Area", "1000");
        log("Return back");
        enclosureParam.goBack();
        enclosure.goBack();
        dashboard = new ListPage(driver, "Enclosure").getLeftPanel().getDashboard();

        log("Open HVAC");
        ListPage hvacList = dashboard.getHVAC();
        String hvacName = "HVACd";
        try {
            hvacList.checkListItem(hvacName);
        }
        catch (Exception e){
            hvacName = "HVAC";
        }
        hvacList.getItem(hvacName, "hvacItem", "HVAC");
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

        log("Open recommendation");
        Recommendation recommendationPage = dashboard.getRecommendation();
        log("Filling recommendation");
        recommendationPage
                .addRecommendation("DIM-Lighting", "", "", "editBulbReplacement")
                .addRecommendation("DIM-AirSeal", "", "", "airSealDIM")
                .addRecommendation("DIM-HotWater", "Showerheads", "", "showerheads")
                .addRecommendation("DIM-HotWater", "Sink Aerators", "", "sinkAerators")
                .addRecommendation("DIM-HotWater", "Tank Turn-Down", "", "tankturndown")
                /*.addRecommendation("Appliances", "", "", "SmartPowerStrip")*/;
        recommendationPage.majorMeasure.click();
        recommendationPage
                .addRecommendation("MM-HVAC", "Add or replace an HVAC system", "Replace "+hvacName, "addHVACSystem")
                .addRecommendation("MM-HVAC", "Ducts", "Distribution system 1", "ducts")
                .addRecommendation("MM-FloorInsulation", "Floors over outside/garage", "Foundation space over garage", "floorsOverOutsideGarage")
                .addRecommendation("MM-WallInsulation", "Wall", "Wall", "GeneralWall")
                .addRecommendation("MM-WallInsulation", "Exterior Walls", "Exterior wall", "ExteriorWalls")
                .addRecommendation("MM-WallInsulation", "Foundation Walls", "Foundation wall", "FoundationWalls")
                .addRecommendation("MM-WallInsulation", "Foundation Walls", "Foundation Space", "FoundationWallsFS")
                .addRecommendation("MM-WallInsulation", "Other Walls", "Other wall", "OtherWalls")
                .addRecommendation("MM-AtticsRoofs", "Recommend insulation for the attic access", "Attic", "AtticAccess")
                .addRecommendation("MM-AtticsRoofs", "Recommend insulation for the attic access", "Other wall", "WallAccess")
                .addRecommendation("MM-AtticsRoofs", "Attic Floor - Insulate", "Attic", "AtticFloorInsulate")
                .addRecommendation("MM-AtticsRoofs", "Vault/Roof - Insulate", "Attic", "vaultRoofInsulate")
                .addRecommendation("MM-AirSeal", "", "", "airSealMM");
        log("Return back");
        dashboard = recommendationPage.getLeftPanel().getDashboard();


        dashboard.makeReport().completeReport().signReport().clickSave();
        ViewPage thanks = new ViewPage(driver, "Thanks");
        assertTrue("Appointment is not found", thanks.page.findElement(By.xpath(".//div[@class='message-thanks']")).getText().equals("Your audit is complete and has been submitted"));

    }
}
