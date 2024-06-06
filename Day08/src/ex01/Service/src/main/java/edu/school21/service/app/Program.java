package edu.school21.service.app;

import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        UsersRepository usersRepository = applicationContext.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println("Fined all by plain JDBC");
        System.out.println(usersRepository.findAll());

        System.out.println("Fined all by  JDBC Template");
        usersRepository = applicationContext.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findAll());

        System.out.println("Add user with email = FindMe@mail.en .");
        usersRepository.save(new User("FindMe@mail.en"));
        System.out.println("Find by email using Template:");
        System.out.println(usersRepository.findByEmail("FindMe@mail.en"));
        System.out.println("Update user.");
        User user = usersRepository.findByEmail("FindMe@mail.en").get();
        user.setEmail("FindMeUpdated@mail.com");
        usersRepository.update(user);
        System.out.println("Check updated user:");
        System.out.println(usersRepository.findByEmail("FindMeUpdated@mail.com"));
        System.out.println("Delete user with id = 1");
        usersRepository.delete(1L);
        System.out.println(usersRepository.findAll());
    }
}
