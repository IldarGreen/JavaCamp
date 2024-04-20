import java.io.IOException;
import java.util.UUID;


public class TransactionsService {
    private static UsersArrayList usersArrayList = new UsersArrayList();

    public int addUser(String userName, int transferAmount) throws IOException {
        User user = new User(userName, transferAmount);
        int userId = user.getId();
        usersArrayList.addUser(user);

        return userId;
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

    public String getUserName(int userId) throws IOException {
        String name = null;
        try {
            name = usersArrayList.getUserById(userId).getName();
        } catch (IOException e) {
            throw new IOException("User with id = " + userId + " " + e.getMessage());
        }
        return name;
    }

    public UUID makeTransaction(int senderId, int recipientID, int transferAmount) throws IOException {
        User sender;
        User recipient;
        sender = usersArrayList.getUserById(senderId);
        recipient = usersArrayList.getUserById(recipientID);
        UUID uuid = Transaction.makeTransaction(sender, recipient, transferAmount);
        return uuid;
    }

    public Transaction[] userTransactions(int userId) {
        Transaction[] transactions;
        try {
            TransactionsLinkedList transactionsLinkedList = usersArrayList.getUserById(userId).getUserTransLinkedList();
            transactions = transactionsLinkedList.toTransactionsArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return transactions;
    }

    public void removeUserTransaction(int userId, UUID uuid) throws IOException {
            usersArrayList.getUserById(userId).getUserTransLinkedList().removeTransactionByID(uuid);
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
                    //if not found
                    if (flag) {
                        if (tas != null) {
                            transactionsLinkedList.addTransaction(tas);
                        }
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

    public Transaction getTransaction(int userId, UUID uuid) throws IOException {
        Transaction transaction;
        transaction = usersArrayList.getUserById(userId).getUserTransLinkedList().getTransactionByIDFromUser(uuid);

        return transaction;
    }
}
