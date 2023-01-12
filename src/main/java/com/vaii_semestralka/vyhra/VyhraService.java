package com.vaii_semestralka.vyhra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VyhraService implements VyhraServiceInterface{
    @Autowired private VyhraRepository vyhraRepository;

    @Override
    public VyhraEntity findById(VyhraPrimaryKey vyhraPrimaryKey) {
        return vyhraRepository.findById(vyhraPrimaryKey).orElse(null);
    }

    @Override
    public Iterable<VyhraEntity> getAll() {
        return vyhraRepository.findAll();
    }

    @Override
    public void save(VyhraEntity vyhraEntity) {
        vyhraRepository.save(vyhraEntity);
    }

    @Override
    public void delete(VyhraEntity vyhraEntity) {
        vyhraRepository.delete(vyhraEntity);
    }

    @Override
    public List<VyhraEntity> findAllKoefsInOrder(String name) {
        return this.vyhraRepository.findAllKoefsInOrder(name);
    }

    @Override
    public void deleteAllByName(String name) {
        this.vyhraRepository.delByName(name);
    }
}
