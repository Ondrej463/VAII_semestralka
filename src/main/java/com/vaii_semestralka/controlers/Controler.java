package com.vaii_semestralka.controlers;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
class Controler {
    @Autowired private UserService service;

    @GetMapping("/informacie")
    public String getInformacie(Model model) {
        model.addAttribute("user_firstname", LoggedInUser.getActualUser().getFirst_name());
        model.addAttribute("user_lastname", LoggedInUser.getActualUser().getLast_name());
        return "informations";
    }

    @RequestMapping("/logout")
    public String logOut() {
        LoggedInUser.logOut();
        return "redirect:../X-Tipping";
    }

}

