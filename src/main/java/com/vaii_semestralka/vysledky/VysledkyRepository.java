package com.vaii_semestralka.vysledky;

import com.vaii_semestralka.tip.TipPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VysledkyRepository extends JpaRepository<VysledkyEntity, TipPrimaryKey> {
    @Query(value = "select count(*) from vysledky where event = :nazov", nativeQuery = true)
    int getPocetVysledkov(String nazov);

    default List<VysledkyEntity> saveAllAndFlush(List<VysledkyEntity> vysledky) {
        List<VysledkyEntity> vysledkyEntities = saveAll(vysledky);
        flush();

        return vysledkyEntities;
    }
    @Query(value = "select * from vysledky where event = :event order by poradie", nativeQuery = true)
    List<VysledkyEntity> getVysledkyPodujatiaUsporiadanePodlaPoradia(String event);
}
