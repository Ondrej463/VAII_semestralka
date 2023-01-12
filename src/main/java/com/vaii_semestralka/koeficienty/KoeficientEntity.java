package com.vaii_semestralka.koeficienty;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "koeficient")
public class KoeficientEntity implements Serializable {
    @EmbeddedId
    @Getter @Setter private KoeficientPrimaryKey koeficientPrimaryKey;

    @Getter @Setter private int do_;
    @Getter @Setter private double koef_;

    public KoeficientEntity(KoeficientPrimaryKey koeficientPrimaryKey, int to, double koef) {
        this.koeficientPrimaryKey = koeficientPrimaryKey;
        this.koef_ = koef;
        this.do_ = to;
    }

    public KoeficientEntity() {

    }

}
