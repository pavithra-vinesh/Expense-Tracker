package services;

import models.Expense;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String FILE_PATH = "data/expenses.txt";
    
    public static void saveExpenses(List<Expense> expenses) {
        try {
            
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Expense expense : expenses) {
                writer.write(expense.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }
    
    public static List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return expenses;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                Expense expense = Expense.fromFileString(line);
                if (expense != null) {
                    expenses.add(expense);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
        return expenses;
    }
}