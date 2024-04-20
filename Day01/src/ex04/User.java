import java.io.IOException;

public class User {
    static int idCounter = 0;
    private int id;
    private String name;
    private int balance;
    private TransactionsLinkedList userTransLinkedList;
    private int count;

    User(String name, int balance) throws IOException {
        if (balance < 0) {
            throw new IOException("User start balance can't be negative.");
        }
        this.name = name;
        this.balance = balance;
        this.id = idCounter++;
        this.userTransLinkedList = new TransactionsLinkedList();
        this.count = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TransactionsLinkedList getUserTransLinkedList() {
        return userTransLinkedList;
    }

    public void addUserTransLinkedList(Transaction transaction) {
        this.userTransLinkedList.addTransaction(transaction);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "id = " + id + ", name = " + name + ", " + "balanse = " + balance;
    }

    public void printUserInfo() {
        System.out.println("Info:\nid = " + id + ", name = " + name + ", " + "balanse = " + balance);
        userTransLinkedList.printList();
    }
}
