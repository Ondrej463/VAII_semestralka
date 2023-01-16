package com.vaii_semestralka.controlers;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileControler {
     @Autowired private UserService service;

    @GetMapping("/profile")
    public String getProfile(Model model) {
        if (model.containsAttribute("email")) {
            model.addAttribute("user", this.service.getByEmail(model.getAttribute("email").toString()));
        } else {
            model.addAttribute("user", LoggedInUser.getActualUser());
        }
        model.addAttribute("user_firstname", LoggedInUser.getActualUser().getFirst_name());
        model.addAttribute("user_lastname", LoggedInUser.getActualUser().getLast_name());
        return "profile";
    }
}
