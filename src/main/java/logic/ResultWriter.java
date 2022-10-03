package logic;

import frauds.ExpensiveTransactions;
import frauds.ManyTransactionsADay;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashSet;

public class ResultWriter {
    private Connection connection;
    private  final String RESULT_FILE_NAME = "result.txt";
    HashSet<String> manyTransADayIds = new HashSet<>();
    HashSet<String> minTimeTransIds = new HashSet<>();
    HashSet<String> expensiveTransIds = new HashSet<>();
    HashSet<String> expensiveMonthTransIds = new HashSet<>();


    public ResultWriter(Connection connection, HashSet<String> manyTransADayIds, HashSet<String> minTimeTransIds) {
        this.connection = connection;
    }

    public void createResultFile() throws IOException {
        FileWriter fileWriter = new FileWriter(RESULT_FILE_NAME);

        fileWriter.write("Более 10 операций в день\n");
        for (String id : manyTransADayIds) {
            System.out.println(id);
            fileWriter.write(id);
        }
        fileWriter.write("\n");

        fileWriter.write("Менее минуты между зачислением средств и их списанием;\n");
        for (String id : minTimeTransIds) {
            fileWriter.write(id);
        }

        fileWriter.close();
    }


}
