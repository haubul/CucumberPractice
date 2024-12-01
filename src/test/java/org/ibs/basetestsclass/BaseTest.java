package org.ibs.basetestsclass;

import org.ibs.framework.managers.DatabaseManager;
import org.ibs.framework.managers.WebDriverManager;
import org.ibs.framework.pages.ProductPage;
import org.ibs.framework.utils.Consts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import java.sql.SQLException;

/**
 * ����� � ����������� ����� �������
 */

public class BaseTest {

    protected WebDriverManager webDriverManager;
    protected WebDriver driver;
    protected ProductPage productPage;
    protected DatabaseManager databaseManager;

    /**
     * �����, ����������� ����� ������ ������
     * �������������� �������� ���-��������, �������� ��������� ���-��������,
     * ������� ��������� �������� � ��������� ������� ������.
     * ������� ��������� ���� ������
     */
    @BeforeEach
    public void beforeAll() throws SQLException {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        productPage = new ProductPage(driver);
        driver.get(Consts.BASE_URL);
        databaseManager = new DatabaseManager();
    }

    /**
     * �����, ����������� ����� ������� �����
     * ����������� - ������� ������ �� ��
     * ��������� ������ �������� � �������
     * ��������� ���������� � ����� ������
     */
    @AfterEach
    public void afterEach() throws SQLException {
        databaseManager.deleteProductFromDatabase("������");
        databaseManager.deleteProductFromDatabase("��������");
        webDriverManager.quitDriver();
        databaseManager.closeConnection();
    }
}