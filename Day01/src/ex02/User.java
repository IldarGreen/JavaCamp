import java.io.IOException;

public class User{
    static int idCounter = 0;
    private int id;
    private String name;
    private int balance;

    User(String name, int balance) throws IOException {
        if (balance < 0) {
            throw new IOException("The user balance can't be negative.");
        }
        this.name = name;
        this.balance = balance;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id = " + id  + ", name = " + name + ", " + "balanse = " + balance;
    }
}
