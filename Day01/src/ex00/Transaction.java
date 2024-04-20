import java.io.IOException;
import java.util.UUID;

public class Transaction {

    UUID id;
    User recipient;
    User sender;
    String transferCategory; //(OUTCOME, INCOME)
    int transferAmount;

    Transaction(User sender, User recipient, String transferCategory, int transferAmount) throws IOException {
        if (transferCategory.equals("debits") & transferAmount > 0) {
            throw new IOException("Debit transaction amount should be negative.");
        }
        if (transferCategory.equals("credits") & transferAmount < 0) {
            throw new IOException("Credit transaction amount should be positive.");
        }
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
        this.id = UUID.randomUUID();

        sender.balance -= transferAmount;
        recipient.balance += transferAmount;
    }

    @Override
    public String toString() {
            return  sender.name + " -> " + recipient.name + ", " + transferAmount +
                ", " + transferCategory + ", " + id;
    }
}
