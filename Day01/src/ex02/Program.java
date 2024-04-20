import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            User user0 = new User("Boby0", 1000);
            User user1 = new User("Boby1", 1000);
            User user2 = new User("Boby2", 1000);
            User user3 = new User("Boby3", 1000);
            User user4 = new User("Boby4", 1000);
            User user5 = new User("Boby5", 1000);
            User user6 = new User("Boby6", 1000);
            User user7 = new User("Boby7", 1000);
            User user8 = new User("Boby8", 1000);
            User user9 = new User("Boby9", 1000);
            User user10 = new User("Boby10", 1000);

            UsersArrayList UAL1 = new UsersArrayList();

            System.out.println(UAL1.getNumberOfUser());

            UAL1.addUser(user0);
            UAL1.addUser(user1);
            UAL1.addUser(user2);
            UAL1.addUser(user3);
            UAL1.addUser(user4);
            UAL1.addUser(user5);
            UAL1.addUser(user6);
            UAL1.addUser(user7);
            UAL1.addUser(user8);
            UAL1.addUser(user9);
            UAL1.addUser(user10);

            System.out.println(UAL1.getNumberOfUser());

            System.out.println(UAL1.getUserByIndex(9));
            System.out.println(UAL1.getUserById(9));
//            System.out.println(UAL1.getUserById(20));//throw
//            System.out.println(UAL1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
