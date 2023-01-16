package com.vaii_semestralka.beans;
import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.druh.DruhEntity;
import com.vaii_semestralka.druh.DruhService;
import com.vaii_semestralka.tipping_all.StavUdalosti;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.tipping_all.TippingAllService;
import com.vaii_semestralka.koeficienty.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class SpravcaPodujatiBean {
    @Autowired private TippingAllService tippingAllService;
    @Autowired private KoeficientService koeficientService;
    @Autowired private DruhService druhService;

    @Getter private String koefErrorMessage;
    @Getter private Operacia operacia;
    @Getter @Setter private TippingAllEntity tipping;
    @Getter private Iterable<DruhEntity> druhy;

    @Getter private DruhEntity druh;
    @Getter private KoeficientList koeficientList;
    public void save(TippingAllEntity tippingAllEntity) {
        if (this.operacia == Operacia.EDIT) {
            tippingAllEntity.setName(this.tipping.getName());
            if (disableZaciatok()) {
                tippingAllEntity.setBegginingDateTime(this.tipping.getBegginingDateTime());
            }
            if (disableCisla()) {
                tippingAllEntity.setFirst_number(this.tipping.getFirst_number());
                tippingAllEntity.setSecond_number(this.tipping.getSecond_number());
                tippingAllEntity.setThird_number(this.tipping.getThird_number());
                tippingAllEntity.setFourth_number(this.tipping.getFourth_number());
                tippingAllEntity.setFifth_number(this.tipping.getFifth_number());
            }
            this.koeficientService.deleteAllByName(tippingAllEntity.getName());
        }
        tippingAllEntity.setDruh(this.druh);
        this.tippingAllService.save(tippingAllEntity);
        List<Koeficient> koeficients = this.koeficientList.getKoeficients();
        for (Koeficient koeficient : koeficients) {
            KoeficientPrimaryKey koeficientPrimaryKey = new KoeficientPrimaryKey(tippingAllEntity, koeficient.getOd_());
            KoeficientEntity koeficientEntity = new KoeficientEntity(koeficientPrimaryKey,
                    koeficient.getDo_(), koeficient.getKoef());
            this.koeficientService.save(koeficientEntity);
        }
    }
    public String getButtonText() {
        return this.operacia == Operacia.EDIT ? "Upraviť" : "Vytvoriť";
    }
    public void init(Model model) {
        if (model.containsAttribute("name")) {
            this.druhy = this.druhService.getAll();
            if (model.getAttribute("name") == null) {
                this.operacia = Operacia.NEW;
                this.tipping = new TippingAllEntity();
                this.koeficientList = new KoeficientList();
                model.addAttribute("name", "novy");
            } else {
                this.koeficientList = new KoeficientList();
                this.operacia = Operacia.EDIT;
                this.tipping = tippingAllService.findById(model.getAttribute("name").toString());
                List<KoeficientEntity> koefs = this.koeficientService.findAllKoefsInOrder(this.tipping.getName());
                for (KoeficientEntity koeficientEntity : koefs) {
                    this.koeficientList.addKoeficient(new Koeficient(koeficientEntity.getKoeficientPrimaryKey().getOd_(),
                            koeficientEntity.getDo_(), koeficientEntity.getKoef_()));
                }
            }
        }
    }
    public void addNewKoef() {
        this.koeficientList.addNewKoeficient();
    }
    public void resetKoeficients() {
        this.koeficientList.removeKoeficients();
    }
    public Object getStyle() {
        return this.operacia == Operacia.EDIT ? "disabled=disabled" : "";
    }

    public Object jeNovy() {
        return this.operacia == Operacia.NEW;
    }
    public boolean suKoeficienty() {
        return this.koeficientList != null && !this.koeficientList.getKoeficients().isEmpty();
    }

    public void validateKoefList() {
        this.koefErrorMessage = this.koeficientList.zvalidujKoeficienty();
    }
    public DruhEntity getDruhEntity(String druhNazov) {
        return this.druhService.findById(druhNazov);
    }
    public List<String> validateTipping(String nazov, String zaciatok) {
        List<String> data = new ArrayList<>();
        if (this.operacia == Operacia.NEW) {
            TippingAllEntity entity = this.tippingAllService.findById(nazov);
            if (entity != null) {
                 data.add("Podujatie s názvom " + nazov + " už existuje!");
            } else {
                data.add("");
            }
            LocalDateTime date = LocalDateTime.now();
            if (DateTimeConverter.parseDateTimeT(zaciatok).isBefore(date)) {
                data.add("Dátum začiatku musí byť väčší ako súčasný!");
            } else {
                data.add("");
            }
        } else {
            data.add("");
            LocalDateTime date = LocalDateTime.now();
            LocalDateTime zaciatokDate = DateTimeConverter.parseDateTimeT(zaciatok);
            LocalDateTime zaciatokTip = this.tipping.getBegginingDateTime();
            if (!zaciatokTip.isEqual(zaciatokDate) && DateTimeConverter.parseDateTimeT(zaciatok).isBefore(date)) {
                data.add("Dátum začiatku musí byť väčší ako súčasný!");
            } else {
                data.add("");
            }
        }
        return data;
    }

    public boolean isSelected(DruhEntity druh) {
        if (this.tipping.getDruh() != null && druh.getNazov().equals(this.tipping.getDruh().getNazov())) {
            return true;
        }
        return false;
    }
    public void setDruh(String druhNazov) {
        this.druh = this.druhService.findById(druhNazov);
    }
    public boolean disableNazov() {
        return this.operacia == Operacia.EDIT;
    }

    public boolean disableZaciatok() {
        return this.operacia == Operacia.EDIT && !this.tipping.getTips().isEmpty();
    }

    public boolean disableKoniec() {
        return this.operacia == Operacia.EDIT && this.tipping.getStavUdalosti() == StavUdalosti.SKONCENY;
    }

    public boolean disableCisla() {
        return this.operacia == Operacia.EDIT && this.tipping.getTips() != null && !this.tipping.getTips().isEmpty();
    }

    public boolean disableDruh() {
        return this.operacia == Operacia.EDIT && this.tipping.getTips() != null && !this.tipping.getTips().isEmpty();
    }
    public boolean disableKoeficienty() {
        return this.operacia == Operacia.EDIT && this.tipping.getTips() != null && !this.tipping.getTips().isEmpty();
    }
}

enum Operacia {
    NEW,
    EDIT;
}