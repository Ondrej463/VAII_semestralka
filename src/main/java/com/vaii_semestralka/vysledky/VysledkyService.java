package com.vaii_semestralka.vysledky;

import com.vaii_semestralka.tip.TipPrimaryKey;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VysledkyService implements VysledkyServiceInterface {
    @Autowired private VysledkyRepository vysledkyRepository;

    @Override
    public VysledkyEntity findById(TipPrimaryKey tipPrimaryKey) {
        return this.vysledkyRepository.findById(tipPrimaryKey).orElse(null);
    }

    @Override
    public void save(VysledkyEntity vysledkyEntity) {
        this.vysledkyRepository.save(vysledkyEntity);
    }

    @Override
    public boolean maVysledky(TippingAllEntity tippingAllEntity) {
        return this.vysledkyRepository.getPocetVysledkov(tippingAllEntity.getName()) > 0;
    }

    @Override
    public void saveAll(List<VysledkyEntity> vysledkyEntities) {
        this.vysledkyRepository.saveAllAndFlush(vysledkyEntities);
    }

    @Override
    public List<VysledkyEntity> getVysledkyPodujatiaUsporiadanePodlaPoradia(String event) {
        return this.vysledkyRepository.getVysledkyPodujatiaUsporiadanePodlaPoradia(event);
    }
}
