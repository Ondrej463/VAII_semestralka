package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.tip.TipEntity;
import com.vaii_semestralka.tip.TipPrimaryKey;
import com.vaii_semestralka.tip.TipService;
import com.vaii_semestralka.tipping_all.StavUdalosti;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MojeTipyBean {
    @Autowired private TippingAllService tippingAllService;
    @Autowired private TipService tipService;


    public List<TipEntity> getAllUserTippings() {
        return tipService.getAllTipsOrderByWhen(LoggedInUser.getActualUser().getEmail());
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


    public TipEntity getTipEntity(TippingAllEntity tippingAllEntity) {
        return this.tipService.findById(new TipPrimaryKey(tippingAllEntity, LoggedInUser.getActualUser()));
    }

    public String[] getStavTipu(TippingAllEntity tippingAllEntity) {
        String[] vypis = new String[2];
        if (tippingAllEntity.getStavUdalosti() == StavUdalosti.PREBIEHA) {
            vypis[0] = "Čaká na výsledok";
            vypis[1] = "yellow";
        } else {
            TipEntity tip = getTipEntity(tippingAllEntity);
            if (tip.isVybratePeniaze()) {
                if (tip.getVysledkyEntity() == null) {
                    vypis[0] = "Peniaze vrátené";
                } else {
                    vypis[0] = "Peniaze vybraté";
                }
                vypis[1] = "green";
            } else {
                if ((tip.getVysledkyEntity() == null) || (tip.getVysledkyEntity() != null && tip.getVysledkyEntity().getZisk() > 0)) {
                    vypis[0] = "Nevybraté peniaze";
                    vypis[1] = "red";
                } else {
                    vypis[0] = "Nevyhrali ste";
                    vypis[1] = "white";
                }
            }
        }
        return vypis;
    }

    public String getZisk(TipEntity tipEntity) {
        if (tipEntity.getVysledkyEntity() == null) {
            return "-";
        } else {
            return tipEntity.getVysledkyEntity().getZiskScreenFormat();
        }
    }

    public TippingAllEntity getTippingAllEntity(String name) {
        return this.tippingAllService.findById(name);
    }
}
