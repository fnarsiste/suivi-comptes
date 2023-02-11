package bj.tresorbenin.suicom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/administration/dashboard")
    public String dashboard(){
        return "admin/dashboard";
    }
}
