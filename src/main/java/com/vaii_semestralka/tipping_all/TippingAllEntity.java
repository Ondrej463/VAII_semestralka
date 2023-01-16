package com.vaii_semestralka.tipping_all;

import com.vaii_semestralka.converter.DateTimeConverter;
import com.vaii_semestralka.druh.DruhEntity;
import com.vaii_semestralka.tip.TipEntity;
import com.vaii_semestralka.koeficienty.KoeficientEntity;
import com.vaii_semestralka.vysledky.VysledkyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "tipping_all")
public class TippingAllEntity implements Serializable {
    @Id
    @Getter @Setter private String name;

    @ManyToOne
    @JoinColumn(name = "druh", referencedColumnName = "nazov")
    @Getter @Setter private DruhEntity druh;

    private LocalDateTime beggining;
    private LocalDateTime end;

    @Getter @Setter private int first_number;
    @Getter @Setter private int second_number;
    @Getter @Setter private int third_number;
    @Getter @Setter private int fourth_number;
    @Getter @Setter private int fifth_number;

     @OneToMany(mappedBy = "tipPrimaryKeys.tippingAllEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     @Getter private Set<TipEntity> tips;

    @OneToMany(mappedBy = "koeficientPrimaryKey.tippingAllEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter private Set<KoeficientEntity> koefs;

    @OneToMany(mappedBy = "tipPrimaryKey.tippingAllEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Getter private Set<VysledkyEntity> vysledky;

    public String getBeggining() {
        return this.beggining == null ? null : DateTimeConverter.formatDateTime(this.beggining);
    }
    public void setBeggining(String date) {
        this.beggining = LocalDateTime.parse(date);
    }

    public String getEnd() {
        return this.end == null ? null : DateTimeConverter.formatDateTime(this.end);
    }

    public void setEnd(String date) {
        this.end = LocalDateTime.parse(date);
    }

    public StavUdalosti getStavUdalosti() {
        LocalDateTime now = LocalDateTime.now();
        if (end.isBefore(now)) {
            return StavUdalosti.SKONCENY;
        } else if (beggining.isBefore(now) && end.isAfter(now)) {
            return StavUdalosti.PREBIEHA;
        } else {
            return StavUdalosti.NEZACAL;
        }
    }
    public String getBegginingScreenFormat() {
        return this.beggining == null ? null : DateTimeConverter.getScreenFormat(this.beggining);
    }

    public String getEndScreenFormat() {
        return this.end == null ? null : DateTimeConverter.getScreenFormat(this.end);
    }
    public LocalDateTime getBegginingDateTime() {
        return this.beggining;
    }
    public LocalDateTime getEndDateTime() {
        return this.end;
    }
    public void setBegginingDateTime(LocalDateTime date) {
        this.beggining = date;
    }

    public List<KoeficientEntity> getKoeficientsInOrder() {
        return this.getKoefs().stream()
                .sorted(Comparator.comparingInt(o -> o.getKoeficientPrimaryKey().getOd_())).collect(Collectors.toList());
    }

    public boolean maDostatokTipov() {
        return this.getTips().size() >= this.getDruh().getMin_pocet_tipov();
    }

    public String[] getCisla() {
        String[] cisla = {this.first_number + "", this.second_number + "", this.third_number + "", this.fourth_number + ""
                , this.fifth_number + ""};
        for (int i = 0; i < cisla.length; i++) {
            if (i >= this.druh.getPocet_cislic()) {
                cisla[i] = "";
            }
        }
        return cisla;
    }
}

