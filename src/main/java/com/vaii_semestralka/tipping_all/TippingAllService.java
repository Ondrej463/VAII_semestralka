package com.vaii_semestralka.tipping_all;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TippingAllService implements TippingAllServiceInterface {
    @Autowired private TippingAllRepository tippingAllRepository;

    @Override
    public List<TippingAllEntity> getAllTippings() {
        return tippingAllRepository.findAll();
    }

    @Override
    public void save(TippingAllEntity tipping) {
        tippingAllRepository.save(tipping);
    }

    @Override
    public TippingAllEntity findById(String name) {
         Optional<TippingAllEntity> optional = tippingAllRepository.findById(name);
        return optional.orElse(null);
    }

    @Override
    public void deleteViaId(String name) {
        tippingAllRepository.deleteById(name);
    }
}
