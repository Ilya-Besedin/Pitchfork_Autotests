package pitchfork;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static java.lang.System.getProperty;

public class TestBase {

    @Owner("IBesedin")
    @Severity(value = SeverityLevel.CRITICAL)
    @Story("Filters on news and search on main page")
    @DisplayName("Checking filters and search")
    @Description("Запуск в Jenkins clean\n" +
            "test\n" +
            "-Dbrowser=${BROWSER}\n" +
            "-DbrowserSize=${BROWSER_SIZE}\n" +
            "-DremoteBrowser=${REMOTE_BROWSER}\n" +
            "-Duser=${USER}\n" +
            "-Dpassword=${PASSWORD}")
    @Link(url = "https://pitchfork.com/")

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        step("Setup testing page", () -> {
            Configuration.baseUrl = "https://pitchfork.com/";
            Configuration.browser = System.getProperty("browser", "chrome");

            String browserSize = getProperty("browserSize", "1920x1080");
            Configuration.browserSize = browserSize;
            Configuration.pageLoadStrategy = "none";

            //Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
            String user = getProperty("user", "user1");
            String password = getProperty("password", "1234");
            String remoteBrowser = getProperty("remoteBrowser", "selenoid.autotests.cloud/wd/hub");
            Configuration.remote = "https://" + user + ":" + password + "@" + remoteBrowser;

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.browserCapabilities = capabilities;
        });
    }

    @BeforeEach
    void precondition() {
        step("Open testing page", () -> {
            open("https://pitchfork.com/");
        });
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }

}