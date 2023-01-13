package com.vaii_semestralka.beans;

import com.vaii_semestralka.druh.DruhEntity;
import com.vaii_semestralka.druh.DruhService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DruhBean {
    @Autowired private DruhService druhService;
    @Getter private List<String> zaciatokOptions;
    @Getter private List<String> koniecOptions;
    @Getter private String nazovErrorMessage;
    @Getter @Setter
    private String nazovClass;
    public void initZaciatokOptions() {
        zaciatokOptions = new ArrayList<>();
        zaciatokOptions.add("Vyberte jednu z možností");
        zaciatokOptions.add("Hneď");
        zaciatokOptions.add("Po 15min");
        zaciatokOptions.add("Po 30min");
        zaciatokOptions.add("Po 45min");
        zaciatokOptions.add("Po 1hod");
        zaciatokOptions.add("Po 2hod");
        zaciatokOptions.add("Po 3hod");
        zaciatokOptions.add("Po 4 hod");
    }

    public void initKoniecOptions() {
        koniecOptions = new ArrayList<>();
        koniecOptions.add("Vyberte jednu z možností");
        koniecOptions.add("Po 5min");
        koniecOptions.add("Po 15min");
        koniecOptions.add("Po 30min");
        koniecOptions.add("Po 45min");
        koniecOptions.add("Po 1hod");
        koniecOptions.add("Po 2hod");
        koniecOptions.add("Po 3hod");
        koniecOptions.add("Po 5hod");
        koniecOptions.add("Po 6hod");
        koniecOptions.add("Po 1 dni");
        koniecOptions.add("Po 2 dňoch");
        koniecOptions.add("Po 3 dňoch");
        koniecOptions.add("Po 4 dňoch");
        koniecOptions.add("Po týždni");
        koniecOptions.add("Po mesiaci");
    }

    public void validate(DruhEntity druh) {
        if (this.druhService.findById(druh.getNazov()) != null) {
            this.nazovErrorMessage = "Druh s názvom " + druh.getNazov() + " už existuje!";
            this.nazovClass = "formControl error";
        } else {
            this.nazovErrorMessage = "";
            this.nazovClass = "formControl success";
        }
    }

    public void save(DruhEntity druh) {
        this.druhService.save(druh);
    }
    public DruhBean() {
        initZaciatokOptions();
        initKoniecOptions();
    }
}
