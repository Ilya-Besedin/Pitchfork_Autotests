package pitchfork;

import com.codeborne.selenide.ElementsCollection;
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
import static com.codeborne.selenide.Selenide.*;
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
    ElementsCollection genreFilterNews = $$(".news-collection-fragment");
    ElementsCollection genreFilterTopNews = $$(".container-fluid");

    //Value Source tests checks genre tag in News
    @Tag("smoke_test")
    @ValueSource(strings = {"Jazz", "Metal", "Rap/Hip-Hop", "Electronic"})
    @ParameterizedTest(name = "Checking set \"{0}\" filter in News")
    void setGenreFilterTest(String genre) {
        step("On Nav bar click News", () -> {
            cookiePopup.shouldBe(visible);
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
    @Tag("Regression_tests")
    @CsvSource(value = {
            "Jazz| Kamasi Washington Shares New Song “The Garden Path”: Listen",
            "Electronic| Watch LCD Soundsystem Perform “Thrills” and “Yr City’s a Sucker” on",
            "Rap/Hip-Hop| Dave Shares Video for New Song “Starlight”: Watch",
            "Metal| Rage Against the Machine Delay Start of Reunion Tour",
    },
            delimiter = '|' //change separator ',' to '|'
    )
    @ParameterizedTest(name = "Checking news list by \"{0}\" filter")
    @Disabled
    void checkGenreFilterContent(String genre, String expectedTest) {
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText(genre)).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterNews.findBy(text(expectedTest)).shouldBe(visible);
    }

    //MethodSource tests check articles in genre filtered news
    static Stream<Arguments> testWithArgumentsData() {
        return Stream.of(
                Arguments.of("Jazz", "Kamasi Washington Shares New Song “The Garden Path”: Listen", "Nubya Garcia Announces 2022 U.S. Tour"),
                Arguments.of("Metal", "Rage Against the Machine Delay Start of Reunion Tour", "Marilyn Manson Sues Evan Rachel Wood for Defamation")
        );
    }

    @Tag("Regression_tests")
    @MethodSource(value = "testWithArgumentsData") //can be written like @MethodSource ("testWithArgumentsData()"
    @ParameterizedTest
    @Disabled
    void testWithArguments(String genre, String firstNews, String secondNews) {
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText(genre)).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterNews.findBy(text(firstNews)).shouldBe(visible);
        genreFilterTopNews.findBy(text(secondNews)).shouldBe(visible);
    }
}