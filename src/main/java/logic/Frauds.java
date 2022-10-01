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

    private LinkedHashSet<String> suspiciousTransactions = new LinkedHashSet<>();

    public LinkedHashSet<User> findSuspiciousClientsTransactionsManyInDay() throws SQLException {
        LinkedHashSet<User> usersGotManyTransactions = new LinkedHashSet<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT client FROM users\n" +
                "INNER JOIN transactions t on t.id = users.transaction_id\n" +
                "WHERE t.oper_result LIKE 'Успешно'\n" +
                "GROUP BY client, EXTRACT(DAY FROM t.transaction_date::date)\n" +
                "HAVING sum(amount) > 100000;");

        return fillingUsersGotManyTransactions(resultSet);
    }

    public LinkedHashSet<User> findSuspiciousClientsTransactionsManyInMonth() throws SQLException {
        LinkedHashSet<User> usersGotManyTransactions = new LinkedHashSet<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT client FROM users\n" +
                "INNER JOIN transactions t on t.id = users.transaction_id\n" +
                "WHERE t.oper_result LIKE 'Успешно'\n" +
                "GROUP BY client, EXTRACT(MONTH FROM t.transaction_date::date)\n" +
                "HAVING sum(t.amount) > 1000000;");

        return fillingUsersGotManyTransactions(resultSet);
    }

    private LinkedHashSet<User> fillingUsersGotManyTransactions(ResultSet resultSet) {
        try {
            LinkedHashSet<User> usersGotManyTransactions = new LinkedHashSet<>();
            while (resultSet.next()) {
                suspiciousTransactions.add(resultSet.getString("client"));
            }
            for (String item : suspiciousTransactions) {
                for (User user : allUsers) {
                    if (item.equals(user.getClientId())) {
                        usersGotManyTransactions.add(user);
                        break;
                    }
                }
            }
            suspiciousTransactions.clear();
            return usersGotManyTransactions;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
