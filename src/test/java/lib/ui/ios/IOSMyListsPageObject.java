package lib.ui.ios;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TMP = "xpath://XCUIElementTypeStaticText[contains(@name, '{TITLE}')]";
        CLOSE_LOGIN_POPUP = "id:Close";
    }

    public IOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
