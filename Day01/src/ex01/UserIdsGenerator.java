public class UserIdsGenerator {
    private static int id = 0;
    private static final UserIdsGenerator INSTANCE = new UserIdsGenerator();

    public static UserIdsGenerator getInstance() {
        return INSTANCE;
    }
    public int generateId() {
        return ++id;
    }

    UserIdsGenerator() {

    }
}
