package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Ignore;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    private static final String LOGIN = "Mariabeqa";
    private static final String PW = "6522174!";

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        assertEquals("We see unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    @Ignore
    @Test
    public void testSwipeArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Java (programming language)");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testArticleTitleAfterDeletion() {
        String searchWord = "Selenium";
        String nameOfFolder = "Learning Selenium";
        String firstArticleTitle = "Selenium in biology";
        String secondArticleTitle = "Selenium (software)";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(firstArticleTitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
            articlePageObject.addArticleToMyList(nameOfFolder);
        } else if (Platform.getInstance().isIOS()){
            articlePageObject.waitForTitleElement(firstArticleTitle);
            articlePageObject.addArticleToMySaved();
        } else {
            articlePageObject.addArticleToMySaved();
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthBtn();
            authorizationPageObject.enterLoginData(LOGIN, PW);
            authorizationPageObject.submitForm();
            assertEquals("We are not on the same page after login",
                    firstArticleTitle,
                    articlePageObject.getArticleTitle());
        }
        articlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
            searchPageObject.initSearchInput();
        } else {
            searchPageObject.initSearchInput();
        }

        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(secondArticleTitle);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
            articlePageObject.addArticleToAnExistingFolder(nameOfFolder);
        } else {
            articlePageObject.waitForTitleElement(secondArticleTitle);
            articlePageObject.addArticleToMySaved();
        }
        articlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()) {
            searchPageObject.clickCancelSearch();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameOfFolder);
        } else if (Platform.getInstance().isIOS()){
            myListsPageObject.dismissLogInPopUp();
        }

        myListsPageObject.swipeByArticleToDelete(firstArticleTitle);
        myListsPageObject.openArticleByTitle(secondArticleTitle);

        if (Platform.getInstance().isAndroid()) {
            String actualTitle = articlePageObject.getArticleTitle();
            assertEquals(secondArticleTitle, actualTitle);
        } else {
            articlePageObject.waitForTitleElement(secondArticleTitle);
        }

    }

    @Test
    public void testArticleTitle() {
        String searchWord = "Selenium";
        String articleTitle = "Selenium (software)";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(articleTitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String actualTitle = articlePageObject.getArticleTitle();

        assertEquals("Actual title is different from expected title: " + articleTitle,
                articleTitle, actualTitle);
    }

}
