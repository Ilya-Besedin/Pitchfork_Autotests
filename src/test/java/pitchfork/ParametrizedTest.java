package pitchfork;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;

public class ParametrizedTest {
    @Test
    @DisplayName("Checking genre filter in News")
        void setGenreFilterTest() {
        Selenide.open("https://pitchfork.com/");
        $("li.primary-nav__item").$("a").click();
    }
}



