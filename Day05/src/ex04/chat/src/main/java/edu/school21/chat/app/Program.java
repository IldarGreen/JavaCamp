package edu.school21.chat.app;


import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.util.List;


public class Program {
    public static void main(String[] args) {
        JDBCDataSource dataSource = new JDBCDataSource();
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource.getDataSource());

        List<User> userList = usersRepository.findAll(1, 3);
        System.out.println("PAGE = 1 SIZE = 3");
        userList.forEach(System.out::println);

        userList = usersRepository.findAll(3, 3);
        System.out.println("PAGE = 2 SIZE = 3");
        userList.forEach(System.out::println);

        userList = usersRepository.findAll(1, 3);
        System.out.println("PAGE = 3 SIZE = 3");
        userList.forEach(System.out::println);
    }

}
