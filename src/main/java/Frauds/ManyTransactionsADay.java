package Frauds;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class ManyTransactionsADay implements Fraud{
    private Connection connection;
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
        return dates;
    }

    private void countTransactionsAmountPerDay(LinkedHashMap<String, Integer> map, String date) {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM transactions WHERE transaction_date ");
    }
    @Override
    public void getFraudTransactionsIds() throws SQLException {
        LinkedHashSet<String> suspiciousUsersIds = findUserId();
        for (String id : suspiciousUsersIds) {
            LinkedHashSet<String> dates = findDatesWhenTransactionsMade(id);
            LinkedHashMap<String, Integer> transactionsAmountPerDay;
            for (String date : dates) {
                countTransactionsAmountPerDay()
            }
        }
    }

    @Override
    public void insertIntoDatabase() {

    }
}