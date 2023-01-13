package com.vaii_semestralka.druh;

public interface DruhServiceInterface {
    void save(DruhEntity druhEntity);
    DruhEntity findById(String nazov);
    Iterable<DruhEntity> getAll();
}
