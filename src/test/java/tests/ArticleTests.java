package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {

    private static final String LOGIN = "Mariabeqa";
    private static final String PW = "6522174!";

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open 'Java Object-oriented programming language' article and make sure the title is expected")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        articlePageObject.takeScreenshot("article_page");

        Assert.assertEquals("We see unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    @Ignore
    @Test
    @Feature(value = "Article")
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test testSwipeArticleTitle")
    @Severity(value = SeverityLevel.MINOR)
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
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"), @Feature(value = "My list")})
    @DisplayName("Verify article title after other article in My Lists is deleted")
    @Description("We add two articles to My List and then delete one of the articles from My List.")
    @Severity(value = SeverityLevel.NORMAL)
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
            Assert.assertEquals("We are not on the same page after login",
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
            Assert.assertEquals(secondArticleTitle, actualTitle);
        } else {
            articlePageObject.waitForTitleElement(secondArticleTitle);
        }

    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Verify article title after we open an article")
    @Description("We search for an article and open it to check title")
    @Severity(value = SeverityLevel.NORMAL)
    public void testArticleTitle() {
        String searchWord = "Selenium";
        String articleTitle = "Selenium (software)";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchWord);
        searchPageObject.clickByArticleWithSubstring(articleTitle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String actualTitle = articlePageObject.getArticleTitle();

        Assert.assertEquals("Actual title is different from expected title: " + articleTitle,
                articleTitle, actualTitle);
    }

}
