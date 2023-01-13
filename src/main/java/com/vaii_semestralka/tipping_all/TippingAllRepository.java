package com.vaii_semestralka.tipping_all;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TippingAllRepository extends JpaRepository<TippingAllEntity, String> {
    @Transactional
    @Modifying
    @Query(value="delete from tipping_all where name = :name", nativeQuery = true)
    public void deleteByName(String name);

    @Query(value="SELECT * from tipping_all t ORDER BY t.beggining", nativeQuery = true)
    List<TippingAllEntity> findAllInOrder();
}
