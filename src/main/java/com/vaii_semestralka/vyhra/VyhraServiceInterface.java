package com.vaii_semestralka.vyhra;

import java.util.List;

public interface VyhraServiceInterface {
    VyhraEntity findById(VyhraPrimaryKey vyhraPrimaryKey);
    Iterable<VyhraEntity> getAll();
    void save(VyhraEntity vyhraEntity);
    void delete(VyhraEntity vyhraEntity);
    List<VyhraEntity> findAllKoefsInOrder(String name);
    void deleteAllByName(String name);
}
