package ui;

import models.Expense;
import models.Category;
import services.ExpenseService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerUI {
    private ExpenseService expenseService;
    private Scanner scanner;
    
    public ExpenseTrackerUI() {
        this.expenseService = new ExpenseService();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println(" Welcome to Your Personal Expense Tracker! ");
        System.out.println("Let's help you manage your money better!\n");
        
        while (true) {
            showMainMenu();
            int choice = getNumberInput("What would you like to do? ");
            
            switch (choice) {
                case 1: addExpense(); break;
                case 2: showAllExpenses(); break;
                case 3: showExpensesByCategory(); break;
                case 4: showMonthlySummary(); break;
                case 5: showSpendingBreakdown(); break;
                case 6: removeExpense(); break;
                case 7: 
                    System.out.println(" Thanks for using Expense Tracker! See you next time!");
                    return;
                default:
                    System.out.println(" Oops! Please choose a number between 1-7.");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n MAIN MENU");
        System.out.println("1. Add New Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. View Expenses by Category");
        System.out.println("4. View Monthly Summary");
        System.out.println("5. View Spending Breakdown");
        System.out.println("6. Delete an Expense");
        System.out.println("7. Exit");
    }
    
    private void addExpense() {
        System.out.println("\n ADD NEW EXPENSE");
        
        System.out.print("What did you spend money on? ");
        String description = scanner.nextLine();
        
        double amount = getNumberInput("How much did it cost? $");
        
        System.out.println("\n Spending Categories:");
        for (Category category : Category.values()) {
            System.out.println("• " + category.getDisplayName());
        }
        
        System.out.print("Which category? ");
        String categoryInput = scanner.nextLine();
        Category category = Category.findByName(categoryInput);
        
        LocalDate date = getDateInput("When did you spend this? (YYYY-MM-DD) or press Enter for today: ");
        if (date == null) {
            date = LocalDate.now();
        }
        
        expenseService.addExpense(description, amount, category, date);
        System.out.println("Great! Expense added successfully!");
    }
    
    private void showAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        displayExpensesList(expenses, "ALL YOUR EXPENSES");
    }
    
    private void showExpensesByCategory() {
        System.out.println("\n  CHOOSE A CATEGORY:");
        for (Category category : Category.values()) {
            System.out.println("• " + category.getDisplayName());
        }
        
        System.out.print("Which category would you like to see? ");
        String categoryInput = scanner.nextLine();
        Category category = Category.findByName(categoryInput);
        
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        displayExpensesList(expenses, "EXPENSES FOR: " + category.getDisplayName());
    }
    
    private void showMonthlySummary() {
        int year = getNumberInput("Which year? (e.g., 2024): ");
        int month = getNumberInput("Which month? (1-12): ");
        
        List<Expense> expenses = expenseService.getExpensesByMonth(year, month);
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        
        displayExpensesList(expenses, String.format("EXPENSES FOR %d-%02d", year, month));
        System.out.printf("Total spent this month: $%.2f\n", total);
    }
    
    private void showSpendingBreakdown() {
        System.out.println("\n YOUR SPENDING BREAKDOWN");
        double totalSpent = expenseService.getTotalSpending();
        
        if (totalSpent == 0) {
            System.out.println("No expenses yet! Start adding some expenses to see your breakdown.");
            return;
        }
        
        for (Category category : Category.values()) {
            double categoryTotal = expenseService.getTotalSpendingByCategory(category);
            double percentage = (categoryTotal / totalSpent) * 100;
            System.out.printf("• %-15s: $%-8.2f (%.1f%%)\n", 
                            category.getDisplayName(), categoryTotal, percentage);
        }
        
        System.out.println("────────────────────────────────────");
        System.out.printf("TOTAL SPENT: $%.2f\n", totalSpent);
    }
    
    private void removeExpense() {
        List<Expense> expenses = expenseService.getAllExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses to delete. Your list is empty!");
            return;
        }
        
        displayExpensesList(expenses, "SELECT EXPENSE TO DELETE");
        int index = getNumberInput("Enter the number of expense to delete: ") - 1;
        
        if (index >= 0 && index < expenses.size()) {
            expenseService.deleteExpense(index);
            System.out.println(" Expense deleted successfully!");
        } else {
            System.out.println(" Invalid number! Please try again.");
        }
    }
    
    private void displayExpensesList(List<Expense> expenses, String title) {
        System.out.println("\n--- " + title + " ---");
        if (expenses.isEmpty()) {
            System.out.println("No expenses found. Time to go shopping? ");
            return;
        }
        
        System.out.printf("#  %-12s %-15s %-10s %s\n", "Date", "Category", "Amount", "Description");
        System.out.println("─".repeat(65));
        
        for (int i = 0; i < expenses.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, expenses.get(i));
        }
        
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        System.out.println("─".repeat(65));
        System.out.printf("Total: $%.2f\n", total);
    }
    
    // Helper methods for user input
    private int getNumberInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number!");
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount!");
            }
        }
    }
    
    private LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    return null;
                }
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Please use YYYY-MM-DD format (e.g., 2024-01-15)");
            }
        }
    }
}