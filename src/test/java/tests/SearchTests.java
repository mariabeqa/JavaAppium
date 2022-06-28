package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

@Epic("Tests for articles")
public class SearchTests extends CoreTestCase {

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify that search results appear")
    @Description("We search for an article and check search results")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify ability to cancel search")
    @Description("We search for an article and cancel search")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify amount of non-empty search results")
    @Description("We search for an article and check the amount of search results")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfNotEmptySearch() {
        String searchLine = "Linkin Park Discography";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        int amountOfSearchResults = searchPageObject.getAmountOfArticles();

        Assert.assertTrue("We found 0 results",
                amountOfSearchResults > 0);
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify that search results don't appear")
    @Description("We search for an article that doesn't exist and check there is no search results")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfEmptySearch() {
        String searchLine = "jkjnkjnkjnkj";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assertThereIsNoSearchResults();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify search placeholder")
    @Description("We search for an article and check search placeholder")
    @Severity(value = SeverityLevel.MINOR)
    public void testSearchFieldPlaceholderText() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.assertThereIsSearchPlaceholder();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify canceling search")
    @Description("We search for an article and cancel search")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchAndCancel() {
        String searchTerm = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchTerm);
        int amountOfSearchResults = searchPageObject.getAmountOfArticles();

        Assert.assertTrue("There are no articles found in search results",
                amountOfSearchResults > 1);

        searchPageObject.clickCancelSearch();
        searchPageObject.assertThereIsNoSearchResults();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify search results contains search term")
    @Description("We search for an article and check results contain search term")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchResultsContainsText() {
        String searchTerm = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchTerm);

        int amountOfSearchResults = searchPageObject.getAmountOfArticles();
        List<WebElement> amountOfArticlesWithSearchTerm = searchPageObject.getArticlesThatContains(searchTerm);

        Assert.assertTrue(String.format("Не все результаты содержат %s", searchTerm),
                amountOfSearchResults == amountOfArticlesWithSearchTerm.size());
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Verify search results title and description")
    @Description("We search for articles and check its title and description")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchResultTitleAndDescription() {
        String searchTerm = "Java";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchTerm);

        searchPageObject.waitForElementByTitleAndDescription("Java", "Island in Indonesia, Southeast Asia");
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
    }

}
