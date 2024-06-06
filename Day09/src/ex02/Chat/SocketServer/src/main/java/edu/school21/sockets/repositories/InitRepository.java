package edu.school21.sockets.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class InitRepository {
    private DataSource dataSource;

    @Autowired
    public InitRepository(@Qualifier("dataSourceHikari") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initDatabase(String pathInitSql) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource(pathInitSql));
        populator.execute(dataSource);
    }
}
