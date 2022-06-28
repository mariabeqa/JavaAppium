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
import org.junit.Test;

@Epic("Tests for articles")
public class MyListsTests extends CoreTestCase {

    private static final String nameOfFolder = "Learning programming";
    private static final String LOGIN = "Mariabeqa";
    private static final String PW = "6522174!";

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),  @Feature("My list")})
    @DisplayName("Verify that article is added to My list")
    @Description("We search for an article, add it to My List and delete it.")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(nameOfFolder);
        } else if (Platform.getInstance().isIOS()){
            articlePageObject.addArticleToMySaved();
        } else {
            articlePageObject.addArticleToMySaved();
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthBtn();
            authorizationPageObject.enterLoginData(LOGIN, PW);
            authorizationPageObject.submitForm();
            Assert.assertEquals("We are not on the same page after login",
                    articleTitle,
                    articlePageObject.getArticleTitle());
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(nameOfFolder);
        }
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

}
