package com.vaii_semestralka.tipping_all;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TippingAllRepository extends JpaRepository<TippingAllEntity, String> {

    @Query(value="SELECT * from tipping_all t ORDER BY t.end DESC", nativeQuery = true)
    List<TippingAllEntity> findAllInOrder();
}
