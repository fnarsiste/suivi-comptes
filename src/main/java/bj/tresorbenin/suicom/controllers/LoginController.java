package bj.tresorbenin.suicom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/administration/dashboard")
    public String dashboard(Model model){
        model.addAttribute("CONTENT_TITLE", "Tableau de bord");
        return "admin/dashboard";
    }
}
