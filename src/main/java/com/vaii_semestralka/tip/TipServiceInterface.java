package com.vaii_semestralka.tip;

import java.util.List;

public interface TipServiceInterface {
    TipEntity findById(TipPrimaryKey tipPrimaryKeys);
    Iterable<TipEntity> getAll();
    void save(TipEntity tipEntity);
    void delete(TipEntity tipEntity);

    List<TipEntity> getAllTipsOrderByWhen(String who);
}
