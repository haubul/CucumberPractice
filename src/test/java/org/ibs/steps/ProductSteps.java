package org.ibs.steps;

import io.cucumber.java.After;
import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.То;
import org.ibs.framework.managers.DatabaseManager;
import org.ibs.framework.managers.TestPropManager;
import org.ibs.framework.managers.WebDriverManager;
import org.ibs.framework.pages.ProductPage;
import org.ibs.framework.utils.Consts;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import java.sql.SQLException;

/**
 * Класс, содержащий шаги для тестирования страницы продуктов с использованием Cucumber
 */

public class ProductSteps {

    private WebDriverManager webDriverManager;
    private WebDriver driver;
    private ProductPage productPage;
    private DatabaseManager databaseManager;
    private static final TestPropManager props = TestPropManager.getTestPropManager();

    /**
     * Открывает страницу продуктов и иницализирует веб-драйвер и базу данных
     * @throws SQLException если возникает ошибка при соединении с базой данных
     */

    /**
     * Метод для выбора URL в зависимости от типа окружения
     */
    private void initUrl() {
        if ("remote".equalsIgnoreCase(props.getProperty("type.driver"))) {
            initRemoteUrl();
        } else {
            initLocalUrl();
        }
    }

    /**
     * Устанавливает URL для локального окружения
     */
    private void initLocalUrl() {
        driver.get(Consts.LOCAL_URL);
    }

    /**
     * Устанавливает URL для удаленного окружения
     */
    private void initRemoteUrl() {
        driver.get(Consts.REMOTE_URL);
    }

    @Допустим("пользователь открывает страницу продуктов")
    public void пользователь_открывает_страницу_продуктов() throws SQLException {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        productPage = new ProductPage(driver);
        initUrl();
        databaseManager = new DatabaseManager();
    }

    /**
     * Открывает выпадающий список на странице
     */

    @И("открывает выпадающий список")
    public void открывает_выпадающий_список() {
        productPage.openNavbarDropdown();
    }

    /**
     * Открывает список товаров на странице
     */

    @И("открывает список товаров")
    public void открывает_список_товаров() {
        productPage.openProductsListPage();
    }

    /**
     * Проверяет, что список товаров открылся
     */

    @Если("список товаров открылся")
    public void список_товаров_открылся() {
        Assertions.assertEquals("Список товаров", productPage.getTitleProductListPage(),
                "Не перешли на страницу со списком товаров");
    }

    /**
     * Добавляет товар с указанными параметрами на страницу
     * @param productName название товара
     * @param productType тип товара
     * @param isExotic  параметр - чекбокс (экзотический или не экзотический)
     */

    @То("пользователь добавляет товар {string} типа {string} с чекбоксом {string}")
    public void пользователь_добавляет_товар_типа_с_чекбоксом(String productName, String productType, String isExotic) {
        boolean exotic = Boolean.parseBoolean(isExotic);
        productPage.addProductToList(productName, productType, exotic);
    }

    /**
     * Проверяет, что товар с указанным названием добавлен на страницу
     * @param productName название товара
     */

    @То("товар {string} должен быть добавлен на страницу")
    public void товар_должен_быть_добавлен_на_страницу(String productName) {
        Assertions.assertTrue(productPage.isProductAdded(productName));
    }

    /**
     * Проверяет, что товар с указанными параметрами добавлен в базу данных
     * @param productName название товара
     * @param productType тип товара
     * @param isExotic  параметр - чекбокс (экзотический или не экзотический)
     * @throws SQLException  если возникает ошибка при выполнении запроса к базе данных
     */

//    @То("товар {string} должен быть добавлен в БД как {string} с экзотичностью {int}")
//    public void товар_должен_быть_добавлен_в_БД(String productName, String productType, int isExotic) throws SQLException {
//        Assertions.assertTrue(databaseManager.isProductInDatabase(productName, productType, isExotic));
//    }

    /**
     * Сбрасывает добавленные товары на странице
     */

    @Если("пользователь сбрасывает товары")
    public void пользователь_сбрасывает_товары() {
        productPage.openNavbarDropdown();
        productPage.resetProducts();
    }

    /**
     * Проверяет, что товары удалены на сайте
     */

    @То("товары должны быть удалены на сайте")
    public void товары_должны_быть_удалены_на_сайте() {
        Assertions.assertThrows(NoSuchElementException.class, () ->
                driver.findElement(By.xpath("//*[contains(text(), 'Огурец')]")));
        Assertions.assertThrows(NoSuchElementException.class, () ->
                driver.findElement(By.xpath("//*[contains(text(), 'Маракуйя')]")));
    }

    /**
     * Проверяет, что товары удалены из базы данных
     * @throws SQLException если возникает ошибка при выполнении запроса к базе данных
     */

    @То("товары должны быть удалены из БД")
    public void товары_должны_быть_удалены_из_БД() throws SQLException {
        Assertions.assertFalse(databaseManager.isProductInDatabase("Огурец", "VEGETABLE", 0));
        Assertions.assertFalse(databaseManager.isProductInDatabase("Маракуйя", "FRUIT", 1));
    }

    /**
     * Закрывает браузер и соединение с базой данных после выполнения тестов
     */

    @И("закрывает браузер и соединение с БД")
    public void закрывает_браузер_и_соединение_с_БД() {
        webDriverManager.quitDriver();
        databaseManager.closeConnection();
    }

    /**
     * Проверяет, что товар с указанными параметрами не был добавлен в базу данных
     * @param productName название товара
     * @param productType тип товара
     * @param isExotic параметр - чекбокс (экзотический или не экзотический)
     * @throws SQLException если возникает ошибка при выполнении запроса к базе данных
     */

    @То("товар {string} не должен быть добавлен в БД как {string} с экзотичностью {int}")
    public void товар_не_должен_быть_добавлен_в_БД(String productName, String productType, int isExotic) throws SQLException {
        Assertions.assertFalse(databaseManager.isProductInDatabase(productName, productType, isExotic),
                "Товар 'Яблоко' был добавлен в БД, хотя не должен был");

    }

}