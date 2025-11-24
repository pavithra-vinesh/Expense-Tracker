package services;

import models.Expense;
import models.Category;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {
    private List<Expense> expenses;
    
    public ExpenseService() {
        this.expenses = FileService.loadExpenses();
    }
    
    public void addExpense(String description, double amount, Category category, LocalDate date) {
        Expense expense = new Expense(description, amount, category, date);
        expenses.add(expense);
        FileService.saveExpenses(expenses);
    }
    
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }
    
    public List<Expense> getExpensesByCategory(Category category) {
        List<Expense> result = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory() == category) {
                result.add(expense);
            }
        }
        return result;
    }
    
    public List<Expense> getExpensesByMonth(int year, int month) {
        List<Expense> result = new ArrayList<>();
        for (Expense expense : expenses) {
            LocalDate date = expense.getDate();
            if (date.getYear() == year && date.getMonthValue() == month) {
                result.add(expense);
            }
        }
        return result;
    }
    
    public double getTotalSpending() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }
    
    public double getTotalSpendingByCategory(Category category) {
        double total = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory() == category) {
                total += expense.getAmount();
            }
        }
        return total;
    }
    
    public void deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            FileService.saveExpenses(expenses);
        }
    }
}