package com.vaii_semestralka.beans;

import com.vaii_semestralka.LoggedInUser;
import com.vaii_semestralka.tip.TipPrimaryKeys;
import com.vaii_semestralka.tipping_all.StavUdalosti;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MojeTipyBean {
    @Autowired private TippingAllService tippingAllService;

    public List<TippingAllEntity> getAllUserTippings() {
        return LoggedInUser.getActualUser().getTips().stream()
                .map(tipEntity -> tipEntity.getTipPrimaryKeys().getTippingAllEntity()).collect(Collectors.toList());
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
    public String getPopisStavu(TippingAllEntity tippingAllEntity) {
        return tippingAllEntity.getStavUdalosti().getStav();
    }

    public int getNumberOfTips(String nameOfEvent) {
        return tippingAllService.findById(nameOfEvent).getTips().size();
    }
}
