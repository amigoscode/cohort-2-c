package com.amigoscode.chohort2.carRental;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startable;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTestContainerWithDB extends AbstractTestContainer {

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("unit-test")
                    .withUsername("root")
                    .withPassword("123");

    protected static DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }
    private static final Map<String, Supplier<Object>> dynamicSourceProperties = new HashMap<>(){{
        put("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        put("spring.datasource.username", postgreSQLContainer::getUsername);
        put("spring.datasource.password", postgreSQLContainer::getPassword);
    }
    };

    public AbstractTestContainerWithDB() {
        this(new HashMap<>(), new ArrayList<>());
    }

    public AbstractTestContainerWithDB(Map<String, Supplier<Object>> sources, List<Startable> services) {

        super(new HashMap<>() {{
            putAll(dynamicSourceProperties);
            putAll(sources);
        }}, new ArrayList<>() {{
            add(postgreSQLContainer);
            addAll(services);
        }});
    }
}
