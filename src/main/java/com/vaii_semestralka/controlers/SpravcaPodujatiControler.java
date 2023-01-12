package com.vaii_semestralka.controlers;
import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.beans.SpravcaPodujatiBean;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.koeficienty.Koeficient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        model.addAttribute("suKoeficienty", spravcaPodujatiBean.suKoeficienty());
        model.addAttribute("koeficients", spravcaPodujatiBean.getKoeficientList().getKoeficients());
        return "spravca_podujati";
    }
    @PostMapping("/save")
    public String save(
            @ModelAttribute TippingAllEntity tippingAllEntity
    ) {
        spravcaPodujatiBean.save(tippingAllEntity);
        return "redirect:/prehlad";
    }
    @RequestMapping(value = "/saveKoeficient", method = RequestMethod.POST)
    public @ResponseBody RestReponse saveCoeficients(@RequestBody List<Koeficient> koeficients) {
        this.spravcaPodujatiBean.getKoeficientList().setKoeficients(koeficients);
        this.spravcaPodujatiBean.validateKoefList();
        return new RestReponse(this.spravcaPodujatiBean.getKoefErrorMessage());
    }
    @RequestMapping(value = "/addKoeficient", method = RequestMethod.POST)
    public @ResponseBody RestReponse addKoeficient(@RequestBody List<Koeficient> koeficients) {

        if (!koeficients.isEmpty()) {
            this.spravcaPodujatiBean.getKoeficientList().setKoeficients(koeficients);

        }
        this.spravcaPodujatiBean.addNewKoef();
        return new RestReponse(RestReponse.OK);
    }
    @RequestMapping(value = "/getKoeficients", method = RequestMethod.GET)
    public @ResponseBody RestReponse getKoeficients() {
        return new RestReponse(RestReponse.OK, this.spravcaPodujatiBean.getKoeficientList().getKoeficients());
    }
    @RequestMapping(value = "/removeKoeficient", method = RequestMethod.POST)
    public @ResponseBody RestReponse removeKoeficient() {
        this.spravcaPodujatiBean.getKoeficientList().removeKoeficient();
        return new RestReponse(RestReponse.OK);
    }

    @RequestMapping(value = "/resetKoeficient", method = RequestMethod.POST)
    public @ResponseBody RestReponse resetKoeficient() {
        this.spravcaPodujatiBean.resetKoeficients();
        return new RestReponse(RestReponse.OK);
    }

}
