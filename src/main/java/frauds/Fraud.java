package frauds;

import java.sql.SQLException;

public interface Fraud {
    public void insertIntoDatabase();
    public void getFraudTransactionsIds() throws SQLException;
}
