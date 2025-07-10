package Bank;

import java.util.List;
import java.util.ArrayList;

public class Client {

    private String fullName;
    private List<Account> accounts;

    public Client(String name) {
        this.fullName = fullName;
        this.accounts = new ArrayList<>();
    }

    public void transfer(Account from, Account to, double amount) {
        System.out.println("Перевод " + amount + " рублей со счёта '" + from.getName() +
                "' на счёт '" + to.getName() + "'");

        from.withdraw(amount);
        to.deposit(amount);

        System.out.println("Текущий статус:");
        System.out.println("Отправитель: " + from.getInfo());
        System.out.println("Получатель: " + to.getInfo());
        System.out.println("------------");

    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public void showAccounts() {
        for (Account acc : accounts) {
            System.out.println(acc.getInfo());
        }
    }
}
