package org.qa.autotests;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.qa.autotests.pages.YaMarketPage;
import org.qa.autotests.utils.PropParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.invoke.MethodHandles.lookup;

public abstract class BaseTest {

    public final static Logger logger = LoggerFactory.getLogger(lookup().lookupClass());

    public YaMarketPage yaMarketPage;

    @BeforeAll
    static void setupClass() throws IOException {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        //set Selenide properties
        PropParser.setProperties("selenide.properties");
        //set locator UI map
        PropParser.setProperties("locators.properties");

        logger.info("Set up environment");

    }

    @AfterAll
    static void cleanupClass() {
        SelenideLogger.removeListener("AllureSelenide");
        if (WebDriverRunner.hasWebDriverStarted()) WebDriverRunner.closeWebDriver();
    }

    @BeforeEach
    protected void setupTest() {
        this.yaMarketPage = open("/", YaMarketPage.class);
    }

    @AfterEach
    protected void cleanupTest() {
        WebDriverRunner.closeWindow();
    }
}
