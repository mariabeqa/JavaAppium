package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;
import java.util.stream.Collectors;

public abstract class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_FIELD_PLACEHOLDER,
            SEARCH_RESULT_TITLE,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getSearchResultElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getSearchResultElementByTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESC}", description);
    }
    /* TEMPLATES METHODS */

    @Step("Initializing the search field")
    public void initSearchInput() {
        this. waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element",
                5);

        this.waitForElementAndClick(SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5);
    }

    @Step("Waiting for the button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                5);
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                5);
    }

    @Step("Click button to cancel search result")
    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5);
    }

    @Step("Typing '{searchLine}' to the search line")
    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(SEARCH_INPUT,
                searchLine,
                "Cannot find and type into search input",
                5);
    }

    @Step("Waiting for search result - '{substring}'")
    public void waitForSearchResult(String substring) {
        String searchResultXpath = getSearchResultElement(substring);
        this.waitForElementPresent(searchResultXpath,
                "Cannot find search result with substring " + substring,
                5);
    }

    @Step("Waiting for search result and select article by substring '{substring}' in article title")
    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getSearchResultElement(substring);
        this.waitForElementAndClick(searchResultXpath,
                "Cannot find search result with substring " + substring,
                5);
    }

    @Step("Get amount of articles")
    public int getAmountOfArticles() {
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request ",
                15);

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Wait for empty search label")
    public void waitForEmptyResultLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15);
    }

    @Step("Making sure there are no results for the search")
    public void assertThereIsNoSearchResults() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT,
                "There are some search results found");
    }

    @Step("Making sure there is search placeholder in search field")
    public void assertThereIsSearchPlaceholder() {
        this.waitForElementPresent(SEARCH_FIELD_PLACEHOLDER,
                "There is no placeholder text in search field");
    }

    @Step("Get article that contains '{searchTerm}'")
    public List<WebElement> getArticlesThatContains(String searchTerm) {
        List<WebElement> searchResults = this.waitForElementsPresent(SEARCH_RESULT_TITLE,
                "There is no search results found",
                15);

        return searchResults
                .stream()
                .filter(result -> result.getText().contains(searchTerm))
                .collect(Collectors.toList());

    }

    @Step("Wait search result that contains '{title}' and '{description}'")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String searchResultXpath = getSearchResultElementByTitleAndDescription(title, description);

        this.waitForElementPresent(searchResultXpath,
                String.format("Cannot find search result with title '%1$s' and description '%2$s'", title, description),
                15);
    }

}
