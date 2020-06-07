package org.qa.autotests.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.qa.autotests.BaseTest.logger;

public class PopUpPage extends BasePage<PopUpPage>{
    @Step("Установили регион {value} в поле ввода")
    public YaMarketPage setRegionValue(String value) {
        $x("//input[contains(@placeholder,'Укажите другой регион')]").waitUntil(Condition.appear, minTimeoutToWait).setValue(value);
        $$x("//a[contains(@data-tid, '') and ancestor::form]").findBy(Condition.text(value)).click();
        $x("//button[@type='submit' and descendant::span[contains(.,'Продолжить')]]").click();
        logger.debug("[шаг] установили регион {} в поле ввода", value);
        return Selenide.page(YaMarketPage.class);
    }
}
