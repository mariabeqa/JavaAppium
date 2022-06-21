package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TMP,
        ARTICLE_BY_TITLE_TMP,
        CLOSE_LOGIN_POPUP,
        REMOVE_FROM_SAVED_BUTTON;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TMP.replace("{FOLDER_NAME}", folderName);
    }

    private static String getSavedArticleXpathByName(String articleTitle) {
        return ARTICLE_BY_TITLE_TMP.replace("{TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
    }

    public void openFolderByName(String folderName) {
        String folderNameXpath = getFolderXpathByName(folderName);
        this.waitForElementAndClick(folderNameXpath,
                "Cannot find folder by " + folderName,
                5);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleAppearByTitle(articleTitle);
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        if ((Platform.getInstance().isIOS()) || (Platform.getInstance().isAndroid())) {
            this.swipeElementToLeft(articleTitleXpath,
                    "Cannot find saved article");
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementAndClick(removeLocator, "Cannot remove article from Saved",
                    10);
        }

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(articleTitleXpath,
                    "Cannot find saved article");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }
        this.waitForArticleDisappearByTitle(articleTitle);
    }

    public void waitForArticleDisappearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        this.waitForElementNotPresent(articleTitleXpath,
                "Saved article still present by title " + articleTitle,
                15);
    }

    public void waitForArticleAppearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        this.waitForElementPresent(articleTitleXpath,
                "Cannot find saved title by title " + articleTitle,
                15);
    }

    public void openArticleByTitle(String articleTitle) {
        this.waitForArticleAppearByTitle(articleTitle);
        String articleTitleXpath = getSavedArticleXpathByName(articleTitle);

        this.waitForElementAndClick(articleTitleXpath,
                "Cannot find nav button to My lists",
                5);
    }

    public void dismissLogInPopUp() {
        if (Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(CLOSE_LOGIN_POPUP,
                    "Cannot find nav button to My lists",
                    5);
        }
    }

}
