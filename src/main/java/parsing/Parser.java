package parsing;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Parser {
    private final HashSet<String> transactionsIds = new HashSet<>();
    public Parser() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/test.json")); //TODO: заменить test на transactions
        jsonObject = (JSONObject) jsonObject.get("transactions");
        findTransactionsIds();
        displayTransactionIds();

        for (String id : transactionsIds) {
            JSONObject jsonTransaction = (JSONObject) jsonObject.get(id);
            System.out.println(jsonTransaction);
        }





    }
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
    private void displayTransactionIds() {
        System.out.println("Ids:");
        for (String id : transactionsIds) {
            System.out.println(id);
        }
    }
}
