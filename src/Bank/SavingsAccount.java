package Bank;

public class SavingsAccount extends Account {

    private int attempts;

    public SavingsAccount (String name, double balance, int attempts) {
        super(name, balance);
        this.attempts = attempts;
    }

    @Override
    public double getFeeRate() {
        return 0.005;
    }

    @Override
    public void withdraw(double amount) {
        if (attempts >= 3) {
            System.out.println("Ошибка! Лимит вывода в месяц закончился!");
            return;
        }
        attempts++;
        super.withdraw(amount);
    }
}
