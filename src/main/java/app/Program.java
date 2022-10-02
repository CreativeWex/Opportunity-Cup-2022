package app;

import frauds.ExpensiveTransactions;
import logic.Database;
import models.Transaction;
import models.User;
import org.json.simple.parser.ParseException;
import logic.Parser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

public class Program {
    public static void main(String[] args) throws IOException, ParseException, SQLException {
        Parser parser = new Parser();
        List<Transaction> transactions = parser.getTransactions();
        List<User> users = parser.getUsers();

        Database database = new Database();
//        database.fillDatabaseFromList(transactions);

        System.out.println("=================[БОЛЬШЕ 100К В ДЕНЬ И 1млн В МЕСЯЦ]=====================");
        ExpensiveTransactions expensiveTransactions = new ExpensiveTransactions(Database.getConnection());
        expensiveTransactions.getFraudTransactionsIds();

    }
}
