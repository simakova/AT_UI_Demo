package org.qa.autotests;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.qa.autotests.pages.YaMarketPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.invoke.MethodHandles.lookup;

public abstract class BaseTest {

    public final static Logger logger = LoggerFactory.getLogger(lookup().lookupClass());
    private final static String selenideProperties = "selenide.properties";

    public YaMarketPage yaMarketPage;

    @BeforeAll
    static void setupClass() throws IOException {

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        //set properties
        Properties props = new Properties();
        InputStream inputStream = BaseTest.class
                .getClassLoader()
                .getResourceAsStream(selenideProperties);
        props.load(inputStream);

        if (!props.isEmpty()) {
            for (Object propObj : props.keySet()) {
                String prop = String.valueOf(propObj);

                if (!System.getProperties().containsKey(prop)) {
                    System.setProperty(prop, props.getProperty(prop));
                }
            }
        }

        logger.info("Set selenide properties {}", selenideProperties);
    }

    @AfterAll
    static void cleanupClass() {

        ImmutableMap.Builder<String, String> environmentBuilder = ImmutableMap.builder();

        System.getProperties().forEach((key, val) -> {
            if (key.toString().startsWith("selenide.")) {
                environmentBuilder.put(key.toString(), val.toString());
            }
        });

        System.getProperties().forEach((key, val) -> {
            if (key.toString().startsWith("allure.")) {
                environmentBuilder.put(key.toString(), val.toString());
            }
        });

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
