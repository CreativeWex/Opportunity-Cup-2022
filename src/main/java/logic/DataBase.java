package logic;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Transaction;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataBase {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/open_cup";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static Connection connection;

    public DataBase() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USERNAME);
            config.setPassword(DB_PASSWORD);

            HikariDataSource hikariDataSource = new HikariDataSource(config);
            if (hikariDataSource.getConnection() == null) {
                throw new SQLException("Database connection failed");
            }
            connection = hikariDataSource.getConnection();
            System.out.println("Database successfully connected");
        }
        catch (Exception exception) {
            throw new RuntimeException("Connection failed from createDataSourceConnection: " + exception);
        }
    }
    public void fillDatabaseFromList(List<Transaction> list) throws SQLException {
        for (Transaction transaction : list) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" +
                    "(client, last_name, first_name, patronymic, date_of_birth, passport, passport_valid_to, phone)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            User client = transaction.getClient();
            preparedStatement.setString(1, client.getClientId());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getFirstName());
            preparedStatement.setString(4, client.getPatronymic());
            preparedStatement.setTimestamp(5, client.getDateOfBirth());
            preparedStatement.setString(6, client.getPassport());
            preparedStatement.setTimestamp(7, client.getPassportValidTo());
            preparedStatement.setString(8, client.getPhone());
            preparedStatement.executeUpdate();
        }



    }
}
