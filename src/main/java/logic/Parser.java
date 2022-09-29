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
    private List<Transaction> transactions = new ArrayList<>();
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
        displayTransactionIds();  //TODO

        for (String id : transactionsIds) {
            JSONObject jsonTransaction = (JSONObject) jsonObject.get(id);
            System.out.println(jsonTransaction); //TODO
            String clientId = (String) jsonTransaction.get("client");
            String lastName = (String) jsonTransaction.get("last_name");
            String firstName = (String) jsonTransaction.get("first_name");
            String patronymic = (String) jsonTransaction.get("patronymic");
            Timestamp dateOfBirth = (Timestamp) jsonTransaction.get("date_of_birth");
            String passport = (String) jsonTransaction.get("passport");
            Timestamp passportValidTo = (Timestamp) jsonTransaction.get("passport_valid_to");
            String phone = (String) jsonTransaction.get("phone");
            User client = new User(clientId, lastName, firstName, patronymic, dateOfBirth, passport,
                    passportValidTo, phone);

            Timestamp transactionDate = (Timestamp) jsonTransaction.get("date");
            String card = (String) jsonTransaction.get("card");
            String account = (String) jsonTransaction.get("account");
            String operationType = (String) jsonTransaction.get("date_of_birth");
            String amount = (String) jsonTransaction.get("date_of_birth");
            String operationResult = (String) jsonTransaction.get("date_of_birth");
            String terminal = (String) jsonTransaction.get("date_of_birth");
            String terminalType = (String) jsonTransaction.get("date_of_birth");
            String city = (String) jsonTransaction.get("date_of_birth");
            String address = (String) jsonTransaction.get("date_of_birth");


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
