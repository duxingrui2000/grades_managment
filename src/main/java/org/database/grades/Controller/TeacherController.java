package org.database.grades.Controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @RequestMapping("/main")
    public String hello() {
        return "/teacher/main";
    }

}
