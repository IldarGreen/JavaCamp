import java.io.IOException;

public class User{
    static int idCounter = 0;
    int id;
    String name;
    int balance;

    User(String name, int balance) throws IOException {
        if (balance < 0) {
            throw new IOException("The user balance can't be negative.");
        }
        this.name = name;
        this.balance = balance;
        this.id = ++idCounter;
    }

    @Override
    public String toString() {
        return "id = " + id  + ", name = " + name + ", " + "balanse = " + balance;
    }
}
