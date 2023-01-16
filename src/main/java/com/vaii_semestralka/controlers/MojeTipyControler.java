package com.vaii_semestralka.controlers;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.beans.MojeTipyBean;
import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("bean", this.mojeTipyBean);
        model.addAttribute("mojeTipyBean", mojeTipyBean);
        return "moje_tipy";
    }

    @PostMapping("/sendTip")
    public String postMojeTipy() {
        return "";
    }

}
