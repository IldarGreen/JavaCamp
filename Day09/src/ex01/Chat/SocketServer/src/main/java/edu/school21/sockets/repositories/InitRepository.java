package edu.school21.sockets.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class InitRepository {

    @Autowired
    public InitRepository(@Qualifier("dataSourceHikari") DataSource dataSource) {
        initDatabase(dataSource);
    }

    public void initDatabase(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("users_init.sql"),
                new ClassPathResource("users_init_data.sql"),
                new ClassPathResource("messages_init.sql")
        );
        populator.execute(dataSource);
    }
}
