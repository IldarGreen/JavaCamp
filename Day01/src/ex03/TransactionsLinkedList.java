import java.util.UUID;
import java.io.IOException;

public class TransactionsLinkedList implements TransactionsList {
    private Node head;
    private Node last;

    static class Node {
        private Transaction transaction;
        private Node next;
        private Node prev;

        Node(Transaction transaction) {
            this.transaction = transaction;
            this.next = null;
            this.prev = null;
        }
    }

    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);

        //if list empty
        if (this.head == null) {
            this.head = newNode;
            this.last = newNode;
        }
        else {
            this.last.next = newNode;
            this.last = newNode;
        }
    }

    public void printList() {
        Node currNode = this.head;

        System.out.print("TransactionsLinkedList: \n");
        
        while (currNode != null) {
            System.out.print(currNode.transaction + " ");
            currNode = currNode.next;
            System.out.println();
        }
    }

    public void removeTransactionByID(UUID uuid) throws IOException {
        // Store head node
        Node currNode = this.head;
        Node prev = null;
        //1 if the head is what we're looking for
        if (currNode != null && currNode.transaction.getId() == uuid) {
            if (this.head == this.last) {
                this.last = currNode.next;
            }
            this.head = currNode.next;// changed head
        } else {
            //2 if what we're looking for isn't in the head
            while (currNode != null && currNode.transaction.getId() != uuid) {
                prev = currNode;
                currNode = currNode.next;
            }
            // last Node
            if (currNode != null) {
                prev.next = currNode.next;
                this.last = prev;
            }
            // 3 if what we're looking for is not present
            if (currNode == null) {
                throw new IOException("TransactionNotFoundException.");
            }
        }
    }

    public Transaction getTransactionByIDFromUser(UUID uuid) throws IOException {
        Node currNode = this.head;
        while (currNode != null && currNode.transaction.getId() != uuid) {
            currNode = currNode.next;
        }
        if (currNode == null) {
            throw new IOException("TransactionNotFoundException.");
        }

        return currNode.transaction;
    }

    public void toTransactionsLinkedList(Transaction[] transactions) {
        TransactionsLinkedList transactionsLinkedList = new TransactionsLinkedList();
        for (Transaction transaction : transactions) {
            transactionsLinkedList.addTransaction(transaction);
        }
        this.head = transactionsLinkedList.head;
    }
}
