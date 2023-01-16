package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.DruhBean;
import com.vaii_semestralka.druh.DruhEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DruhControler {
    @Autowired private DruhBean druhBean;

    @GetMapping("/novyDruh")
    public String novyDruh(Model model) {
        if (!model.containsAttribute("newDruh")) {
            model.addAttribute("newDruh", new DruhEntity());
        }
        model.addAttribute("nazovClass", this.druhBean.getNazovClass());
        model.addAttribute("nazovErrorMessage", this.druhBean.getNazovErrorMessage());
        model.addAttribute("zaciatokOptions", this.druhBean.getZaciatokOptions());
        model.addAttribute("koniecOptions", this.druhBean.getKoniecOptions());
        return "druh_new";
    }

    @PostMapping("/saveDruh")
    public String saveDruh(RedirectAttributes redirectAttributes, @ModelAttribute DruhEntity druh) {
        this.druhBean.validate(druh);
        if (this.druhBean.getNazovErrorMessage().equals("")) {
            this.druhBean.save(druh);
            this.druhBean.setNazovClass(null);
            return "redirect:/druhyAll";
        }
        redirectAttributes.addFlashAttribute("newDruh", druh);
        return "redirect:/novyDruh";
    }
}
