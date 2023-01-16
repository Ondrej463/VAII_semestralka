package com.vaii_semestralka.tip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TipService implements TipServiceInterface {
    @Autowired private TipRepository repository;

    @Override
    public TipEntity findById(TipPrimaryKey tipPrimaryKeys) {
        return repository.findById(tipPrimaryKeys).orElse(null);
    }

    @Override
    public Iterable<TipEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(TipEntity tipEntity) {
        repository.save(tipEntity);
    }

    @Override
    public void delete(TipEntity tipEntity) {
        repository.delete(tipEntity);
    }

    @Override
    public List<TipEntity> getAllTipsOrderByWhen(String who) {
        return this.repository.getAllTipsOrderByWhen(who);
    }
}
