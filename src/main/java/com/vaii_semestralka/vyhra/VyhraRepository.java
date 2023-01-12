package com.vaii_semestralka.vyhra;

import com.vaii_semestralka.tipping_all.TippingAllEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface VyhraRepository extends CrudRepository<VyhraEntity, VyhraPrimaryKey> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM vyhra WHERE event = :name", nativeQuery = true)
    void delByName(String name);
    @Query(value="SELECT * from vyhra where event = :name order by od_", nativeQuery = true)
    List<VyhraEntity> findAllKoefsInOrder(String name);

    //    int deleteVyhraEntitiesByVyhraPrimaryKey(VyhraPrimaryKey vyhraPrimaryKey);
//
//    int deleteVyhraEntitiesByVyhraPrimaryKeyTippingAllEntityName(String name);
}
