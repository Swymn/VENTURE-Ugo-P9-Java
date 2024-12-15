package fr.swynn.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendConfig {
    
    // <DATABASE_URL></DATABASE_URL>
    // <DATABASE_USERNAME></DATABASE_USERNAME>
    // <DATABASE_PASSWORD>rootroot</DATABASE_PASSWORD>
    private static final String DATABASE_URL = "jdbc:postgresql://127.0.0.1:5432/medilab";
    private static final String DATABASE_USERNAME = "postgres";
    private static final String DATABASE_PASSWORD = "rootroot";

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
            .create()
            .driverClassName("org.postgresql.Driver")
            .url(DATABASE_URL)
            .username(DATABASE_USERNAME)
            .password(DATABASE_PASSWORD)
            .build();
    }
}
