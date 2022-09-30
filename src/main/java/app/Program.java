package app;

import models.Transaction;
import org.json.simple.parser.ParseException;
import logic.Parser;

import java.io.IOException;
import java.util.List;

public class Program {
    public static void main(String[] args) throws IOException, ParseException {
        Parser parser = new Parser();
        List<Transaction> transactions = parser.getTransactions();
    }
}
