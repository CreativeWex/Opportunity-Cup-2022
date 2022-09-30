package logic;

import models.Transaction;
import models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Frauds {
    private Connection connection;
    private LinkedHashSet<String> gotManyTransactions = new LinkedHashSet<>();
    private List<User> allUsers = new ArrayList<>();
    private List<Transaction> allTransactions = new ArrayList<>();

    public Frauds(Connection connection, List<User> allUsers, List<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
        this.allUsers = allUsers;
        this.connection = connection;
    }

    private LinkedHashSet<User> findSuspiciousClients() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT client FROM users GROUP BY client HAVING COUNT(*) > 10;");
        while (resultSet.next()) {

            gotManyTransactions.add(resultSet.getString("client"));
        }
    }

    public LinkedHashSet<User> getUsersGotManyTransactions() {
        return usersGotManyTransactions;
    }
}
