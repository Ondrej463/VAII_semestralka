package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.beans.TipBean;
import com.vaii_semestralka.tip.TipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TipControler {
    @Autowired private Session session;

    @Autowired private TipBean tipBean;

    @GetMapping("/tip")
    public String getTip(Model model) {
        if (!session.checkUrlAuthorisation()) {
            return "redirect:/";
        }
        if (model.containsAttribute("name")) {
            this.tipBean.init(model.getAttribute("name").toString());
        }
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("tip", tipBean.getTipEntity());
        model.addAttribute("tippingAllEntity", tipBean.getTippingAllEntity());
        if (!model.containsAttribute("vkladErrorMessage")) {
            model.addAttribute("vkladErrorMessage", "");
            model.addAttribute("vkladClass", "");
        }
        return "tip";
    }
    @PostMapping("/confirmTip")
    public String saveTip(
            RedirectAttributes redirectAttributes,
            @ModelAttribute TipEntity tipEntity
    ) {
        String sprava = this.tipBean.validate(tipEntity);
        if (!sprava.equals("")) {
            this.tipBean.setTipEntity(tipEntity);
            redirectAttributes.addFlashAttribute("vkladErrorMessage", sprava);
            redirectAttributes.addFlashAttribute("vkladClass", "error");
            return "redirect:/tip";
        }
        tipBean.save(tipEntity);
        return "redirect:/prehlad";
    }

    @RequestMapping(value="/confirmTip", params = "delete", method = RequestMethod.POST)
    public String deleteTip() {
        tipBean.delete();
        return "redirect:/prehlad";
    }

    @RequestMapping(value="pocetCislic", method = RequestMethod.GET)
    public @ResponseBody RestReponse getPocetCislic() {
        return new RestReponse(RestReponse.OK, tipBean.getTippingAllEntity().getDruh().getPocet_cislic() + "");
    }
}
