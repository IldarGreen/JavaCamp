import java.io.IOException;
import java.util.UUID;


public class TransactionsService {
    private static UsersArrayList usersArrayList = new UsersArrayList();

    public void addUser(String userName, int transferAmount) {
        try {
            User user = new User(userName, transferAmount);
            usersArrayList.addUser(user);
        } catch (IOException e) {
            System.out.println("User with name " + userName + " can't be created, cause: " + e.getMessage());
        }
    }

    public int viewBalance(int userId) throws IOException {
        int balance = 0;

        try {
            balance = usersArrayList.getUserById(userId).getBalance();
        } catch (IOException e) {
            throw new IOException("User with id = " + userId + " " + e.getMessage());
        }
        return balance;
    }

    public UUID makeTransaction(int senderId, int recipientID, int transferAmount) {
        User sender;
        User recipient;
        UUID uuid = new UUID(0L, 0L);
        try {
            sender = usersArrayList.getUserById(senderId);
            recipient = usersArrayList.getUserById(recipientID);
            try {
                uuid = Transaction.makeTransaction(sender, recipient, transferAmount);
            } catch (IOException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("One of the users(with id " + senderId + " or " + recipientID + ") was not found.");
        }
        return uuid;
    }

    public Transaction[] userTransactions(int userId) {
        Transaction[] transactions;
        try {
            TransactionsLinkedList transactionsLinkedList = usersArrayList.getUserById(userId).getUserTransLinkedList();
            transactions = transactionsLinkedList.toTransactionsArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public void removeUserTransaction(int userId, UUID uuid) {
        try {
            usersArrayList.getUserById(userId).getUserTransLinkedList().removeTransactionByID(uuid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction[] checkTransactions() {
        Transaction[] transactions = new Transaction[0];
        TransactionsLinkedList transactionsLinkedList = new TransactionsLinkedList();
        int length = usersArrayList.getNumberOfUser();

        for (int i = 0; i < length; i++) {
            try {
                Transaction[] transactionsArray = Transaction.getTransactionsLinkedList().toTransactionsArray();
                Transaction[] tAS = userTransactions(transactionsArray[i].getSender().getId());
                Transaction[] tAR = userTransactions(transactionsArray[i].getRecipient().getId());
                boolean flag;
                for (Transaction tas : tAS) {
                    flag = true;
                    for (Transaction tar : tAR) {
                        if (tas != null & tar != null) {
                            if (tas.getId() == tar.getId()) {
                                flag = false;
                                break;
                            }
                        }
                    }
                    //если не нашли
                    if (flag && tas != null) {
                        transactionsLinkedList.addTransaction(tas);
                    }
                }
                transactions = transactionsLinkedList.toTransactionsArray();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return transactions;
    }

    public void printUserTransLinkedList(int userId) {
        try {
            usersArrayList.getUserById(userId).getUserTransLinkedList().printList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
