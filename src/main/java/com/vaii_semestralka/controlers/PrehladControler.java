package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.PrehladBean;
import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class PrehladControler {
    @Autowired private PrehladBean prehladBean;
    @Autowired private Session session;

    @GetMapping("/prehlad")
    public String getPrehlad(Model model)
    {
        if (!session.checkUrlAuthorisation()) {
            return "redirect:/";
        }
        model.addAttribute("tippings", prehladBean.getTippings());
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("jeAdmin", session.jePrihlasenyPouzivatelAdmin());
        model.addAttribute("prehladBean", prehladBean);
        model.addAttribute("jeSprava", model.containsAttribute("pridanyTipSprava"));
        return "prehlad";
    }
    @RequestMapping("/edit")
    public String edit(
            @ModelAttribute TippingAllEntity tippingAllEntity,
            @RequestParam(name = "paName", required = false) String name,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("name", name);
        return "redirect:/spravcaPodujati";
    }

    @RequestMapping("/getVysledky")
    public String getVysledky(
            @RequestParam(name = "paName", required = false) String name,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("nazov", name);
        return "redirect:/vysledky";
    }

    @RequestMapping("/viewPodujatie")
    public String viewPodujatie(
            @RequestParam(name = "paName", required = false) String name,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("nazov", name);
        return "redirect:/podujatie";
    }

    @RequestMapping("/novy")
    public String novy(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("name", null);
        return "redirect:/spravcaPodujati";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody RestReponse delete(@RequestBody String nazov) {
        return new RestReponse(RestReponse.OK, prehladBean.delete(nazov));
    }

    @RequestMapping("/tipuj")
    public String tip(
            @ModelAttribute() TippingAllEntity tippingAllEntity,
            @RequestParam(name = "paName", required = false) String name,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("paName", name);
        return "redirect:/tip";
    }
}
