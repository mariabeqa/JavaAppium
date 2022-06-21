package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class ArticlePageObject extends MainPageObject {

    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        OPTIONS_REMOVE_FROM_MY_LISTS_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        ADD_TO_READING_LIST_BUTTON,
        FOLDER_NAME_ELEMENT;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getFolderNameElement(String substring) {
        return FOLDER_NAME_ELEMENT.replace("{SUBSTRING}", substring);
    }

    private static String getTitleNameElement(String substring) {
        return TITLE.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE,
                "Cannot find article title",
                15);
    }

    public WebElement waitForTitleElement(String title) {
        return this.waitForElementPresent(getTitleNameElement(title),
                "Cannot find article title",
                15);
    }

    public String getArticleTitle() {
        WebElement title = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title.getAttribute("text");
        } else if(Platform.getInstance().isIOS()){
            return title.getAttribute("name");
        } else {
            return title.getText();
        }

    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    20);
        } else if (Platform.getInstance().isIOS()){
            swipeUpUntilElementAppears(FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40);
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        }

    }

    public void addArticleToMyList(String nameOfFolder) {
        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5);

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);

        this.waitForElementAndClick(ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5);

        this.waitForElementAndClear(MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                5);

        this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
                nameOfFolder,
                "Cannot put text into articles folder input",
                5);

        this.waitForElementAndClick(MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5);
    }

    public void addArticleToAnExistingFolder(String nameOfFolder) {
        String folderNameXpath = getFolderNameElement(nameOfFolder);

        this.waitForElementAndClick(OPTIONS_BUTTON,
                "Cannot find button to open article options",
                15);

        this.waitForElementAndClick(ADD_TO_READING_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);

        this.waitForElementAndClick(folderNameXpath,
                "Cannot find folder name " + nameOfFolder,
                5);
    }

    public void removeArticleFromSavedIfAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LISTS_BUTTON)) {
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LISTS_BUTTON,
                    "Cannot click on remove button",
                    1);
        }
        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add to my list"
        );
    }

    public void closeArticle() {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5);
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());

        }
    }

    public void addArticleToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfAdded();
        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
    }
}
