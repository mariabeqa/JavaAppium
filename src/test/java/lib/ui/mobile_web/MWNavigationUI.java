package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {

    static {
        MY_LISTS_LINK = "css:a.menu__item--watchlist";
        OPEN_NAVIGATION = "css:label#mw-mf-main-menu-button";

    }

    public MWNavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
}
