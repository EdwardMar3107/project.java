package Employee;

public class PartTimeEmployee extends Employee {

    private int hoursWorked;
    private double ratePerHour;

    public PartTimeEmployee (String name, int hoursWorked, double ratePerHour) {
        super(name);
        this.hoursWorked = hoursWorked;
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * ratePerHour;
    }
}
