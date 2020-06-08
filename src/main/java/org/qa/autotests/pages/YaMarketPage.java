package org.qa.autotests.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.junit.Assert;
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
        $x("//button[ancestor::div[contains(@data-apiary-widget-name, 'HeaderRegion')]]")
                .waitUntil(appear, minTimeoutToWait)
                .click();
        //}
        logger.debug("[шаг] вызвали pop up окно для выбора региона");
        return Selenide.page(PopUpPage.class);
    }

    @Step("Проверили наименование региона")
    private YaMarketPage checkRegionName(String value) {
        Assert.assertTrue("Название региона не совпадает с ожидаемым",
                $x("//button[ancestor::div[contains(@data-apiary-widget-name, 'HeaderRegion')]]").waitUntil(Condition.visible, 30000)
                        .has(text(value)));
        logger.debug("[шаг] проверили наименование региона {}", value);
        return this;
    }

    @Step("Ввели запрос в строку поиска ${query}")
    public YaMarketPage setQuery(String query){
        $x("//input[@itemprop='query-input']").setValue(query).pressEnter();
        logger.debug("[шаг] ввели запрос в строку поиска {}", query);
        return this;
    }

    @Step("Отфильтровали результаты поиска по весу до ${filterString}")
    public YaMarketPage filterResults(String filterString) throws InterruptedException {
        List<String> listItems = getItemList();
        $x("//input[@name='Вес до']").setValue(filterString);
        Thread.sleep(3000);
        List<String> listItemsCurrent = getItemList();
        //comapre before/after lists, fail if equals
        if (listItems.stream().allMatch(listItemsCurrent::contains)) Assert.fail();
        logger.debug("[шаг] Отфильтровали результаты поиска по весу до {}", filterString);
        return this;
    }

    List<String> getItemList() throws InterruptedException {
        logger.debug("[шаг] получили список элементов");
        new WebDriverWait(driver, 4000).until(driver -> (boolean)((JavascriptExecutor)driver)
                .executeScript("return jQuery.active == 0"));
        Thread.sleep(4000);
        return $$x("//div[contains(@class,'n-snippet-list')]/descendant::h3[contains(@class, 'title')]").shouldHave(CollectionCondition.sizeGreaterThan(0)).texts();
    }

    @Step("Нашли наименование товара ${itemName} в списке")
    public int getAndCountItemsInList(String itemName) throws InterruptedException {
        logger.debug("[шаг] Нашли наименование товара {} в списке", itemName);
        return (int) getItemList().stream().filter(x -> x.contains(itemName)).count();
    }

    @Step("Перешли на {} страницу")
    public YaMarketPage clickForMoreReaults(int pageCount){
        for (int i = 1; i < pageCount; i++) {
            $x("//a[contains(., 'Показать еще')]").click();
        }
        logger.debug("[шаг] Перешли на {} страницу", pageCount);
        return this;
    }

    @Step("Нажали на кнопку {}")
    public YaMarketPage clickButton(String nameBtn){
        $x(String.format("//span[@class='button__text' and contains(text(), '%s')]", nameBtn)).click();
        logger.debug("[шаг] Нажали на кнопку {}", nameBtn);
        return this;
    }

    @Step("Проверили наличие элемента на странице")
    public YaMarketPage checkItemExist(String text){
        Assert.assertTrue("такого элемента не существует", $$x("//div[contains(@class,'n-snippet-list')]/descendant::ul[contains(@class, 'desc')]").filter(Condition.text(text)).size()!=0);
        logger.debug("[шаг] Проверили наличие {} в списке", text);
        return this;
    }
    @Step("Обновили страницу")
    public YaMarketPage refreshPage() {
        driver.navigate().refresh();
        return this;
    }
}
