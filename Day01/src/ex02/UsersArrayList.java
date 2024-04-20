import java.io.IOException;
import java.util.Arrays;

public class UsersArrayList implements UsersList {
    private int size;
    private int inputIndex;
    private User[] userArr;

    UsersArrayList() {
        this.size = 10;
        this.inputIndex = 0;
        this.userArr = new User[size];
    }

    @Override
    public void addUser(User user) {
        User[] tempArr;
        if (this.inputIndex >= size) {
            size = size * 2;
            tempArr = new User[size];
            for (int i = 0; i < size / 2; i++) {
                tempArr[i] = this.userArr[i];
            }
            tempArr[inputIndex] = user;
            this.inputIndex++;
            this.userArr = tempArr;
        } else {
            this.userArr[inputIndex] = user;
            this.inputIndex++;
        }
    }

    @Override
    public User getUserById(int id) throws IOException {
        User user = null;

        for (int i = 0; i < inputIndex; i++) {
            if (this.userArr[i].getId() == id) {
                user = this.userArr[i];
            }
        }
        if (user == null) {
            throw new IOException("UserNotFoundException.");
        }
        return user;
    }

    @Override
    public User getUserByIndex(int index) {
        return this.userArr[index];
    }

    @Override
    public int getNumberOfUser() {
        return this.inputIndex;
    }

    @Override
    public String toString() {
        return "UsersArrayList{" +
                "size=" + size +
                ", inputIndex=" + inputIndex +
                ", userArr=" + Arrays.toString(userArr) +
                '}';
    }
}
