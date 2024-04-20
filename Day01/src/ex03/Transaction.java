import java.io.IOException;
import java.util.UUID;


public class Transaction {
    private UUID uuid;
    private User recipient;
    private User sender;
    private String transferCategory; //(OUTCOME, INCOME)
    private int transferAmount;
    private static TransactionsLinkedList transactionsLinkedList;

    public Transaction() {
    }

    Transaction(User sender, User recipient, String transferCategory, int transferAmount, UUID uuid) {
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
        this.uuid = uuid;

        sender.setBalance(sender.getBalance() + transferAmount);

        if (transactionsLinkedList == null) {
            transactionsLinkedList = new TransactionsLinkedList();
        }
    }

    public static UUID makeTransaction(User sender, User recipient, int transferAmount) throws IOException {
        if (transferAmount < 0) {
            throw new IOException("Transaction amount should be positive.");
        }
        if (sender.getBalance() - transferAmount < 0) {
            throw new IOException("Sander doesn't have enough money for this transaction.");
        }
        UUID uuid = UUID.randomUUID();
        Transaction transactionSender = new Transaction(sender, recipient, "OUTCOME", -transferAmount, uuid);
        Transaction transactionRecipient = new Transaction(recipient, sender, "INCOME", transferAmount, uuid);
        transactionsLinkedList.addTransaction(transactionSender);
        transactionsLinkedList.addTransaction(transactionRecipient);

        sender.addUserTransLinkedList(transactionSender);
        recipient.addUserTransLinkedList(transactionRecipient);

        return uuid;
    }

    public UUID getId() {
        return uuid;
    }

    public void removeTransaction(UUID uuid) throws IOException {
        transactionsLinkedList.getTransactionByIDFromUser(uuid).getSender().getUserTransLinkedList().removeTransactionByID(uuid);
        transactionsLinkedList.getTransactionByIDFromUser(uuid).getRecipient().getUserTransLinkedList().removeTransactionByID(uuid);
    }

    public static TransactionsLinkedList getTransactionsLinkedList() {
        return transactionsLinkedList;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    @Override
    public String toString() {
            return  sender.getName() + " -> " + recipient.getName() + ", " + transferAmount +
                ", " + transferCategory + ", " + uuid;
    }
}
