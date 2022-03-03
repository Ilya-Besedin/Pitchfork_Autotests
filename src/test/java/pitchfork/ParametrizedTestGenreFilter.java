package pitchfork;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class ParametrizedTestGenreFilter {

    //locators
    SelenideElement navBar = $("li.primary-nav__item");
    SelenideElement genreTrigger = $(".genre-menu__trigger");
    SelenideElement genreMenu = $(".genre-menu__hanging");
    SelenideElement genreFilterList = $("ul.genre-filters__list");

    @BeforeEach
        void precondition() {
        Configuration.browserSize = "1280x800";
        open("https://pitchfork.com/");
    }

    @AfterEach
        void closeBrowser () {
        closeWebDriver();
    }

    @ValueSource (strings = {"Jazz", "Electronic", "Rap/Hip-Hop", "Metal"})
    @ParameterizedTest (name = "Checking \"{0}\" filter in News")
    //@DisplayName("Checking genre filter in News") - mover to @ParameterizedTest
        void setGenreFilterTest(String genre) {
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText(genre)).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterList.shouldHave(text(genre));
    }
}



