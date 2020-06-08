package org.qa.autotests.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.qa.autotests.BaseTest.logger;

public class YaMarketPage extends BasePage<YaMarketPage>{
    @Step("Вызвали popup window для выбора региона")
    public PopUpPage callRegionSelector(String value) {
        this.screenshotEntryPage();
        /*try {
            checkRegionName(value);
        } catch (AssertionError ae){*/
        $x(getProperty("YaMarketPage.regionPopUp"))
                .waitUntil(appear, minTimeoutToWait)
                .click();
        //}
        logger.debug("[шаг] вызвали pop up окно для выбора региона");
        return Selenide.page(PopUpPage.class);
    }

    @Step("Проверили наименование региона")
    private YaMarketPage checkRegionName(String value) {
        Assertions.assertTrue($x(getProperty("YaMarketPage.regionPopUp")).waitUntil(Condition.visible, 30000)
                .has(text(value)),"Название региона не совпадает с ожидаемым");
        logger.debug("[шаг] проверили наименование региона {}", value);
        return this;
    }

    @Step("Ввели запрос в строку поиска ${query}")
    public YaMarketPage setQuery(String query){
        $x(getProperty("YaMarketPage.inputQuery")).setValue(query).pressEnter();
        logger.debug("[шаг] ввели запрос в строку поиска {}", query);
        return this;
    }

    @Step("Отфильтровали результаты поиска по весу до ${filterString}")
    public YaMarketPage filterResults(String filterString, String filterName) throws InterruptedException {
        List<String> listItems = getItemList();
        $x(String.format(getProperty("YaMarketPage.inputFilter"), filterName)).waitUntil(appear, 3000).setValue(filterString);
        Thread.sleep(4000);
        List<String> listItemsCurrent = getItemList();
        //comapre before/after lists, fail if equals
        if (listItems.stream().allMatch(listItemsCurrent::contains)) Assertions.fail();
        logger.debug("[шаг] Отфильтровали результаты поиска по весу до {}", filterString);
        return this;
    }

    List<String> getItemList() throws InterruptedException {
        logger.debug("[шаг] получили список элементов");
        Thread.sleep(4000);
        $$x(getProperty("YaMarketPage.itemHeader")).get(0).waitUntil(Condition.visible, 4000).scrollTo();
        return $$x(getProperty("YaMarketPage.itemHeader")).shouldHave(CollectionCondition.sizeGreaterThan(0)).texts();
    }

    @Step("Нашли наименование товара ${itemName} в списке")
    public int getAndCountItemsInList(String itemName) throws InterruptedException {
        logger.debug("[шаг] Нашли наименование товара {} в списке", itemName);
        return (int) getItemList().stream().filter(x -> x.contains(itemName)).count();
    }

    @Step("Перешли на {} страницу")
    public YaMarketPage clickForMoreReaults(int pageCount){
        for (int i = 1; i < pageCount; i++) {
            $x(getProperty("YaMarketPage.btnViewMore")).click();
        }
        logger.debug("[шаг] Перешли на {} страницу", pageCount);
        return this;
    }

    @Step("Нажали на кнопку {}")
    public YaMarketPage clickButton(String nameBtn){
        $x(String.format(getProperty("YaMarketPage.button"), nameBtn)).waitUntil(Condition.appear, 3000).scrollTo().click();
        logger.debug("[шаг] Нажали на кнопку {}", nameBtn);
        return this;
    }

    @Step("Проверили наличие элемента на странице")
    public YaMarketPage checkItemExist(String text){
        $$x(getProperty("YaMarketPage.itemDescr")).first().waitUntil(Condition.visible, 4000);
        Assertions.assertTrue( $$x(getProperty("YaMarketPage.itemDescr"))
                .filter(Condition.text(text)).size()!=0, "такого элемента не существует");
        logger.debug("[шаг] Проверили наличие {} в списке", text);
        return this;
    }
    @Step("Обновили страницу")
    public YaMarketPage refreshPage() {
        driver.navigate().refresh();
        $x("//div[@data-apiary-widget-name=\"@MarketNode/HeaderLogo\"]").scrollTo();
        return this;
    }
}
