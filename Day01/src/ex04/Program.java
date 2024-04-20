import java.io.IOException;
import java.util.UUID;

public class Program {
    public static void main(String[] args) throws IOException {
        TransactionsService transactionsService = new TransactionsService();

        transactionsService.addUser("Bob", 1000);
        transactionsService.addUser("John", 5000);
//        transactionsService.addUser("John", -5000);
        // 1
        System.out.println("Balance of user with id = 0 is: " + transactionsService.viewBalance(0));
        System.out.println("Balance of user with id = 1 is: " + transactionsService.viewBalance(1));
//        // выведем баланс несуществуюшего юзера, бросит ошибку
//        try {
//            System.out.println(transactionsService.viewBalance(100));
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        // 2
        UUID uuidTran1 = transactionsService.makeTransaction(0, 1, 500);
        UUID uuidTran2 = transactionsService.makeTransaction(0, 1, 100);
        UUID uuidTran3 = transactionsService.makeTransaction(1, 0, 350);
        UUID uuidTran4 = transactionsService.makeTransaction(1, 0, 400);
//        // 3
        transactionsService.printUserTransLinkedList(0);
        transactionsService.printUserTransLinkedList(1);
//        // 4
        transactionsService.removeUserTransaction(0, uuidTran1);
//        transactionsService.removeUserTransaction(1, uuidTran1);
        transactionsService.printUserTransLinkedList(0);
        transactionsService.printUserTransLinkedList(1);
        // удалим транзакцию с неcуществующим UUID, броситт ошибку
//        transactionsService.removeUserTransaction(0, UUID.randomUUID());
//        // 5
        transactionsService.removeUserTransaction(0, uuidTran1);
        transactionsService.removeUserTransaction(1, uuidTran4);
        transactionsService.printUserTransLinkedList(0);
        transactionsService.printUserTransLinkedList(1);
        System.out.println("unpairedTransactions:");
        Transaction[] unpairedTransactions = transactionsService.checkTransactions();
        for (Transaction uT : unpairedTransactions) {
            System.out.println(uT);
        }
    }
}
