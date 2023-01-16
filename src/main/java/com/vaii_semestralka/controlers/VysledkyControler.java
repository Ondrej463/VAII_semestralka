package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.beans.VysledkyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VysledkyControler {
    @Autowired private VysledkyBean vysledkyBean;
    @Autowired private Session session;
    @RequestMapping(value = "/vysledky", method = RequestMethod.GET)
    public String getVysledky(Model model) {
        if (model.containsAttribute("nazov")) {
            this.vysledkyBean.init(model.getAttribute("nazov").toString());
        } else {
            this.vysledkyBean.reloadTipStatus();
        }
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("tipping", this.vysledkyBean.getTippingAllEntity());
        model.addAttribute("vysledky", this.vysledkyBean.getVysledky());
        model.addAttribute("bean", this.vysledkyBean);
        return "vysledky";
    }

    @RequestMapping(value="/prevezmiPeniaze", method = RequestMethod.POST)
    public @ResponseBody RestReponse prevezmiVyhru() {
        this.vysledkyBean.pridajPeniaze(this.vysledkyBean.getTipPrihlasenehoPouzivatela().getVysledkyEntity().getZisk());
        return new RestReponse(RestReponse.OK);
    }

    @RequestMapping(value="/vratPeniaze", method = RequestMethod.POST)
    public @ResponseBody RestReponse vratPeniaze() {
        this.vysledkyBean.pridajPeniaze(this.vysledkyBean.getTipPrihlasenehoPouzivatela().getVklad());
        return new RestReponse(RestReponse.OK);
    }

    @RequestMapping("/getMeno")
    public String edit(
            @RequestParam(name = "paName", required = false) String email,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/profile";
    }
}
