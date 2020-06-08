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
        $x(getProperty("PopUpPage.inputRegion")).waitUntil(Condition.appear, minTimeoutToWait).setValue(value);
        $$x(getProperty("PopUpPage.regionHelpList")).findBy(Condition.text(value)).click();
        $x(getProperty("PopUpPage.btnContinue")).click();
        logger.debug("[шаг] установили регион {} в поле ввода", value);
        return Selenide.page(YaMarketPage.class);
    }
}
