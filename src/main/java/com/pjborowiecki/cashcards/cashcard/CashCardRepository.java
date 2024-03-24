package com.pjborowiecki.cashcards.cashcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jdbc.repository.query.Query;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {

    Iterable<CashCard> findByOwner(String owner);

    @Query("select * from cash_card cc where cc.owner = :#{authentication.name}")
    Iterable<CashCard> findAll();

}