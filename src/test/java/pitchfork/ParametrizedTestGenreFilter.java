package pitchfork;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;
import static pitchfork.TestData.*;

public class ParametrizedTestGenreFilter extends TestBase {

    //locators
    SelenideElement cookiePopup = $("#onetrust-button-group-parent");
    SelenideElement navBar = $("li.primary-nav__item");
    SelenideElement genreTrigger = $(".genre-menu__trigger");
    SelenideElement genreMenu = $(".genre-menu__hanging");
    SelenideElement genreFilterList = $("ul.genre-filters__list");
    SelenideElement genreFilterNews = $("#news-page");
    SelenideElement genreFilterTopNews = $(".article-details"); //there checks only first article in the top

    //Value Source tests checks genre tag in News
    @Tag("smoke_test")
    @ValueSource(strings = {"Jazz", "Metal", "Rap/Hip-Hop", "Electronic"})
    @ParameterizedTest(name = "Checking set \"{0}\" filter in News")
    void setGenreFilterTest(String genre) {
        step("On Nav bar click News", () -> {
            cookiePopup.$(byText("I Accept")).click();
            navBar.shouldBe(visible);
            navBar.$("a").click();
        });

        step("Click genre", () -> {
            genreTrigger.click();
        });

        step("In pop-up click 'Clear All Selected'", () -> {
            genreMenu.$(".genre-menu__clear").click();
        });

        step("Fill genre checkbox", () -> {
            genreMenu.$(byText(genre)).click();
        });

        step("Check genre tag", () -> {
            genreMenu.$(byText("Update Results")).click();
            genreFilterList.shouldHave(text(genre));
        });
    }

    //CsvSource tests check articles in genre filtered news
    @Tag("regression_test")
    @CsvSource(value = {
            "Jazz| Sun Ra House in Philadelphia Is Now a Historic Landmark",
            "Electronic| Watch LCD Soundsystem Perform “Thrills” and “Yr City’s a Sucker” on",
            "Rap/Hip-Hop| Dave Shares Video for New Song “Starlight”: Watch",
            "Metal| Rage Against the Machine Delay Start of Reunion Tour",
    },
            delimiter = '|' //change separator ',' to '|'
    )
    @ParameterizedTest(name = "Checking news list by \"{0}\" filter")
    void checkGenreFilterContent(String genre, String expectedTest) {
        cookiePopup.$(byText("I Accept")).click();
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText(genre)).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterNews.$(byText(expectedTest)).shouldBe(visible);
    }

    //MethodSource tests check articles in genre filtered news
    static Stream<Arguments> testWithArgumentsData() {
        return Stream.of(
                Arguments.of(genre_one, genre_one_news, genre_one_top_news),
                Arguments.of(genre_two, genre_two_news, genre_two_top_news)
        );
    }

    @Tag("regression_test")
    @MethodSource(value = "testWithArgumentsData") //can be written like @MethodSource ("testWithArgumentsData()"
    @ParameterizedTest
    void testWithArguments(String genre, String firstNews, String secondNews) {
        cookiePopup.$(byText("I Accept")).click();
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText(genre)).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterNews.$(byText(firstNews)).shouldBe(visible);
        genreFilterTopNews.$(byText(secondNews)).shouldBe(visible);
    }
}