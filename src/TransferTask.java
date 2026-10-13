public class TransferTask implements Runnable {
    private final BankAccount source;
    private final BankAccount destination;
    private final double amount;

    public TransferTask(BankAccount source,
                        BankAccount destination,
                        double amount) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    @Override
    public void run() {

        boolean success = source.withdraw(amount);

        if (success) {
            destination.deposit(amount);
        }
    }
}
