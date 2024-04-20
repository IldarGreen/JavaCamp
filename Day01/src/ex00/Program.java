public class Program {
    public static void main(String[] args) {
        try {
            User user1 = new User("John", 1000);
            User user2 = new User("Mike", 5000);
            System.out.println(user1);
            System.out.println(user2);

            Transaction transaction1 = new Transaction(user1, user2,"debits", -500);
            System.out.println(transaction1);

            System.out.println(user1);
            System.out.println(user2);
//
//            Transaction transaction2 = new Transaction(user1, user2,"OUTCOME", 500);
//            System.out.println(transaction2);
//
//            System.out.println(user1);
//            System.out.println(user2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
