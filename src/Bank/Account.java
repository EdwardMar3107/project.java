package Bank;

public abstract class Account {

    private String name;
    private double balance;

    public Account(String name, double balance) {
        setName(name);
        setBalance(balance);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public abstract double getFeeRate();

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Ошибка! Пополнение не может быть отрицательным");
            return;
        }
        balance += amount;
        System.out.println("Пополнение на: " + amount + ". На счету осталось: " + balance);

    }

    public void withdraw(double amount) {
        double total = amount + (amount * getFeeRate());
        if (total > balance) {
            System.out.println("Ошибка! Недостаточно средств на счёте!");
            return;
        }
        balance -= total;
        System.out.println("Снятие: " + amount + " рублей. Комиссия: " + (getFeeRate() * 100) + "%. На счету осталось: " + balance);

    }

    public String getInfo() {
        return "Счёт: " + name + " | Баланс: " + balance + " | Комиссия: " + (getFeeRate() * 100) + "%";
    }
}
