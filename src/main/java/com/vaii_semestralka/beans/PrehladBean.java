package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.koeficienty.KoeficientService;
import com.vaii_semestralka.tipping_all.StavUdalosti;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import java.util.List;


@Configuration
public class PrehladBean {
    @Autowired private TippingAllService service;
    @Autowired private KoeficientService koeficientService;
    @Autowired private Session session;

    public List<TippingAllEntity> getTippings() {
        return service.findAllInOrder();
    }

    public String delete(String name) {
        TippingAllEntity tippingAllEntity = this.service.findById(name);
        if (!tippingAllEntity.getTips().isEmpty()) {
            return "Nie je možné vymazať udalosť " + name + "!";
        } else {
            this.koeficientService.deleteAllByName(name);
            this.service.delete(name);
            return "";
        }
    }

    public String getColor(TippingAllEntity tippingAllEntity) {
        StavUdalosti stavUdalosti = tippingAllEntity.getStavUdalosti();
        switch (stavUdalosti) {
            case PREBIEHA:
                return "green";
            case SKONCENY:
                return "red";
            default:
                return "yellow";
        }
    }
    public String getTextCol() {
        return this.session.jePrihlasenyPouzivatelAdmin() ? "Akcie" : "Výsledky";
    }

    public String getPopisStavu(TippingAllEntity tippingAllEntity) {
        return tippingAllEntity.getStavUdalosti().getStav();
    }
    public String getMojTip(TippingAllEntity tippingAllEntity) {
        return LoggedInUser.getActualUser().hasTipInEvent(tippingAllEntity.getName()) ? "fa fa-check text-green" : "fa fa-times text-red";
    }

    public int getNumberOfTips(String nameOfEvent) {
        return service.findById(nameOfEvent).getTips().size();
    }

    public TippingAllEntity getTipping(String name) {
        return this.service.findById(name);
    }

    public boolean disableInfo(TippingAllEntity tippingAllEntity) {
        return tippingAllEntity.getStavUdalosti() != StavUdalosti.SKONCENY;
    }
}


