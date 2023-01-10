package com.vaii_semestralka.controlers;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.beans.MojeTipyBean;
import com.vaii_semestralka.beans.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MojeTipyControler {

    @Autowired private MojeTipyBean mojeTipyBean;
    @Autowired private Session session;

    @GetMapping("/mojeTipy")
    public String getMojeTipy(Model model) {
        if (!session.checkUrlAuthorisation()) {
            return "redirect:/";
        }
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("tippings", mojeTipyBean.getAllUserTippings());
        model.addAttribute("mojeTipyBean", mojeTipyBean);
        return "moje_tipy";
    }

    @PostMapping("/sendTip")
    public String postMojeTipy() {
        return "";
    }

}
