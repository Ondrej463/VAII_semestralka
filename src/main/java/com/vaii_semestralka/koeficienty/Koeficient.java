package com.vaii_semestralka.koeficienty;

import lombok.Getter;
import lombok.Setter;

public class Koeficient {
    @Getter @Setter private int od_;
    @Getter @Setter private int do_;
    @Getter @Setter private double koef;

    public Koeficient() {

    }
    public Koeficient(int od_, int do_, double koef) {
        this.od_ = od_;
        this.do_ = do_;
        this.koef = koef;
    }

}
