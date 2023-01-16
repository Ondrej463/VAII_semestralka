package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.koeficienty.Koeficient;
import com.vaii_semestralka.koeficienty.KoeficientEntity;
import com.vaii_semestralka.tipping_all.StavUdalosti;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class PodujatieBean {
    @Autowired private TippingAllService tippingAllService;
    @Getter private TippingAllEntity tippingAllEntity;

    public String getFarbaKoeficient(KoeficientEntity koeficient) {
        return koeficient.getDo_() == 0 ? "red" : "green";
    }

    public String getMojTip() {
        return LoggedInUser.getActualUser().hasTipInEvent(tippingAllEntity.getName()) ? "fa fa-check text-green" : "fa fa-times text-red";
    }
    public boolean tipPovoleny() {
        return !LoggedInUser.getActualUser().hasTipInEvent(tippingAllEntity.getName())
                && this.tippingAllEntity.getStavUdalosti() == StavUdalosti.PREBIEHA;
    }
    public String getFarbaKoniec() {
        return this.tippingAllEntity.getStavUdalosti() == StavUdalosti.SKONCENY ? "red" : "green";
    }
    public void init(String nazov) {
        this.tippingAllEntity = this.tippingAllService.findById(nazov);
    }
    public String getFarbaZaciatok() {
        if (this.tippingAllEntity.getStavUdalosti() == StavUdalosti.NEZACAL) {
            return "yellow";
        } else if (this.tippingAllEntity.getStavUdalosti() == StavUdalosti.PREBIEHA) {
            return "green";
        } else {
            return "red";
        }
    }
}
