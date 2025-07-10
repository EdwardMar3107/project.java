package Bank;

public class CheckingAccount extends Account {

    public CheckingAccount (String name, double balance) {
        super(name, balance);
    }

    @Override
    public double getFeeRate() {
        return 0.01;
    }
}
