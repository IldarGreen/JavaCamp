import java.io.IOException;
import java.util.UUID;

public class Program {
    public static void main(String[] args) throws IOException {
        User user1 = new User("Bob", 1000);
        User user2 = new User("John", 5000);
        Transaction forCall = new Transaction();

        UUID uuidTran1 = Transaction.makeTransaction(user1, user2, 500);
        UUID uuidTran2 = Transaction.makeTransaction(user1, user2, 100);
        UUID uuidTran3 = Transaction.makeTransaction(user2, user1, 200);
        UUID uuidTran4 = Transaction.makeTransaction(user2, user1, 455);
//        // 1
//        user1.getUserTransLinkedList().printList();
//        user2.getUserTransLinkedList().printList();
//        // 2
//        System.out.println();
//        forCall.removeTransaction(uuidTran1);
//        user1.getUserTransLinkedList().printList();
//        user2.getUserTransLinkedList().printList();
//        // 3
//        forCall.removeTransaction(uuidTran1);
//        forCall.removeTransaction(uuidTran2);
//        forCall.removeTransaction(uuidTran3);
////        forCall.removeTransaction(uuidTran4);
//        user1.getUserTransLinkedList().printList();
//        user2.getUserTransLinkedList().printList();
//        //4 удалим транзакцию с неcуществующим UUID, броситт ошибку
        forCall.removeTransaction(UUID.randomUUID());
//        // 5 преобразуем массив транзакций в список
//        Transaction[] transactions = {
//            Transaction.getTransactionsLinkedList().getTransactionByIDFromUser(uuidTran1),
//            Transaction.getTransactionsLinkedList().getTransactionByIDFromUser(uuidTran2),
//            Transaction.getTransactionsLinkedList().getTransactionByIDFromUser(uuidTran3),
//            Transaction.getTransactionsLinkedList().getTransactionByIDFromUser(uuidTran4)
//        };
//        TransactionsLinkedList listFromArray = new TransactionsLinkedList();
//        listFromArray.toTransactionsLinkedList(transactions);
//        listFromArray.printList();
//        // 6 user transactionlist
//        user1.printUserInfo();
//        user2.printUserInfo();
    }
}