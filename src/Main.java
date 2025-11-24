public class Main {
    
    public static void main(String[] args) {
        System.out.println("            EXPENSE TRACKER             ");
        System.out.println("        Take Control of Your Money!         ");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Continue if sleep fails
        }
        
        // Use the full package name
        ui.ExpenseTrackerUI expenseApp = new ui.ExpenseTrackerUI();
        expenseApp.start();
        
        System.out.println("\nThank you for using Expense Tracker! ");
    }
}