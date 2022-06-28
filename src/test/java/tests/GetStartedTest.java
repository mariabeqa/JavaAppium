package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests for registration")
public class GetStartedTest extends CoreTestCase {

    @Test
    @Feature(value = "Onboarding")
    @DisplayName("Passing through onboarding steps")
    @Description("We go through the onboarding steps on Welcome screen")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testPassThroughWelcome() {

        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isMW())) {
            return;
        }

        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreLink();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForNewWayToExploreText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLanguageText();
        welcomePageObject.clickNextButton();

        welcomePageObject.waitForLearnMoreAboutDataCollectedLink();
        welcomePageObject.clickGetStartedButton();
    }

}
