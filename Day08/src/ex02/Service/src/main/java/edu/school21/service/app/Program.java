package edu.school21.service.app;

import edu.school21.service.config.ApplicationConfig;
import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.services.UsersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Program {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        UsersService usersService = applicationContext.getBean("usersService", (UsersService.class));

        UsersRepository usersRepository = applicationContext.getBean("usersRepositoryJdbc", UsersRepository.class);
        System.out.println("Fined all by plain JDBC");
        System.out.println(usersRepository.findAll());

        System.out.println("Fined all by  JDBC Template");
        usersRepository = applicationContext.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findAll());

        System.out.println("Add user with email = FindMe@mail.en .");
        usersRepository.save(new User("FindMe@mail.en", "222dfgad222"));
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

        usersService.signUp("SignUpMail@gmail.com");
        usersService.signUp("");
        System.out.println(usersRepository.findAll());

        applicationContext.close();
    }
}
