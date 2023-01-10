package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.beans.TipBean;
import com.vaii_semestralka.tip.TipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TipControler {
    @Autowired private Session session;

    @Autowired private TipBean tipBean;

    @GetMapping("/tip")
    public String getTip(Model model) {
        if (!session.checkUrlAuthorisation()) {
            return "redirect:/";
        }
        String nameOfEvent = model.getAttribute("paName").toString();
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("tip", tipBean.getTip(nameOfEvent));
        model.addAttribute("tippingAllEntity", tipBean.getTippingAllEntity(nameOfEvent));
        return "tip";
    }
    @RequestMapping(value="/confirmTip", params = "send", method = RequestMethod.POST)
    public String saveTip(
            @ModelAttribute TipEntity tipEntity
    ) {
        tipBean.save(tipEntity);
        return "redirect:/prehlad";
    }

    @RequestMapping(value="/confirmTip", params = "delete", method = RequestMethod.POST)
    public String deleteTip() {
        tipBean.delete();
        return "redirect:/prehlad";
    }
}
