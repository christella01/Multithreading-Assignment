java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

    public class Main {

        public static void main(String[] args) throws InterruptedException {

            BankAccount[] accounts = new BankAccount[10];

            for (int i = 0; i < accounts.length; i++) {
                accounts[i] = new BankAccount(10000);
            }

            double initialTotal = 0;

            for (BankAccount account : accounts) {
                initialTotal += account.getBalance();
            }

            System.out.println("Initial total: $" + initialTotal);

            ExecutorService pool = Executors.newFixedThreadPool(10);

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 100000; i++) {

                int sourceIndex =
                        (int) (Math.random() * accounts.length);

                int destinationIndex =
                        (int) (Math.random() * accounts.length);

                while (sourceIndex == destinationIndex) {
                    destinationIndex =
                            (int) (Math.random() * accounts.length);
                }

                double amount = 1 + Math.random() * 100;

                TransferTask task = new TransferTask(
                        accounts[sourceIndex],
                        accounts[destinationIndex],
                        amount
                );

                pool.execute(task);
            }

            pool.shutdown();

            boolean finished = pool.awaitTermination(
                    5, TimeUnit.MINUTES
            );

            if (!finished) {
                pool.shutdownNow();
                System.out.println("Transfers did not finish in time.");
                return;
            }

            long endTime = System.currentTimeMillis();

            double finalTotal = 0;

            for (BankAccount account : accounts) {
                finalTotal += account.getBalance();
            }

            System.out.println("Final total: $" + finalTotal);
            System.out.println("Execution time: "
                    + (endTime - startTime) + " milliseconds");

            for (int i = 0; i < accounts.length; i++) {
                System.out.println("Account " + (i + 1)
                        + " balance: $" + accounts[i].getBalance());
            }

            if (Math.abs(initialTotal - finalTotal) < 0.01) {
                System.out.println("Total balance is preserved.");
            } else {
                System.out.println("WARNING: Total balance changed!");
            }
        }
    }