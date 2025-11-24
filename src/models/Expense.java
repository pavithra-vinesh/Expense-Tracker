package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private String description;
    private double amount;
    private Category category;
    private LocalDate date;
    
    public Expense(String description, double amount, Category category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    
    // Simple getters
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Category getCategory() { return category; }
    public LocalDate getDate() { return date; }
    
    // Convert to string for saving to file
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return description + "|" + amount + "|" + category.getDisplayName() + "|" + date.format(formatter);
    }
    
    // Create expense from saved string
    public static Expense fromFileString(String fileString) {
        try {
            String[] parts = fileString.split("\\|");
            if (parts.length == 4) {
                String description = parts[0];
                double amount = Double.parseDouble(parts[1]);
                Category category = Category.findByName(parts[2]);
                LocalDate date = LocalDate.parse(parts[3]);
                return new Expense(description, amount, category, date);
            }
        } catch (Exception e) {
            System.out.println("Error reading expense data: " + e.getMessage());
        }
        return null;
    }
    
    // Pretty display for user
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return String.format("%-12s %-15s $%-8.2f %s", 
                            date.format(formatter), 
                            category.getDisplayName(), 
                            amount, 
                            description);
    }
}