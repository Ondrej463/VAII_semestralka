package com.vaii_semestralka.koeficienty;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface KoeficientRepository extends CrudRepository<KoeficientEntity, KoeficientPrimaryKey> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM koeficient WHERE event = :name", nativeQuery = true)
    void delByName(String name);
    @Query(value="SELECT * from koeficient where event = :name order by od_", nativeQuery = true)
    List<KoeficientEntity> findAllKoefsInOrder(String name);
}
