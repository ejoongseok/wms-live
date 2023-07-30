package com.ejoongseok.wmslive.common;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTest {
    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        if (RestAssured.UNDEFINED_PORT == RestAssured.port) {
            RestAssured.port = port;
            databaseCleaner.afterPropertiesSet();
        }
        databaseCleaner.execute();
    }
}
