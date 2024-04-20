import java.io.IOException;

public class User {
    int id;
    String name;
    int balance;

    User(String name, int balance) throws IOException {
        if (balance < 0) {
            throw new IOException("The user balance can't be negative.");
        }
        this.name = name;
        this.balance = balance;
        this.id = UserIdsGenerator.getInstance().generateId();
    }

    @Override
    public String toString() {
        return "id = " + id  + ", name = " + name + ", " + "balanse = " + balance;
    }
}
