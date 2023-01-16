package com.vaii_semestralka.vysledky;

import com.vaii_semestralka.tip.TipEntity;
import com.vaii_semestralka.tip.TipPrimaryKey;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vysledky")
public class VysledkyEntity implements Serializable {
    @EmbeddedId
    @Getter @Setter private TipPrimaryKey tipPrimaryKey;

    @Getter @Setter private int poradie;
    @Getter @Setter private int rozdiel;

    @Getter @Setter private double koeficient;
    @Getter @Setter private double zisk;


    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @Getter private TipEntity tipEntity;

    public VysledkyEntity(TipPrimaryKey tipPrimaryKey, int rozdiel, double zisk) {
        this.tipPrimaryKey = tipPrimaryKey;
        this.rozdiel = rozdiel;
        this.zisk = zisk;
    }
    public VysledkyEntity() {

    }
    public String getZiskScreenFormat() {
        String ziskS = zisk + "";
        if (ziskS.substring(ziskS.indexOf('.') + 1).length() == 1) {
            return ziskS.concat("0€");
        }
        return ziskS.concat("€");
    }
}
