package com.example.demo.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//TODO конец файла испорчен
public class PostingsService {
    public static final String AUTHORIZED_POSTING = "AuthorizedPosting";
    static Map<String, Boolean> accountNameAndStatus = new HashMap<>();
    static String POSTINGS_IN_FILE_PATH = "src/main/resources/postings.csv";
    static String POSTINGS_OUT_FILE_PATH = "src/main/resources/postings_out.csv";
    static String LOGINS_FILE_PATH = "src/main/resources/logins.csv";
    static int USERNAME_ORDINAL_NUMBER = 9;

    public static void execute() throws IOException {
        setAccountsToMapFromLogins();

        changePostingsFile();
    }

    private static void changePostingsFile() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(POSTINGS_OUT_FILE_PATH), '\t', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        try (CSVReader fileReader = new CSVReader(new FileReader(POSTINGS_IN_FILE_PATH), '\t', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER)) {
            String[] header = fileReader.readNext();
            String[] newHeader = Arrays.copyOf(header, header.length + 1);
            newHeader[newHeader.length - 2] = newHeader[newHeader.length - 2].concat(";");
            newHeader[newHeader.length - 1] = AUTHORIZED_POSTING;
            writer.writeNext(newHeader);

            String[] line;
            while ((line = fileReader.readNext()) != null) {
                if (line[0].equals("")) {
                    writer.writeNext(new String[]{});
                    continue;
                }
                String newField;
                if (accountNameAndStatus.get(line[USERNAME_ORDINAL_NUMBER]) != null)
                    newField = String.valueOf(accountNameAndStatus.get(line[USERNAME_ORDINAL_NUMBER]).booleanValue());
                else newField = String.valueOf(false);
                String[] newLine;
                if (line[line.length - 1].equals("")) {
                    line[line.length - 2] = line[line.length - 2].concat(";");
                    line[line.length - 1] = newField;
                    newLine = Arrays.copyOf(line, line.length);
                } else {
                    newLine = Arrays.copyOf(line, line.length + 1);
                    newLine[newLine.length - 2] = newLine[newLine.length - 2].concat(";");
                    newLine[newLine.length - 1] = newField;
                }

                writer.writeNext(newLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
    }

    private static void setAccountsToMapFromLogins() {
        try (CSVReader fileReader = new CSVReader(new FileReader(LOGINS_FILE_PATH), '\t', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER)) {
            fileReader.readNext();
            String[] line;

            while ((line = fileReader.readNext()) != null) {
                accountNameAndStatus.put(line[1].substring(0, line[1].length() - 1), Boolean.parseBoolean(line[2].substring(0, line[2].length() - 1)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        execute();
    }
}
