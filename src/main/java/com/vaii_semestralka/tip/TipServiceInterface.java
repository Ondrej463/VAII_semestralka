package com.vaii_semestralka.tip;

import java.util.List;

public interface TipServiceInterface {
    TipEntity findById(TipPrimaryKeys tipPrimaryKeys);
    Iterable<TipEntity> getAll();
    void save(TipEntity tipEntity);
    void delete(TipEntity tipEntity);
}
