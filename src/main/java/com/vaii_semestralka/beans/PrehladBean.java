package com.vaii_semestralka.beans;

import com.vaii_semestralka.tipping_all.StavUdalosti;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PrehladBean {
    @Autowired private TippingAllService service;
    public List<TippingAllEntity> getTippings() {
        return service.getAllTippings();
    }

    public void delete(String name) {
        this.service.deleteViaId(name);
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
}


