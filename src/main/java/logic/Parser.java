package logic;

import models.Transaction;
import models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Parser {
    private final List<Transaction> transactions = new ArrayList<>();
    private final HashSet<String> transactionsIds = new HashSet<>();
    private void findTransactionsIds() throws FileNotFoundException {
        File file = new File("src/main/resources/test.json"); //TODO: заменить test на transactions
        Scanner scanner = new Scanner(file);
        int lineCounter = 0;
        for (int i = 0; i < 2; i++) {
            scanner.nextLine();
        }
        String line = scanner.nextLine();
        line = line.replace("\t\t", "");
        line = line.replace(": {", "");
        line = line.replace("\"", "");
        transactionsIds.add(line);
        lineCounter++;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (lineCounter == 21) {
                line = line.replace("\t\t", "");
                line = line.replace(": {", "");
                line = line.replace("\"", "");
                transactionsIds.add(line);
                lineCounter = 0;
            }
            lineCounter++;
        }
        transactionsIds.remove("\t}");
        scanner.close();
    }

    public Parser() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/test.json")); //TODO: заменить test на transactions
        jsonObject = (JSONObject) jsonObject.get("transactions");
        findTransactionsIds();

        for (String id : transactionsIds) {
            JSONObject jsonTransaction = (JSONObject) jsonObject.get(id);
            User client = new User(
                    (String) jsonTransaction.get("client"),
                    (String) jsonTransaction.get("last_name"),
                    (String) jsonTransaction.get("first_name"),
                    (String) jsonTransaction.get("patronymic"),
                    (String) jsonTransaction.get("date_of_birth"),
                    jsonTransaction.get("passport").toString(),
                    (String) jsonTransaction.get("passport_valid_to"),
                    (String) jsonTransaction.get("phone"));

            Transaction transaction = new Transaction(client,
                    id,
                    (String) jsonTransaction.get("date"),
                    (String) jsonTransaction.get("card"),
                    (String) jsonTransaction.get("account"),
                    (String) jsonTransaction.get("account_valid_to"),
                    (String) jsonTransaction.get("oper_type"),
                    (Double) jsonTransaction.get("amount"),
                    (String) jsonTransaction.get("oper_result"),
                    (String) jsonTransaction.get("terminal"),
                    (String) jsonTransaction.get("terminal_type"),
                    (String) jsonTransaction.get("city"),
                    (String) jsonTransaction.get("address"));
            transactions.add(transaction);
        }
    }
    private void displayTransactionIds() {
        System.out.println("Ids:");
        for (String id : transactionsIds) {
            System.out.println(id);
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
