package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.PodujatieBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PodujatieControler {
    @Autowired private PodujatieBean podujatieBean;

    @RequestMapping(value="/podujatie", method = RequestMethod.GET)
    public String getPodujatie(Model model) {
        if (model.containsAttribute("nazov")) {
            this.podujatieBean.init(model.getAttribute("nazov").toString());
        }

        model.addAttribute("tipping", this.podujatieBean.getTippingAllEntity());
        model.addAttribute("bean", this.podujatieBean);
        model.addAttribute("koeficienty", this.podujatieBean.getTippingAllEntity().getKoeficientsInOrder());
        return "podujatie";
    }

    @RequestMapping(value="/tipuj", method = RequestMethod.GET)
    public String tipuj(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("name", this.podujatieBean.getTippingAllEntity().getName());
        return "redirect:/tip";
    }
}
