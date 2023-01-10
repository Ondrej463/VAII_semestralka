package com.vaii_semestralka.tip;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface TipRepository extends CrudRepository<TipEntity, TipPrimaryKeys> {
}