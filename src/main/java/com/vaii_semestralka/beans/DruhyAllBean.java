package com.vaii_semestralka.beans;

import com.vaii_semestralka.druh.DruhEntity;
import com.vaii_semestralka.druh.DruhService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DruhyAllBean {
    @Autowired private DruhService druhService;

    @Getter private Iterable<DruhEntity> druhyAll;

    public void init() {
        druhyAll = druhService.getAll();
    }

    public List<String> getData(String nazov) {
        List<String> data = new ArrayList<>();
        DruhEntity druh = this.druhService.findById(nazov);
        data.add(druh.getZaciatok());
        data.add(druh.getKoniec());
        data.add(druh.getPocet_cislic() + "");
        data.add(druh.getMin_pocet_tipov() + "");
        return data;
    }
}
