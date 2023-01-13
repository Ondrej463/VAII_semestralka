package com.vaii_semestralka.tipping_all;

import java.util.List;

public interface TippingAllServiceInterface {
    List<TippingAllEntity> getAllTippings();
    void save(TippingAllEntity tipping);

    TippingAllEntity findById(String name);

    void delete(String name);
    List<TippingAllEntity> findAllInOrder();
}
