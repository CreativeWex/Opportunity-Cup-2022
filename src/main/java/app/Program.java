package app;

import frauds.ExpensiveTransactions;
import frauds.ManyTransactionsADay;
import frauds.MinTimeBeforeDebitAndCredit;
import logic.Database;
import logic.ResultWriter;
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

        System.out.println("==[1]==");
        ManyTransactionsADay manyTransactionsADay = new ManyTransactionsADay(Database.getConnection());
        manyTransactionsADay.insertIntoDatabase();
        System.out.println("=======");

        System.out.println("==[2]==");
        MinTimeBeforeDebitAndCredit minTimeBeforeDebitAndCredit = new MinTimeBeforeDebitAndCredit(Database.getConnection(), parser.getUsers());
        minTimeBeforeDebitAndCredit.insertIntoDatabase();
        System.out.println("=======");

        System.out.println("==[3, 4]==");
        ExpensiveTransactions expensiveTransactions = new ExpensiveTransactions(Database.getConnection());
        expensiveTransactions.getFraudTransactionsIds();

        ResultWriter resultWriter = new ResultWriter(Database.getConnection(),
                manyTransactionsADay.getFraudTransactionsIds(), minTimeBeforeDebitAndCredit.getFraudTransactionsIds(),
                expensiveTransactions.getExpensiveTransIds(), expensiveTransactions.getExpensiveMonthTransIds());
        resultWriter.createResultFile();
        System.out.println("Result file created");
        Database.closeConnection();
    }
}

//TODO: pattarn facade