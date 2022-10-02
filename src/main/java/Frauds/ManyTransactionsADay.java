package Frauds;

import java.sql.*;
import java.util.LinkedHashSet;

public class ManyTransactionsADay implements Fraud{
    private final Connection connection;
    LinkedHashSet<String> fraudTransactions;
    public ManyTransactionsADay(Connection connection) {
        this.connection = connection;
    }
    private LinkedHashSet<String> findUserId() throws SQLException {
        Statement statement = connection.createStatement();
        LinkedHashSet<String> userIds = new LinkedHashSet<>();

        ResultSet resultSet = statement.executeQuery("SELECT client FROM users GROUP BY client HAVING COUNT(*) > 10;");
        while (resultSet.next()) {
            userIds.add(resultSet.getString("client"));
        }

        statement.close();
        return userIds;
    }

    private LinkedHashSet<String> findDatesWhenTransactionsMade(String userId) throws SQLException {
        LinkedHashSet<String> dates = new LinkedHashSet<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT transaction_date FROM users " +
                "INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE client = ?;");
        preparedStatement.setString(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String tmp = resultSet.getString("transaction_date");
            dates.add(tmp.substring(0, tmp.length() - 9));
        }

        preparedStatement.close();
        return dates;
    }

    private int countTransactionsAmountPerDay(String date, String userId) throws SQLException {
        date += " __:__:__";
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT transaction_date FROM users" +
                " INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE transaction_date::text" +
                " LIKE ? AND client = ?;");
        preparedStatement.setString(1, date);
        preparedStatement.setString(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        int amount = 0;
        while (resultSet.next()) {
            amount++;
        }
        return amount;
    }
    @Override
    public void getFraudTransactionsIds() throws SQLException {
        LinkedHashSet<String> suspiciousUsersIds = findUserId();
        for (String userId : suspiciousUsersIds) {
            System.out.println("Пользователь: " + userId);
            LinkedHashSet<String> dates = findDatesWhenTransactionsMade(userId);
            for (String date : dates) {
                System.out.println("Дата: " + date + "; Количество транзакций: " + countTransactionsAmountPerDay(date, userId));
            }
        }
    }

    @Override
    public void insertIntoDatabase() {

    }
}