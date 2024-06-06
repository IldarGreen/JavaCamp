package edu.school21.service.repositories;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class InitRepository {

    public InitRepository(DataSource dataSource) {
        initDatabase(dataSource);
    }

    public void initDatabase(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("data.sql"));
        populator.execute(dataSource);
    }
}
