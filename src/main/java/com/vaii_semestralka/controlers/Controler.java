package com.vaii_semestralka.controlers;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.beans.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
class Controler {
    @Autowired private Session session;
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

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String getHeader(Model model) {
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        return "header";
    }

    @RequestMapping(value = "/navBar", method = RequestMethod.GET)
    public String getNavBar(Model model) {
        model.addAttribute("jeAdmin", this.session.jePrihlasenyPouzivatelAdmin());
        return "navigation_bar";
    }
}

