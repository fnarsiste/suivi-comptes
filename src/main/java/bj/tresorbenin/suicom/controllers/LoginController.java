package bj.tresorbenin.suicom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SuppressWarnings("all")
public class LoginController {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("pageTitle", "MSG.blank");
        return "index";
    }

    @GetMapping("/administration/dashboard")
    public String dashboard(Model model){
        model.addAttribute("pageTitle", "MSG.title.dashboard");
        return "dashboard";
    }
}
