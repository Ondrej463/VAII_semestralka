package com.vaii_semestralka.controlers;

import com.vaii_semestralka.beans.DruhyAllBean;
import com.vaii_semestralka.beans.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DruhyAllControler {
    @Autowired private DruhyAllBean druhyAllBean;
    @Autowired private Session session;

    @RequestMapping(value = "/druhyAll", method = RequestMethod.GET)
    public String getDruhyAll(Model model) {
        this.druhyAllBean.init();
        model.addAttribute("druhyAll", this.druhyAllBean.getDruhyAll());
        model.addAttribute("firstName", session.getUserFirstName());
        model.addAttribute("lastName", session.getUserLastName());

        return "druh_all";
    }

    @RequestMapping(value="/getDruhData", method = RequestMethod.POST)
    public @ResponseBody RestReponse getDruhData(@RequestBody String nazov) {
        return new RestReponse(RestReponse.OK, this.druhyAllBean.getData(nazov));
    }
}
