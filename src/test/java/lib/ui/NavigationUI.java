package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class NavigationUI extends MainPageObject {

    protected static String
        MY_LISTS_LINK,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Open Navigation")
    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find open nav button",5);
        } else {
            System.out.println("Method openNavigation does nothing for platform " + Platform.getInstance().getPlatformVar());

        }
    }

    @Step("Open My Lists")
    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(MY_LISTS_LINK, "Cannot find nav button to My lists", 5);
        } else {
            this.waitForElementAndClick(MY_LISTS_LINK,
                    "Cannot find nav button to My lists",
                    5);
        }
    }


}
