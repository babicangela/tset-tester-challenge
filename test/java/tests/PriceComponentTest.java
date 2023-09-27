package tests;

import com.tset.testerchallenge.PriceComponentPage;
import com.tset.testerchallenge.StringsConstans;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PriceComponentTest extends BaseTest {

    @Test(description = "Verify updated total after editing base price.", priority = 1)
    public void changeBasePriceValueTest() {
        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        priceComponentPage
                .hoverOverBasePrice()
                .editBasePrice(StringsConstans.BASE_PRICE_EDIT_VALUE);

        String actualTotal = priceComponentPage.getTotalValue();
        Assert.assertEquals(StringsConstans.EXPECTED_BASE_PRICE, actualTotal,
                String.format("The result should be '%s' but it's %s", StringsConstans.EXPECTED_BASE_PRICE, actualTotal));
    }

    @Test(description = "Verify the accurate rounding of external and internal surcharge values.", priority = 2)
    public void addAllPriceComponentsTest() {
        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        priceComponentPage.addPriceComponents();

        double actualExternalSurcharge = priceComponentPage.getExternalSurchargeValueAsDouble();
        Assert.assertEquals(StringsConstans.EXPECTED_EXTERNAL_SURCHARGE, actualExternalSurcharge,
                String.format("The external surcharge should be '%f' but it's %f", StringsConstans.EXPECTED_EXTERNAL_SURCHARGE, actualExternalSurcharge));

        double actualInternalSurcharge = priceComponentPage.getInternalSurchargeValueAsDouble();
        Assert.assertEquals(StringsConstans.EXPECTED_INTERNAL_SURCHARGE, actualInternalSurcharge,
                String.format("The internal surcharge should be '%f' but it's %f", StringsConstans.EXPECTED_INTERNAL_SURCHARGE, actualInternalSurcharge));
    }

    @Test(description = "Verify that removal of the 'Internal surcharge' component updates the total sum correctly.", priority = 3)
    public void removePriceComponentOfInternalSurchargeTest() {
        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        priceComponentPage
                .addPriceComponents()
                .removeInternalSurcharge();

        String actualTotal = priceComponentPage.getTotalPrice();
        Assert.assertEquals(StringsConstans.EXPECTED_TOTAL_AFTER_REMOVING_INTERNAL_SURCHARGE, actualTotal,
                String.format("The result should be '%s' but it's %s", StringsConstans.EXPECTED_TOTAL_AFTER_REMOVING_INTERNAL_SURCHARGE, actualTotal));
    }

    @Test(description = "Verify that 'Storage surcharge' labels must have a minimum of 2 characters. On entering a label shorter than this, an appropriate error message should be displayed, and the system should retain its last valid state.", priority = 4)
    public void editPriceComponentOfStorageSurchargeTest() {
        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        priceComponentPage
                .addPriceComponents()
                .editStorageSurcharge()
                .enterStorageSurchargeLabel(StringsConstans.INVALID_STORAGE_SURCHARGE_LABEL_INPUT);

        String actualErrorMessage = priceComponentPage.getStorageSurchargeLabelErrorMessage();
        Assert.assertEquals(StringsConstans.LABEL_TOO_SHORT_ERROR_MESSAGE, actualErrorMessage,
                String.format("The message should be '%s' but it's %s", StringsConstans.LABEL_TOO_SHORT_ERROR_MESSAGE, actualErrorMessage));

        // Restoring last valid state because the input is invalid
        priceComponentPage.clickRowCheckIcon();
        String actualLabelText = priceComponentPage.getStorageSurchargeLabelText();
        Assert.assertEquals(StringsConstans.VALID_STORAGE_SURCHARGE_LABEL, actualLabelText);
    }

    @Test(description = "Verify that attempting to set a negative value for 'Scrap surcharge' triggers the appropriate error message and the value is reverted to its previous valid state.", priority = 5)
    public void editPriceComponentOfScrapSurchargeTest() {
        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);
        priceComponentPage.addPriceComponents();

        // Step 1: Get and store the current valid value
        String initialScrapSurchargeValue = priceComponentPage.getScrapSurchargeValue();

        priceComponentPage
                .editScrapSurcharge()
                .setScrapSurchargeValue(StringsConstans.SCRAP_SURCHARGE_NEGATIVE_VALUE_INPUT);

        // Step 2: Verify the error message
        String actualErrorMessage = priceComponentPage.getValueError();
        Assert.assertEquals(StringsConstans.NEGATIVE_VALUE_ERROR_MESSAGE, actualErrorMessage,
                String.format("Expected error message: '%s', but got: %s", StringsConstans.NEGATIVE_VALUE_ERROR_MESSAGE, actualErrorMessage));

        // Step 3: Check if the value has been reverted back to its last valid state
        String revertedScrapSurchargeValue = priceComponentPage.getScrapSurchargeValue();
        Assert.assertEquals(initialScrapSurchargeValue, revertedScrapSurchargeValue,
                String.format("Expected Scrap surcharge value to revert to: '%s', but got: %s", initialScrapSurchargeValue, revertedScrapSurchargeValue));
    }

    @Test(description = "Verify that updating the 'Alloy Surcharge' component affects the total amount accurately.", priority = 6)
    public void editPriceComponentOfAlloySurcharge() {
        PriceComponentPage priceComponentPage = new PriceComponentPage(driver);

        priceComponentPage
                .addPriceComponents()
                .hoverOverAlloySurcharge()
                .startEditingAlloySurcharge()
                .setAlloySurchargeValue(StringsConstans.ALLOY_SURCHARGE_VALUE)
                .confirmAlloySurchargeEdit();

        String actualResult = priceComponentPage.getTextFromTotal();
        Assert.assertEquals(StringsConstans.EXPECTED_ALLOY_SURCHARGE_TOTAL, actualResult,
                String.format("Expected result: '%s', but got: '%s'", StringsConstans.EXPECTED_ALLOY_SURCHARGE_TOTAL, actualResult));
    }
}
