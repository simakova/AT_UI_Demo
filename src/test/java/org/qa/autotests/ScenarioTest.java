package org.qa.autotests;

import io.qameta.allure.*;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@Epic("AT_UI_Demo")
@Feature("Yandex Market Catalog")
@Severity(SeverityLevel.NORMAL)

public class ScenarioTest extends BaseTest {

    @Test
    @Story("Check list of chocolate items")
    @Description("Проверка категории товаров \"Шоколадные конфеты в коробках\"")
    void test1() throws InterruptedException {
        this.yaMarketPage.callRegionSelector("Москва")
                .setRegionValue("Москва")
                .setQuery("Шоколадные конфеты в коробках, подарочные наборы")
                .filterResults("100", "Вес до")
                .clickForMoreReaults(4);
        Assertions.assertTrue(this.yaMarketPage.getAndCountItemsInList("Raffaello") >
                        this.yaMarketPage.getAndCountItemsInList("Dove"),"Количество Rafaello меньше чем Dove");
        super.yaMarketPage.refreshPage().checkItemExist("кедровый орех");
    }
}
