import java.io.IOException;

public interface UsersList {
    public void addUser(User user);
    public User getUserById(int id) throws IOException;
    public User getUserByIndex(int index);
    public int getNumberOfUser();
}
