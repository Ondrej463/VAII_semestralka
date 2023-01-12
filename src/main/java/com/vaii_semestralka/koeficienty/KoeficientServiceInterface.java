package com.vaii_semestralka.koeficienty;

import java.util.List;

public interface KoeficientServiceInterface {
    KoeficientEntity findById(KoeficientPrimaryKey koeficientPrimaryKey);
    Iterable<KoeficientEntity> getAll();
    void save(KoeficientEntity koeficientEntity);
    void delete(KoeficientEntity koeficientEntity);
    List<KoeficientEntity> findAllKoefsInOrder(String name);
    void deleteAllByName(String name);
}
