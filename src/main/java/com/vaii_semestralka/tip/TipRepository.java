package com.vaii_semestralka.tip;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TipRepository extends CrudRepository<TipEntity, TipPrimaryKey> {
    @Query(value="select * from vyplnene_tipy where who = :who order by input_time DESC", nativeQuery = true)
    List<TipEntity> getAllTipsOrderByWhen(String who);
}