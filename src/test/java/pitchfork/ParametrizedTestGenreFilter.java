package pitchfork;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

public class ParametrizedTestGenreFilter {

    //locators
    SelenideElement navBar = $("li.primary-nav__item");
    SelenideElement genreTrigger = $(".genre-menu__trigger");
    SelenideElement genreMenu = $(".genre-menu__hanging");
    SelenideElement genreFilterList = $("ul.genre-filters__list");
    ElementsCollection genreFilterNews = $$(".news-collection-fragment");
    ElementsCollection genreFilterTopNews = $$(".container-fluid");

    @BeforeAll
    static void browserConfig() {
        Configuration.browserSize = "1280x800";
        Configuration.pageLoadTimeout = 20000;
        Configuration.pageLoadStrategy = "eager";
    }

    @BeforeEach
    void precondition() {
        open("https://pitchfork.com/");
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }

    @ValueSource(strings = {"Jazz", "Electronic", "Rap/Hip-Hop", "Metal"})
    @ParameterizedTest(name = "Checking set \"{0}\" filter in News")
    @Disabled
        //@DisplayName("Checking genre filter in News") - mover to @ParameterizedTest
    void setGenreFilterTest(String genre) {
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText(genre)).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterList.shouldHave(text(genre));
    }

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
        //@DisplayName("Checking genre filter in News") - mover to @ParameterizedTest
    void checkGenreFilterContent(String genre, String expectedTest) {
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText(genre)).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterNews.findBy(text(expectedTest)).shouldBe(visible);
    }

    static Stream<Arguments> testWithArgumentsData() {
        return Stream.of(
                Arguments.of("Jazz", "Kamasi Washington Shares New Song “The Garden Path”: Listen", "Nubya Garcia Announces 2022 U.S. Tour"),
                Arguments.of("Metal", "Rage Against the Machine Delay Start of Reunion Tour", "Marilyn Manson Sues Evan Rachel Wood for Defamation")
        );
    }

    @MethodSource(value = "testWithArgumentsData") //can be written like @MethodSource ("testWithArgumentsData()"
    @ParameterizedTest
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



