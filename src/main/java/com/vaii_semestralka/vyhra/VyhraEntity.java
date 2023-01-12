package com.vaii_semestralka.vyhra;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "vyhra")
public class VyhraEntity implements Serializable {
    @EmbeddedId
    @Getter @Setter private VyhraPrimaryKey vyhraPrimaryKey;

    @Getter @Setter private int do_;
    @Getter @Setter private double koef_;

    public VyhraEntity(VyhraPrimaryKey vyhraPrimaryKey, int to, double koef) {
        this.vyhraPrimaryKey = vyhraPrimaryKey;
        this.koef_ = koef;
        this.do_ = to;
    }

    public VyhraEntity() {

    }

}
