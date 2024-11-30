package org.ibs.basetestsclass;

import org.ibs.pages.managers.WebDriverManager;
import org.ibs.pages.pages.ProductPage;
import org.ibs.pages.utils.Consts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * ����� � ����������� ����� �������
 */

public class BaseTest {

    protected WebDriverManager webDriverManager;
    protected WebDriver driver;
    protected ProductPage productPage;

    /**
     * �����, ����������� ����� ������ ������
     * �������������� �������� ���-��������, �������� ��������� ���-��������,
     * ������� ��������� �������� � ��������� ������� ������.
     */
    @BeforeEach
    public void beforeAll() {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        productPage = new ProductPage(driver);
        driver.get(Consts.BASE_URL);
    }

    /**
     * �����, ����������� ����� ������� �����
     * ��������� ������ �������� � �������
     */
    @AfterEach
    public void afterEach() {
        webDriverManager.quitDriver();
    }
}