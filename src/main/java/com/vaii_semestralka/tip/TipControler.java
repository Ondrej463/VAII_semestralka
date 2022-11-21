package com.vaii_semestralka.tip;

import com.vaii_semestralka.beans.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TipControler {
    @Autowired private Session session;

    @Autowired private TipBean tipBean;

    @GetMapping("/tip")
    public String getTip(Model model) {
        if (!session.checkUrlAuthorisation()) {
            return "redirect:/";
        }
        tipBean.setup(model);
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("tip", tipBean.getTip());
        return "tip";
    }
    @RequestMapping("confirmTip")
    public String saveTip(
            @ModelAttribute TipEntity tipEntity
    ) {
        tipBean.save(tipEntity);
        return "redirect:/prehlad";
    }
}
