package org.database.grades.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login/error")
    public String toLoginFailurePage(Model msg) {
        msg.addAttribute("msg", "用户名或密码错误，请重试！");
        return "index";
    }
}
