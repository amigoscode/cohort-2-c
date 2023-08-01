package com.amigoscode.chohort2.carRental;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startable;
import org.testcontainers.lifecycle.Startables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTestContainer {

    public AbstractTestContainer(Map<String, Supplier<Object>> sources, List<Startable> services) {
        propertySources = sources;
        Startables.deepStart(services).join();
    }

    private static Map<String, Supplier<Object>> propertySources = new HashMap<>();

    @DynamicPropertySource
    private static void registerDynamicSources(@NotNull DynamicPropertyRegistry registry) {
        propertySources.forEach(registry::add);
    }

}
