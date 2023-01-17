package com.vaii_semestralka.koeficienty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoeficientService implements KoeficientServiceInterface {
    @Autowired private KoeficientRepository koeficientRepository;

    @Override
    public KoeficientEntity findById(KoeficientPrimaryKey koeficientPrimaryKey) {
        return koeficientRepository.findById(koeficientPrimaryKey).orElse(null);
    }

    @Override
    public Iterable<KoeficientEntity> getAll() {
        return koeficientRepository.findAll();
    }

    @Override
    public void save(KoeficientEntity koeficientEntity) {
        koeficientRepository.save(koeficientEntity);
    }

    @Override
    public void delete(KoeficientEntity koeficientEntity) {
        koeficientRepository.delete(koeficientEntity);
    }

    @Override
    public List<KoeficientEntity> findAllKoefsInOrder(String name) {
        return this.koeficientRepository.findAllKoefsInOrder(name);
    }

    @Override
    public void deleteAllByName(String name) {
        this.koeficientRepository.delByName(name);
    }

    @Override
    public void saveAll(List<KoeficientEntity> koefs) {
        this.koeficientRepository.saveAll(koefs);
    }
}
