package com.vaii_semestralka.controlers;
import com.vaii_semestralka.beans.Session;
import com.vaii_semestralka.beans.SpravcaPodujatiBean;
import com.vaii_semestralka.druh.DruhDatum;
import com.vaii_semestralka.druh.DruhEntity;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.koeficienty.Koeficient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
        if (!model.containsAttribute("tipping")) {
            model.addAttribute("tipping",spravcaPodujatiBean.getTipping());
        }
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());
        model.addAttribute("buttonText", spravcaPodujatiBean.getButtonText());
        model.addAttribute("suKoeficienty", spravcaPodujatiBean.suKoeficienty());
        model.addAttribute("koeficients", spravcaPodujatiBean.getKoeficientList().getKoeficients());
        model.addAttribute("druhy", spravcaPodujatiBean.getDruhy());
        model.addAttribute("bean", this.spravcaPodujatiBean);
        return "spravca_podujati";
    }
    @PostMapping("/save")
    public String save(
            RedirectAttributes redirectAttributes,
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
        String status = "allow";
        if (this.spravcaPodujatiBean.disableKoeficienty()) {
            status = "disable";
        }
        return new RestReponse(status, this.spravcaPodujatiBean.getKoeficientList().getKoeficients());
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

    @RequestMapping(value = "/getDatumy", method = RequestMethod.POST)
    public @ResponseBody RestReponse getDatumy(@RequestBody String druhNazov) {
        List<String> data = new ArrayList<>();
        DruhEntity druh = this.spravcaPodujatiBean.getDruhEntity(druhNazov);
        data.add(DruhDatum.getDateFromDruhDatum(druh.getZaciatok()));
        data.add(DruhDatum.getDateFromDruhDatum(druh.getKoniec()));
        data.add(druh.getPocet_cislic() + "");
        return new RestReponse(RestReponse.OK, data);
    }

    @RequestMapping(value = "/setDruh", method = RequestMethod.POST)
    public @ResponseBody RestReponse setDruh(@RequestBody String druhNazov) {
        this.spravcaPodujatiBean.setDruh(druhNazov);
        return new RestReponse(RestReponse.OK);
    }
    @RequestMapping(value="/validate", method = RequestMethod.POST)
    public @ResponseBody RestReponse validate(@RequestBody List<String> data) {
        List<String> response = this.spravcaPodujatiBean.validateTipping(data.get(0), data.get(1));
        return new RestReponse(RestReponse.OK, response);
    }
}
