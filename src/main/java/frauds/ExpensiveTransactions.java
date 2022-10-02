package frauds;

import java.sql.*;
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
                "GROUP BY client, t.transaction_date::date\n" +
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
        ResultSet resultSet = statement.executeQuery("SELECT t.transaction_date::date date FROM users\n" +
                "INNER JOIN transactions t on t.id = users.transaction_id\n" +
                "WHERE t.oper_result LIKE 'Успешно'\n" +
                "GROUP BY client, t.transaction_date::date\n" +
                "HAVING sum(amount) > 100000;");
        while (resultSet.next()) {
            date.add(resultSet.getString("date"));
        }

        statement.close();
        return date;
    }


    private static final LinkedList<String> transactions = new LinkedList<>();

    private void findSuspiciousTransaction(String usersId, Date date) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT t.id FROM transactions t\n" +
                        "INNER JOIN users u on t.id = u.transaction_id\n" +
                        "WHERE oper_result LIKE 'Успешно' AND client = ? AND transaction_date::date = ?;");

        preparedStatement.setString(1, usersId);
        preparedStatement.setDate(2, date);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            transactions.add(resultSet.getString("id"));
        }
        preparedStatement.close();
    }


    @Override
    public void insertIntoDatabase() throws SQLException {
        Statement createTable = connection.createStatement();
        createTable.executeUpdate("DROP TABLE IF EXISTS fraud_expensive_transactions; " +
                "CREATE TABLE IF NOT EXISTS fraud_expensive_transactions" +
                "(transaction_id TEXT PRIMARY KEY REFERENCES transactions(id));");
        createTable.close();
        System.out.println("fraud_expensive_transactions table has been created");

        LinkedList<String> transactionsIds = transactions;
        for (String id : transactionsIds) {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO fraud_expensive_transactions(transaction_id) VALUES (?);");
            insert.setString(1, id);
            insert.executeUpdate();
        }
        System.out.println("All data has been inserted into fraud_many_transactions_a_day");

    }

    @Override
    public void getFraudTransactionsIds() throws SQLException {
        LinkedList<String> suspiciousUsersId = findUserIdInDay();
        LinkedList<String> suspiciousDate = findDateInDay();
        for (int i = 0; i < suspiciousUsersId.size(); i++) {
            findSuspiciousTransaction(suspiciousUsersId.get(i), Date.valueOf(suspiciousDate.get(i)));
        }
        insertIntoDatabase();
    }
}


