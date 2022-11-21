package com.vaii_semestralka.controlers;
import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.beans.SpravcaPodujatiBean;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class SpravcaPodujatiControler {
    @Autowired private SpravcaPodujatiBean spravcaPodujatiBean;
    @Autowired private Session session;

    @GetMapping("/spravcaPodujati")
    public String getSpravcaPodujati(Model model) {
        if (!session.checkUrlAuthorisation()) {
            return "redirect:/";
        }
        spravcaPodujatiBean.init(model);
        model.addAttribute("tipping",spravcaPodujatiBean.getTipping());
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("buttonText", spravcaPodujatiBean.getButtonText());
        model.addAttribute("jeNovy", spravcaPodujatiBean.jeNovy());
        return "spravca_podujati";
    }
    @PostMapping("/save")
    public String save(
            @ModelAttribute TippingAllEntity tippingAllEntity
    ) {
        spravcaPodujatiBean.save(tippingAllEntity);
        return "redirect:/prehlad";
    }

}
