package tests;

import application.pageObjects.pages.AnyPage;
import application.pageObjects.pages.CreditCardPage;
import application.pageObjects.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.constants.RegistrationConstants;

public class RyabinaTest extends BaseTest {
    private static final String EXPECTED_RESULTS = "";
    @Test
    public static void registration(){
        AnyPage anyPage = new AnyPage();
        anyPage.getMoneyButton().click();
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isPageDisplayed(), "Page is not open");
        loginPage.getPhone().sendKeys(RegistrationConstants.PHONE);
        loginPage.getLastName().sendKeys(RegistrationConstants.LAST_NAME);
        loginPage.getFirstName().sendKeys(RegistrationConstants.FIRST_NAME);
        loginPage.getMiddleName().sendKeys(RegistrationConstants.MIDDLE_NAME);
        loginPage.getBDay().sendKeys(RegistrationConstants.B_DAY);
        loginPage.getSendButton().click();
        CreditCardPage creditCardPage = new CreditCardPage();
        Assert.assertEquals(creditCardPage.getCreditCardText().getText(), EXPECTED_RESULTS);
    }
}
