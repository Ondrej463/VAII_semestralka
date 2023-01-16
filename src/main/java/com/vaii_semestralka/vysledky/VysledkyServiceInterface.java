package com.vaii_semestralka.vysledky;

import com.vaii_semestralka.tip.TipPrimaryKey;
import com.vaii_semestralka.tipping_all.TippingAllEntity;

import java.util.List;

public interface VysledkyServiceInterface {
    VysledkyEntity findById(TipPrimaryKey tipPrimaryKey);
    void save(VysledkyEntity vysledkyEntity);

    boolean maVysledky(TippingAllEntity tippingAllEntity);

    void saveAll(List<VysledkyEntity> vysledkyEntities);

    List<VysledkyEntity> getVysledkyPodujatiaUsporiadanePodlaPoradia(String event);
}
