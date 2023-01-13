package com.vaii_semestralka.druh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DruhService implements DruhServiceInterface {
    @Autowired
    private DruhRepository druhRepository;

    @Override
    public void save(DruhEntity druhEntity) {
        this.druhRepository.save(druhEntity);
    }

    @Override
    public DruhEntity findById(String nazov) {
        return this.druhRepository.findById(nazov).orElse(null);
    }

    @Override
    public Iterable<DruhEntity> getAll() {
        return this.druhRepository.findAll();
    }
}
