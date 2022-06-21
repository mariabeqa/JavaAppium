package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:{SUBSTRING}";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        ADD_TO_READING_LIST_BUTTON = "xpath://*[@text = 'Add to reading list']";
        FOLDER_NAME_ELEMENT = "xpath://*[@text = '{SUBSTRING}']";
    }

    public IOSArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
