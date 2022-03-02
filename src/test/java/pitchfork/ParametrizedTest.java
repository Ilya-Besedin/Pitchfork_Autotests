package pitchfork;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class ParametrizedTest {

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
        $("li.primary-nav__item").$("a").click();

    }
}



