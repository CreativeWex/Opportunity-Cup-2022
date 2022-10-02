package app;

import Frauds.ManyTransactionsADay;
import logic.Database;
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

        Database database = new Database();
        database.fillDatabaseFromList(transactions);

        System.out.println("=================[БОЛЬШЕ 10 ТРАНЗАКЦИЙ ЗА ДЕНЬ]=====================");
        ManyTransactionsADay manyTransactionsADay = new ManyTransactionsADay(Database.getConnection());
        manyTransactionsADay.getFraudTransactionsIds();
        System.out.println("======================================");



        Database.closeConnection();
    }
}
