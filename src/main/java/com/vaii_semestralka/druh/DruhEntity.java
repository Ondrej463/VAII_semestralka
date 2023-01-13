package com.vaii_semestralka.druh;

import com.vaii_semestralka.tipping_all.TippingAllEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "druh")
public class DruhEntity {
    @Id
    @Getter @Setter private String nazov;

    @Getter @Setter private String zaciatok;
    @Getter @Setter private String koniec;
    @Getter @Setter private int min_pocet_tipov;
    @Getter @Setter private int pocet_cislic;

    @OneToMany(mappedBy = "druh", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TippingAllEntity> tips;
}
