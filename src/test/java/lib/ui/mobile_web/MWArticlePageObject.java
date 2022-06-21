package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "css:h1#firstHeading";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:a#ca-watch";
        OPTIONS_REMOVE_FROM_MY_LISTS_BUTTON = "css:a#ca-watch.watched";
        ADD_TO_READING_LIST_BUTTON = "xpath://*[@text = 'Add to reading list']";
        FOLDER_NAME_ELEMENT = "xpath://*[@text = '{SUBSTRING}']";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
