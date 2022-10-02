package frauds;

import java.sql.SQLException;

public interface Fraud {
    public void insertIntoDatabase() throws SQLException;
    public void getFraudTransactionsIds() throws SQLException;
}
