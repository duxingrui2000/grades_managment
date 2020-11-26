package org.database.grades.Controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@Secured("ROLE_student")
public class StudentController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello student";
    }
}
