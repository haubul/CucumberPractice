package org.ibs.framework.managers;

import java.sql.*;

/**
 * Класс для управления базой данных
 */

public class DatabaseManager {

    /**
     * Переменная для хранения объекта соединения
     */

    private Connection connection;

    private static final TestPropManager props = TestPropManager.getTestPropManager();

    public DatabaseManager() throws SQLException {
        initDatabase();
        createFoodTableIfNotExists();
    }


    private void createFoodTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS FOOD (" +
                "food_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "food_name VARCHAR(255) NOT NULL, " +
                "food_type VARCHAR(255) NOT NULL, " +
                "food_exotic INT NOT NULL" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }
    public void initDatabase() throws SQLException {
        if ("remote".equalsIgnoreCase(props.getProperty("type.db"))){
            initRemoteDatabase();
        } else{
            initLocalDatabase();
        }
    }

    /**
     *  Инициализирует соединение с базой данных
     * @throws SQLException - если возникает ошибка при соединении с базой данных
     */

    public void initLocalDatabase() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user",
                "pass"
        );
    }

    public void initRemoteDatabase() throws  SQLException {
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:testdb",
                "user",
                "pass"
        );
    }


    /**
     * Проверяет, существует ли товар с заданными параметрами в базе данных
     * @param name - название товара
     * @param type - тип товара
     * @param exotic - параметр - чекбокс (экзотический или не экзотический)
     * @return возвращает true, если товар существует в БД, иначе возвращает false
     * @throws SQLException - если возникает ошибка при выполнении запроса к базе данных
     */

    public boolean isProductInDatabase(String name, String type, int exotic) throws SQLException {
        String query = "SELECT food_name, food_type, food_exotic FROM FOOD WHERE food_name = ? AND food_type = ? AND food_exotic = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, type);
            statement.setInt(3, exotic);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }


    /**
     * Закрывает соединение с базой данных
     */

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}