package app;

import logic.Frauds;
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

        Frauds frauds = new Frauds(Database.getConnection(), users, transactions);
        LinkedHashSet<User> suspiciousUserInDay = frauds.findSuspiciousClientsTransactionsManyInDay();
        LinkedHashSet<User> suspiciousUserInMonth = frauds.findSuspiciousClientsTransactionsManyInMonth();
        System.exit(0);
    }
}
