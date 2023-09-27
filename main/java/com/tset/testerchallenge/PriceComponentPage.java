package com.tset.testerchallenge;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;

public class PriceComponentPage extends BasePage {

    @FindBy ( xpath = "//span[@class='font-bold']" )
    WebElement total;

    @FindBy ( xpath = "//span[contains(text(),'Baseprice')]" )
    public WebElement basePrice;

    @FindBy ( id = "base-edit-icon" )
    public WebElement pencilIcon;

    @FindBy ( id = "base-value-input" )
    WebElement baseValueTextField;

    @FindBy ( id = "base-check-icon" )
    WebElement checkIcon;

    @FindBy ( id = "ghost-label-input" )
    public WebElement labelTextField;

    @FindBy ( id = "ghost-value-input" )
    public WebElement valueTextField;

    @FindBy ( id = "ghost-check-icon" )
    public WebElement rowCheckIcon;

    @FindBy ( xpath = "//span[contains(text(),'Internal surcharge')]" )
    public WebElement internalSurcharge;

    @FindBy ( xpath = "//span[contains(text(),'Storage surcharge')]" )
    public WebElement storageSurcharge;

    @FindBy ( xpath = "//span[contains(text(),'Scrap surcharge')]" )
    public WebElement scrapSurcharge;

    @FindBy ( xpath = "//span[contains(text(),'Alloy surcharge')]" )
    public WebElement alloySurcharge;

    @FindBy ( xpath = "(//span[contains(@id,'edit-icon')])[4]" )
    public WebElement scrapEditIcon;

    @FindBy ( xpath = "(//span[contains(@id,'edit-icon')])[6]" )
    public WebElement alloyEditIcon;

    @FindBy ( xpath = "(//span[contains(@id,'edit-icon')])[2]" )
    public WebElement storageEditIcon;

    @FindBy ( xpath = "(//input[contains(@id,'value-input')])[1]" )
    public WebElement alloyValueField;

    @FindBy ( xpath = "(//span[contains(@id,'check-icon')])[6]" )
    public WebElement alloyRowCheckIcon;

    @FindBy ( xpath = "(//span[contains(@id,'thrash-icon')])[5]" )
    WebElement trashIcon;

    @FindBy ( xpath = "(//span[contains(@id,'edit-icon')])[2]" )
    WebElement editIcon;

    @FindBy ( xpath = "//p[text()=' This label is too short! ']" )
    public WebElement labelErrorMessage;

    @FindBy ( xpath = "//p[text()=' Cannot be negative! ']" )
    public WebElement valueErrorMessage;

    @FindBy ( xpath = "(//div[contains(text(),'1.0')])[2]" )
    public WebElement externalSurchargeValue;

    @FindBy ( xpath = "//div[contains(text(),'0.77')]" )
    public WebElement internalSurchargeValue;

    private WaitHelper waitHelper;

    public PriceComponentPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        PageFactory.initElements(driver, this);
    }

    public PriceComponentPage hoverOverElement(WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        } catch (TimeoutException e) {
            System.out.println("Element not visible after waiting for 10 seconds.");
        } catch (Exception e) {
            System.out.println("An error occurred while hovering over the element: " + e.getMessage());
        }
        return this;
    }

    public PriceComponentPage hoverOverBasePrice() {
        hoverOverElement(basePrice);
        return this;
    }

    public PriceComponentPage enterNewBaseValue(String value) {
        Actions actions = new Actions(driver);
        actions.moveToElement(baseValueTextField).perform();
        actions.moveToElement(baseValueTextField).contextClick();
        baseValueTextField.clear();
        baseValueTextField.sendKeys(value);
        return new PriceComponentPage(driver);
    }

    public void editBasePrice(String value) {
        waitHelper.waitForElementAndClick(pencilIcon);
        enterNewBaseValue(value);
        clickCheckIcon();
    }

    public String getTotalValue() {
        try {
            return getTextFromTotal();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve total value.", e);
        }
    }

    public double getExternalSurchargeValueAsDouble() {
        try {
            return Double.parseDouble(getTextFromElement(externalSurchargeValue));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to convert external surcharge value to double.", e);
        }
    }

    public double getInternalSurchargeValueAsDouble() {
        try {
            return Double.parseDouble(getTextFromElement(internalSurchargeValue));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to convert internal surcharge value to double.", e);
        }
    }

    public PriceComponentPage hoverOverInternalSurcharge() {
        hoverOverElement(internalSurcharge);
        return this;
    }

    public PriceComponentPage removeInternalSurcharge() {
        hoverOverInternalSurcharge();
        clickTrashIcon();
        return this;
    }

    public String getTotalPrice() {
        return getTextFromTotal();
    }

    public PriceComponentPage hoverOverStorageSurcharge() {
        hoverOverElement(storageSurcharge);
        return this;
    }

    public PriceComponentPage editStorageSurcharge() {
        hoverOverStorageSurcharge().clickEditIcon();
        return this;
    }

    public PriceComponentPage enterStorageSurchargeLabel(String label) {
        enterNewLabel(label);
        return this;
    }

    public String getStorageSurchargeLabelErrorMessage() {
        return getTextFromElement(labelErrorMessage);
    }

    public String getStorageSurchargeLabelText() {
        return getTextFromElement(storageSurcharge);
    }

    public PriceComponentPage hoverOverScrapSurcharge() {
        hoverOverElement(scrapSurcharge);
        return this;
    }

    public PriceComponentPage editScrapSurcharge() {
        hoverOverScrapSurcharge();
        scrapEditIcon.click();
        return this;
    }

    public PriceComponentPage setScrapSurchargeValue(String value) {
        enterNewValue(value);
        return this;
    }

    public String getScrapSurchargeValue() {
        return scrapSurcharge.getAttribute("value");
    }

    public String getValueError() {
        return getTextFromElement(valueErrorMessage);
    }

    public PriceComponentPage hoverOverAlloySurcharge() {
        hoverOverElement(alloySurcharge);
        return this;
    }

    public PriceComponentPage startEditingAlloySurcharge() {
        alloyEditIcon.click();
        return this;
    }

    public PriceComponentPage setAlloySurchargeValue(String value) {
        editValue(value);
        return this;
    }

    public PriceComponentPage confirmAlloySurchargeEdit() {
        alloyRowCheckIcon.click();
        return this;
    }

    public PriceComponentPage enterNewValue(String value) {

        Actions actions = new Actions(driver);
        actions.moveToElement(valueTextField).contextClick();
        valueTextField.clear();
        valueTextField.sendKeys(value);
        return new PriceComponentPage(driver);
    }

    public PriceComponentPage editValue(String value) {

        Actions actions = new Actions(driver);
        actions.moveToElement(alloyValueField).contextClick();
        alloyValueField.clear();
        alloyValueField.sendKeys(value);
        return new PriceComponentPage(driver);
    }

    public PriceComponentPage enterNewLabel(String labelName) {
        labelTextField.click();
        labelTextField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        labelTextField.sendKeys(labelName);
        return new PriceComponentPage(driver);
    }

    public void clickCheckIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(checkIcon));
        checkIcon.click();
    }

    public void clickRowCheckIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(rowCheckIcon));
        rowCheckIcon.click();
    }

    public void clickTrashIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(trashIcon));
        trashIcon.click();
    }

    public void clickEditIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(editIcon));
        editIcon.click();
    }

    public void inputComponentLabelAndComponentValue() {
        HashMap<String, String> priceComponents = new HashMap<>();
        priceComponents.put("Alloy surcharge", "2.15");
        priceComponents.put("Scrap surcharge", "3.14");
        priceComponents.put("Internal surcharge", "0.7658");
        priceComponents.put("External surcharge", "1");
        priceComponents.put("Storage surcharge", "0.30");

        for (HashMap.Entry<String, String> set : priceComponents.entrySet()) {
            Actions actions = new Actions(driver);

            // Clearing and inputting for labelTextField
            actions.moveToElement(labelTextField).perform();
            clearTextField(labelTextField);
            labelTextField.sendKeys(set.getKey());

            // Clearing and inputting for valueTextField
            actions.moveToElement(valueTextField).perform();
            clearTextField(valueTextField);
            valueTextField.sendKeys(set.getValue());

            waitHelper.waitForElementToBePresent("ghost-check-icon");
            waitHelper.clickingElementJavaScriptExecutor(rowCheckIcon);
        }
    }

    private void clearTextField(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        element.clear();
    }

    public PriceComponentPage addPriceComponents() {
        Actions actions = new Actions(driver);
        actions.moveToElement(labelTextField).contextClick();
        clearTextField(labelTextField);
        inputComponentLabelAndComponentValue();
        return new PriceComponentPage(driver);
    }

    public String getTextFromTotal() {
        return total.getText();
    }

    public String getTextFromElement(WebElement element) {
        return element.getText();
    }
}
