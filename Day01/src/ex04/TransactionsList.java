import java.io.IOException;
import java.util.UUID;

public interface TransactionsList {
    public void addTransaction(Transaction transaction);

    public void removeTransactionByID(UUID id) throws IOException;

    public void toTransactionsLinkedList(Transaction[] transactions);
}
