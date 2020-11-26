package org.database.grades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class GradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradeApplication.class, args);
        System.out.println("http://localhost:8080/");
    }

}
