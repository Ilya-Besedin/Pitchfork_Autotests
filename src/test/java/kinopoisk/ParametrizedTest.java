package kinopoisk;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.$;

public class ParametrizedTest {
    @Test
    @DisplayName("Оценка без авторизации вызывает окно логина");
    void ratingTest() {
        Selenide.open("https://www.kinopoisk.ru");
        $("[data-tid=\"b0e8f9b\"]").setValue("Дюна");
    }
}


