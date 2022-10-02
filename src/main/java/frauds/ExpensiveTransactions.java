package frauds;

import models.User;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ExpensiveTransactions implements Fraud {
    private Connection connection;

    public ExpensiveTransactions(Connection connection) {
        this.connection = connection;
    }

    private LinkedList<String> findUserIdInDay() throws SQLException {
        LinkedList<String> usersId = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT client FROM users\n" +
                "INNER JOIN transactions t on t.id = users.transaction_id\n" +
                "WHERE t.oper_result LIKE 'Успешно'\n" +
                "GROUP BY client, EXTRACT(DAY FROM t.transaction_date::date)\n" +
                "HAVING sum(amount) > 100000;");

        while (resultSet.next()) {
            usersId.add(resultSet.getString("client"));
        }
        statement.close();
        return usersId;
    }

    private LinkedList<String> findDateInDay() throws SQLException {
        LinkedList<String> date = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT EXTRACT(DAY FROM t.transaction_date::date) date FROM users\n" +
                "INNER JOIN transactions t on t.id = users.transaction_id\n" +
                "WHERE t.oper_result LIKE 'Успешно'\n" +
                "GROUP BY client, EXTRACT(DAY FROM t.transaction_date::date)\n" +
                "HAVING sum(amount) > 100000;");
        while (resultSet.next()) {
            date.add(resultSet.getString("date"));
        }

        statement.close();
        return date;
    }

    private LinkedList<String> findSuspiciousTransaction(String usersId, String date) throws SQLException {
        LinkedList<String> transactions = new LinkedList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT t.id FROM transactions t\n" +
                        "INNER JOIN users u on t.id = u.transaction_id\n" +
                        "WHERE oper_result LIKE 'Успешно' AND client = ? AND EXTRACT(DAY FROM transaction_date::date) = ?;");

//        preparedStatement.setString(1, usersId);
//        preparedStatement.setString(2, date);
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()){
//            transactions.add(resultSet.getString("t.id"));
//        }
        return transactions;
    }


    @Override
    public void insertIntoDatabase() {

    }

    @Override
    public void getFraudTransactionsIds() throws SQLException {
        LinkedList<String> suspiciousUsersId = findUserIdInDay();
        LinkedList<String> suspiciousDate = findDateInDay();
        LinkedList<String> suspiciousTransactions;
        for (int i = 0; i < suspiciousUsersId.size(); i++) {
            suspiciousTransactions = findSuspiciousTransaction(suspiciousUsersId.get(i), suspiciousDate.get(i));
        }
    }
}


