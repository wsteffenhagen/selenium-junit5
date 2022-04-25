package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageobjects.GoogleHomePage;
import pageobjects.GoogleSearchResultsPage;

class GoogleSearchTest extends BaseTest {

    @Test
    @DisplayName("Google anagram search easter egg")
    void googleAnagramSearch() {
        // Navigate to google home page
        GoogleHomePage homePage = new GoogleHomePage(driver).visit();
        // Search for term 'anagram'
        GoogleSearchResultsPage resultsPage = homePage.googleSearch("anagram");

        // Assert did you mean text displays
        softly.assertThat(resultsPage.getDidYouMeanText())
                .as("When 'anagram' is searched on Google 'Did you mean: nag a ram' is shown on results page.")
                .contains("Did you mean:", "nag a ram");
        softly.assertAll();
    }
}
