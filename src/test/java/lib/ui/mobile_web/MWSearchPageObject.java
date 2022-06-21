package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:div.header-action>button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://li[@title = '{SUBSTRING}']";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_FIELD_PLACEHOLDER = "id:Search Wikipedia";
        SEARCH_RESULT_TITLE = "xpath://XCUIElementTypeCell//XCUIElementTypeStaticText[1]";
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://XCUIElementTypeCell[.//XCUIElementTypeStaticText[1][@value='{TITLE}']][.//XCUIElementTypeStaticText[2][@value = '{DESC}']]";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
