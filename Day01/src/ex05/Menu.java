import java.io.IOException;
import java.sql.SQLOutput;
import java.sql.Struct;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    public static void run(String[] args) {
        if (args.length > 0) {
            Scanner scanner = new Scanner(System.in);
            TransactionsService transactionsService = new TransactionsService();

            int mode = parser(args);
            showMenu(mode);
            int userId;
            UUID uuid;
            int input = scanner.nextInt();
            int keyToExit = 7;
            if (mode == 1) {
                keyToExit = 5;
            }
            while (input != keyToExit) {
                switch (input) {
                    case (1): // Add a user
                        System.out.println("Enter a user name and a balance");
                        String userName = scanner.next();
                        int balance = scanner.nextInt();
                        try {
                            userId = transactionsService.addUser(userName, balance);
                            System.out.println("User with id = " + userId + " is added");
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case (2): // View user balances
                        System.out.println("Enter a user ID");
                        userId = scanner.nextInt();
                        try {
                            System.out.println(transactionsService.getUserName(userId) + " - " + transactionsService.viewBalance(userId));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case (3): // Perform a transfer
                        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
                        int senderId = scanner.nextInt();
                        int recipientId = scanner.nextInt();
                        int transferAmount = scanner.nextInt();
                        try {
                            transactionsService.makeTransaction(senderId, recipientId, transferAmount);
                            System.out.println("The transfer is completed");
                        } catch (IOException | IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case (4): // View all transactions for a specific user
                        System.out.println("Enter a user ID");
                        userId = scanner.nextInt();
                        Transaction[] transactions;
                        try {
                            transactions = transactionsService.userTransactions(userId);
                            User user;
                            String toFrom;
                            for (Transaction t: transactions) {
                                if (t != null) {
                                    System.out.println(getToFrom(t) + " " + t.getRecipient().getName() + "(id = " + t.getSender().getId() + ") " +
                                            t.getTransferAmount() + " with id = " + t.getUuid());
                                }
                            }
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case (5): // DEV – remove a transfer by ID
                        if (mode == 2) {
                            System.out.println("Enter a user ID and a transfer ID");
                            userId = scanner.nextInt();
                            uuid = UUID.fromString(scanner.next());
                            try {
                                Transaction t = transactionsService.getTransaction(userId, uuid);
                                transactionsService.removeUserTransaction(userId, uuid);
                                System.out.println("Transfer To " + t.getRecipient().getName() + "(id = " + t.getSender().getId() + ") "
                                        + t.getTransferAmount() + " removed");
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case (6): // DEV – check transfer validity
                        if (mode == 2) {
                            try {
                                transactions = transactionsService.checkTransactions();
                                System.out.println("Check results:");
                                for (Transaction t : transactions) {
                                    System.out.println(t.getSender().getName() + "(id = " + t.getSender().getId() +
                                            ") has an unacknowledged transfer id = " + t.getUuid() + " " + getToFrom(t) + " " +
                                            t.getRecipient().getName() + "(id = " + t.getSender().getId() + ")" + " for " + -t.getTransferAmount());
                                }
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("There is no such menu item.");
                        }
                        break;
                    default:
                        System.out.println("There is no such menu item.");
                        break;
                }
                System.out.println("---------------------------------------------------------");
                showMenu(mode);
                input = scanner.nextInt();
            }
        } else {
            System.out.println("You forgot to introduce the argument.");
        }

    }

    public static int parser(String[] args) {
        String[] parts = args[0].split("=");
        String key = parts[0]; // --profile
        String modeStr = parts[1]; // production or dev
        int modeInt = 0;
        if (modeStr.equals("production"))
            modeInt = 1;
        if (modeStr.equals("dev"))
            modeInt = 2;

        return modeInt;
    }

    public static void showMenu(int mode) {
        int num = 4;
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (mode == 2) {
            System.out.println(++num + ". DEV - remove a transfer by ID");
            System.out.println(++num + ". DEV - check transfer validity");
        }
        System.out.println(++num + ". Finish execution");
    }

    public static String getToFrom(Transaction transaction) {
        String toFrom;
        if (transaction.getTransferAmount() < 0) {
            toFrom = "To";
        } else {
            toFrom = "From";
        }

        return toFrom;
    }
}
