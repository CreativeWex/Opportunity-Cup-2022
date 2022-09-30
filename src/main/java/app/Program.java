package app;

import logic.DataBase;
import models.Transaction;
import org.json.simple.parser.ParseException;
import logic.Parser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Program {
    public static void main(String[] args) throws IOException, ParseException, SQLException {
        Parser parser = new Parser();
        List<Transaction> transactions = parser.getTransactions();
        DataBase dataBase = new DataBase();
        dataBase.fillDatabaseFromList(transactions);
    }
}
