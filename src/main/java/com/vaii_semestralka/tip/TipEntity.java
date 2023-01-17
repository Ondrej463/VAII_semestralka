package com.vaii_semestralka.tip;

import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.vysledky.VysledkyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "vyplnene_tipy")
public class TipEntity implements Serializable {
    @EmbeddedId
    @Getter @Setter private TipPrimaryKey tipPrimaryKeys;

    @Getter @Setter private double vklad;
    @Column(name="input_time")
    private LocalDateTime when;
    @Getter @Setter private int first_number;
    @Getter @Setter private int second_number;
    @Getter @Setter private int third_number;
    @Getter @Setter private int fourth_number;
    @Getter @Setter private int fifth_number;

    @Column(name="vybrate_peniaze")
    @Getter @Setter private boolean vybratePeniaze;

    @OneToOne(mappedBy = "tipEntity")
    @Getter @Setter VysledkyEntity vysledkyEntity;

    public TipEntity() {

    }
    public String getWhen() {
        return this.when == null ? null : DateTimeConverter.formatDateTime(this.when);
    }
    public void setWhen(String date) {
        this.when = DateTimeConverter.parseDateTime(date);
    }
    public void setWhenDateTime(LocalDateTime date) {
        this.when = date;
    }
    public String getVkladScreenFormat() {
        String vkladS = this.vklad + "";
        if (vkladS.substring(vkladS.indexOf('.') + 1).length() == 1) {
            return vkladS.concat("0€");
        }
        return vkladS.concat("€");
    }

    public String getCisla() {
        String vypis = "";
        String[] cisla = {this.first_number + "", this.second_number + "", this.third_number + "", this.fourth_number + ""
                , this.fifth_number + ""};
        for (int i = 0; i < cisla.length; i++) {
            if (i < this.getTipPrimaryKeys().getTippingAllEntity().getDruh().getPocet_cislic()) {
                vypis = vypis.concat(cisla[i] + " ");
            }
        }
        vypis = vypis.substring(0, vypis.length() - 1);
        return vypis;
    }
}
