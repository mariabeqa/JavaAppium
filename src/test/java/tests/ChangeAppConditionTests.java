package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;

@Epic("Tests for articles")
public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Verify that article title is correct after screen rotation")
    @Description("We search for an article, open it and check title is the same after screen rotation")
    @Severity(value = SeverityLevel.NORMAL)
    public void testScreenOrientationOnSearchResults() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String titleBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();

        Assert.assertEquals("Title has been changed after rotation",
                titleBeforeRotation,
                titleAfterRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();

        Assert.assertEquals("Title has been changed after rotation",
                titleAfterRotation,
                titleAfterSecondRotation);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Verify search page results after app was in the background")
    @Description("We search for an article, put an app in the background, get back to the app to check search results")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckSearchArticleInBackground() {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundUp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

}
