package pitchfork;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class ParametrizedTest {

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

    @Test
    @DisplayName("Checking genre filter in News")
        void setGenreFilterTest() {
        navBar.$("a").click();
        genreTrigger.click();
        genreMenu.$(".genre-menu__clear").click();
        genreMenu.$(byText("Jazz")).click();
        genreMenu.$(byText("Update Results")).click();
        genreFilterList.shouldHave(text("Jazz"));
    }
}



