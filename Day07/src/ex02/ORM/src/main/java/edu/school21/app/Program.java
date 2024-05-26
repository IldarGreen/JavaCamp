package edu.school21.app;


import edu.school21.models.Car;
import edu.school21.models.User;
import edu.school21.orm.OrmManager;
import edu.school21.repositories.*;


public class Program {
    public static void main(String[] args) {
        JDBCDataSource dataSource = new JDBCDataSource();

        OrmManager ormManager = new OrmManager(dataSource.getDataSource());
        ormManager.createTable();

        User user1 = new User("John", "Doe", 27);
        ormManager.save(user1);

        User user2 = new User("Jinny", "Doe", 25);
        ormManager.save(user2);

        Car car1 = new Car("Corolla", "Toyota", 10000);
        ormManager.save(car1);

        Car car2 = new Car("Civic", "Honda", 10500);
        ormManager.save(car2);

        User userToFined = ormManager.findById(1L, User.class);
        System.out.println(userToFined);

        User userToUpdate = new User(1L, "John", "Doe", 3500);
        ormManager.update(userToUpdate);

        userToFined = ormManager.findById(1L, User.class);
        System.out.println(userToFined);
    }
}

