package com.vaii_semestralka.tip;

public interface TipServiceInterface {
    TipEntity findById(TipPrimaryKey tipPrimaryKeys);
    Iterable<TipEntity> getAll();
    void save(TipEntity tipEntity);
    void delete(TipEntity tipEntity);
}
