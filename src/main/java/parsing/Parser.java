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
    private HashSet<String> transactionsIds = new HashSet<>();
    public Parser() throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/test.json")); //TODO: заменить test на transactions
        jsonObject = (JSONObject) jsonObject.get("transactions");
        findTransactionsIds();
    }
    private void findTransactionsIds() throws FileNotFoundException {
        File file = new File("src/main/resources/test.json");
        Scanner scanner = new Scanner(file);
        int lineCounter = 0;
        for (int i = 0; i < 2; i++) {
            System.out.println("[Skipped] : " + scanner.nextLine());
        }
        String line = scanner.nextLine();
        line = line.replace("\t\t", "");
        line = line.replace(": {", "");
        transactionsIds.add(line);
        lineCounter++;

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (lineCounter == 21) {
                System.out.println(line);
                line = line.replace("\t\t", "");
                line = line.replace(": {", "");
                transactionsIds.add(line);
                lineCounter = 0;
            }
            lineCounter++;
        }

        transactionsIds.remove("\t}");
        System.out.println("HashSet");
        for (String id : transactionsIds) {
            System.out.println(id);
        }

        scanner.close();
    }

}
