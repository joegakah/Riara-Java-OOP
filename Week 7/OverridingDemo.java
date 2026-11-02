// This program demonstrates Method Overriding and the 'super' keyword.

// ============================================================
// SUPERCLASS
// ============================================================
class Employee {
    // Protected fields: visible in this class, the package, and every subclass.
    protected String name;
    protected double baseSalary;

    // Constructor
    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // Method to calculate salary.
    public double calculateSalary() {
        return baseSalary;
    }

    // Method to display info.
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Base Salary: KSh " + baseSalary);
    }
}

// ============================================================
// SUBCLASS
// ============================================================
class Manager extends Employee {
    // A field unique to Manager.
    private double bonus;

    // Constructor
    public Manager(String name, double baseSalary, double bonus) {
        // Call the Employee constructor.
        super(name, baseSalary);
        // Set the Manager-specific field.
        this.bonus = bonus;
    }

    // OVERRIDING the calculateSalary method.
    // We use the @Override annotation to tell the compiler we are overriding.
    @Override
    public double calculateSalary() {
        // Use 'super' to call the parent's version of the method.
        double base = super.calculateSalary();

        // Add the bonus to the base salary.
        return base + bonus;
    }

    // OVERRIDING the displayInfo method.
    @Override
    public void displayInfo() {
        // Call the parent's displayInfo first.
        super.displayInfo();

        // Then add Manager-specific information.
        System.out.println("Bonus: KSh " + bonus);
        System.out.println("Total Salary: KSh " + calculateSalary());
    }

    // Task 3: proves that 'name' (protected in Employee) is directly accessible.
    public void printNameDirectly() {
        // This works because 'name' is protected in Employee!
        System.out.println("Direct access to name: " + name);
    }
}

// ============================================================
// MAIN CLASS
// ============================================================
public class OverridingDemo {
    public static void main(String[] args) {

        // Create a regular Employee.
        Employee emp = new Employee("Alice", 50000);
        System.out.println("=== EMPLOYEE ===");
        emp.displayInfo();
        System.out.println("Calculated Salary: " + emp.calculateSalary());

        System.out.println();

        // Create a Manager.
        Manager mgr = new Manager("Bob", 80000, 20000);
        System.out.println("=== MANAGER ===");
        mgr.displayInfo();
        System.out.println("Calculated Salary: " + mgr.calculateSalary());

        System.out.println();

        // Task 3: direct access to the protected field from the subclass.
        mgr.printNameDirectly();
    }
}
