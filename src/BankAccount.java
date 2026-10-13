
public class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }//

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance = balance + amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance = balance - amount;
        return true;
    }

}

